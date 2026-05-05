package net.firefoxsalesman.dungeonsmobs.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class KnockbackHelper {
	public static void forceKnockback(LivingEntity attackTarget, float strength, double ratioX, double ratioZ,
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
