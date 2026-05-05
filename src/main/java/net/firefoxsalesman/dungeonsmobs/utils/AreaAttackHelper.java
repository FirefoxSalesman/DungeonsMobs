package net.firefoxsalesman.dungeonsmobs.utils;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class AreaAttackHelper {
	public static void areaAttack(float range, float X, float Y, float Z, float arc, float damage, Mob attacker) {
		for (LivingEntity entityHit : attacker.level().getEntitiesOfClass(LivingEntity.class,
				attacker.getBoundingBox().inflate(X, Y, Z))) {
			float entityHitAngle = (float) ((Math.atan2(
					entityHit.getZ() - attacker.getZ(),
					entityHit.getX() - attacker.getX()) * (180 / Math.PI)
					- 90) % 360);
			float entityAttackingAngle = attacker.yBodyRot % 360;
			if (entityHitAngle < 0) {
				entityHitAngle += 360;
			}
			if (entityAttackingAngle < 0) {
				entityAttackingAngle += 360;
			}
			float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
			float entityHitDistance = (float) Math.sqrt((entityHit.getZ()
					- attacker.getZ())
					* (entityHit.getZ() - attacker.getZ())
					+ (entityHit.getX() - attacker.getX())
							* (entityHit.getX() - attacker.getX()));
			if (entityHitDistance <= range
					&& (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2)
					|| (entityRelativeAngle >= 360 - arc / 2
							|| entityRelativeAngle <= -360 + arc / 2)) {
				if (!attacker.isAlliedTo(entityHit) && !(entityHit == attacker)
						&& !FollowerLeaderHelper.isFollowerOf(entityHit, attacker)) {
					entityHit.hurt(attacker.damageSources().mobAttack(attacker),
							(float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE)
									* damage);

					float attackKnockback = (float) attacker
							.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
					double ratioX = Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F));
					double ratioZ = -Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F));
					double knockbackReduction = 0.35D;
					entityHit.hurt(attacker.damageSources().mobAttack(attacker),
							damage);
					forceKnockback(entityHit, attackKnockback * 0.8F,
							ratioX, ratioZ,
							knockbackReduction);
					entityHit.setDeltaMovement(
							entityHit.getDeltaMovement().add(0, 0.3333333, 0));
				}
			}
		}
	}

	private static void forceKnockback(LivingEntity attackTarget, float strength, double ratioX, double ratioZ,
			double knockbackResistanceReduction) {
		LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(attackTarget, strength, ratioX,
				ratioZ);
		if (event.isCanceled())
			return;
		strength = event.getStrength();
		ratioX = event.getRatioX();
		ratioZ = event.getRatioZ();
		strength = (float) ((double) strength
				* (1.0D - attackTarget.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)
						* knockbackResistanceReduction));
		if (!(strength <= 0.0F)) {
			attackTarget.hasImpulse = true;
			Vec3 vector3d = attackTarget.getDeltaMovement();
			Vec3 vector3d1 = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);
			attackTarget.setDeltaMovement(vector3d.x / 2.0D - vector3d1.x,
					attackTarget.onGround()
							? Math.min(0.4D, vector3d.y / 2.0D + (double) strength)
							: vector3d.y,
					vector3d.z / 2.0D - vector3d1.z);
		}
	}
}
