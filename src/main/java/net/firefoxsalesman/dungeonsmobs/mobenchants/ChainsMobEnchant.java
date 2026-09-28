package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ChainsMobEnchant extends MobEnchant {
	private static float CHAINS_CHANCE = 0.3F;

	public ChainsMobEnchant(Properties properties) {
		super(properties);
	}

	private static void chainNearbyEntities(LivingEntity attacker, LivingEntity target, float distance,
			int timeMultiplier) {
		DungeonsMobs.PROXY.spawnParticles(target, ParticleTypes.PORTAL);
		MobEffectInstance chained = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * timeMultiplier, 5);
		target.addEffect(chained);
		AreaOfEffectHelper.applyToNearbyEntities(target, distance,
				AreaOfEffectHelper.getCanApplyToSecondEnemyPredicate(attacker, target),
				(LivingEntity nearbyEntity) -> {
					double motionX = target.getX() - (nearbyEntity.getX());
					double motionY = target.getY() - (nearbyEntity.getY());
					double motionZ = target.getZ() - (nearbyEntity.getZ());
					Vec3 vector3d = new Vec3(motionX, motionY, motionZ).scale(0.15);

					nearbyEntity.setDeltaMovement(vector3d);
					nearbyEntity.addEffect(chained);
					DungeonsMobs.PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
				});
	}

	public static void doEffect(LivingEntity attacker, Entity defender) {
		if (defender instanceof LivingEntity livingDefender)
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.CHAINS.get(), level -> {
				if (attacker.getRandom().nextFloat() <= CHAINS_CHANCE)
					chainNearbyEntities(attacker, livingDefender, 1.5F, level);
			});
	}
}
