package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;

import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobProps;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobPropsHelper;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ChillingMobEnchant extends MobEnchant {

	public ChillingMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity entity) {
		NewMobEnchantUtils.executeIfPresentWithLevel(entity, ModMobEnchants.CHILLING.get(), (level) -> {
			MobProps comboCap = MobPropsHelper.getMobPropsCapability(entity);
			if (comboCap == null)
				return;
			int freezeNearbyTimer = comboCap.getFreezeNearbyTimer();
			if (freezeNearbyTimer <= 0) {
				PROXY.spawnParticles(entity, ParticleTypes.ITEM_SNOWBALL);
				AreaOfEffectHelper.applyToNearbyEntities(entity, 1.5F,
						AreaOfEffectHelper.getCanApplyToEnemyPredicate(entity),
						(LivingEntity nearbyEntity) -> {
							freezeEnemy(1, nearbyEntity, level);
							PROXY.spawnParticles(nearbyEntity,
									ParticleTypes.ITEM_SNOWBALL);
						});
				comboCap.setFreezeNearbyTimer(40);
			} else {
				comboCap.setFreezeNearbyTimer(freezeNearbyTimer - 1);
			}
		});

	}

	private static void freezeEnemy(int amplifier, LivingEntity nearbyEntity, int durationInSeconds) {
		MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationInSeconds * 20,
				amplifier);
		MobEffectInstance fatigue = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, durationInSeconds * 20,
				Math.max(0, amplifier * 2 - 1));
		nearbyEntity.addEffect(slowness);
		nearbyEntity.addEffect(fatigue);
		PROXY.spawnParticles(nearbyEntity, ParticleTypes.ITEM_SNOWBALL);
	}
}
