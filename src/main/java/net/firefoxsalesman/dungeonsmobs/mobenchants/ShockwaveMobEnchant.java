package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.AbilityHelper;
import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonslibs.utils.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ShockwaveMobEnchant extends MobEnchant {
	private static final float SHOCKWAVE_CHANCE = 0.3f;

	public ShockwaveMobEnchant(Properties properties) {
		super(properties);
	}

	// TODO Pull this out to the library
	private static void spawnCritCloud(LivingEntity attacker, LivingEntity victim, float radius) {
		AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(victim.level(), victim.getX(),
				victim.getY(),
				victim.getZ());
		areaeffectcloudentity.setOwner(attacker);
		areaeffectcloudentity.setParticle(ParticleTypes.CRIT);
		areaeffectcloudentity.setRadius(radius);
		areaeffectcloudentity.setDuration(0);
		attacker.level().addFreshEntity(areaeffectcloudentity);
	}

	private static void causeShockwave(LivingEntity attacker, LivingEntity target, float damageAmount,
			float distance) {
		DamageSource shockwave = target.damageSources().explosion(attacker, target);
		Vec3 vec1 = target.position();
		Vec3 vec2 = attacker.position();
		AreaOfEffectHelper.applyToNearbyEntities(target, distance,
				(nearbyEntity) -> AbilityHelper.isFacingEntity(attacker, nearbyEntity,
						vec1.subtract(vec2), 60)
						&& AreaOfEffectHelper
								.getCanApplyToSecondEnemyPredicate(attacker, target)
								.test(nearbyEntity),
				(LivingEntity nearbyEntity) -> nearbyEntity.hurt(shockwave, damageAmount));
	}

	public static void doEffect(LivingEntity defender, Entity entity, float amount) {
		if (entity instanceof LivingEntity attacker) {
			if (attacker.getLastHurtMobTimestamp() == attacker.tickCount)
				return;
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.SHOCKWAVE.get(),
					level -> {
						if (attacker.getRandom().nextFloat() <= SHOCKWAVE_CHANCE) {

							SoundHelper.playBoltImpactSound(attacker);
							float shockwaveDamage = 4;
							shockwaveDamage *= (level + 1) / 2.0F;
							spawnCritCloud(attacker, defender, 3.0F);
							causeShockwave(attacker, defender, shockwaveDamage, 6.0F);
						}
					});
		}
	}
}
