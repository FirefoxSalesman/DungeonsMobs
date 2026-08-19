package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobProps;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobPropsHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;

public class BurningMobEnchant extends MobEnchant {
	public BurningMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity entity) {
		executeIfPresentWithLevel(entity, ModMobEnchants.BURNING.get(), (level) -> {
			MobProps comboCap = MobPropsHelper.getMobPropsCapability(entity);
			if (comboCap == null)
				return;
			int burnNearbyTimer = comboCap.getBurnNearbyTimer();
			if (burnNearbyTimer <= 0) {
				PROXY.spawnParticles(entity, ParticleTypes.FLAME);
				applyToNearbyEntities(entity, 1.5F,
						getCanApplyToEnemyPredicate(entity),
						(LivingEntity nearbyEntity) -> {
							nearbyEntity.hurt(nearbyEntity.damageSources().onFire(),
									0.5F * level);
							PROXY.spawnParticles(nearbyEntity, ParticleTypes.FLAME);
						});
				comboCap.setBurnNearbyTimer(20);
			} else {
				comboCap.setBurnNearbyTimer(burnNearbyTimer - 1);
			}
		});
	}
}
