package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobProps;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobPropsHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;
import static net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants.BURNING;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class BurningMobEnchant extends MobEnchant {

	public BurningMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void OnLivingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();

		executeIfPresentWithLevel(entity, BURNING.get(), (level) -> {
			MobProps comboCap = MobPropsHelper.getMobPropsCapability(entity);
			if (comboCap == null)
				return;
			int burnNearbyTimer = comboCap.getBurnNearbyTimer();
			if (burnNearbyTimer <= 0) {
				PROXY.spawnParticles(entity, ParticleTypes.FLAME);
				AreaOfEffectHelper.applyToNearbyEntities(entity, 1.5F,
						AreaOfEffectHelper.getCanApplyToEnemyPredicate(entity),
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
