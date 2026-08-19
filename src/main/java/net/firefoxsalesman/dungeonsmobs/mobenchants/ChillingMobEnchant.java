package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;
import static net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants.CHILLING;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobProps;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobPropsHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class ChillingMobEnchant extends MobEnchant {

	public ChillingMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void OnLivingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();

		executeIfPresentWithLevel(entity, CHILLING.get(), (level) -> {
			MobProps comboCap = MobPropsHelper.getMobPropsCapability(entity);
			if (comboCap == null)
				return;
			int freezeNearbyTimer = comboCap.getFreezeNearbyTimer();
			if (freezeNearbyTimer <= 0) {
				PROXY.spawnParticles(entity, ParticleTypes.ITEM_SNOWBALL);
				applyToNearbyEntities(entity, 1.5F,
						getCanApplyToEnemyPredicate(entity), (LivingEntity nearbyEntity) -> {
							freezeEnemy(1, nearbyEntity, level);
							PROXY.spawnParticles(nearbyEntity, ParticleTypes.ITEM_SNOWBALL);
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
