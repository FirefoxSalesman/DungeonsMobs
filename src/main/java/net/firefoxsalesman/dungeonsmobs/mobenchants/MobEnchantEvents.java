package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanHealPredicate;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class MobEnchantEvents {
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity defender = event.getEntity();
			RushMobEnchant.doEffect(defender);
			HealsAlliesMobEnchant.doEffect(defender, event.getAmount());
			// radiance
			Entity attacker;
			if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
				attacker = event.getSource().getDirectEntity();
			} else {
				attacker = event.getSource().getEntity();
			}
			if (attacker instanceof LivingEntity)
				executeIfPresentWithLevel((LivingEntity) attacker, ModMobEnchants.RADIANCE.get(),
						(level) -> {
							LivingEntity source = event.getSource()
									.is(DamageTypeTags.IS_PROJECTILE)
											? event.getEntity()
											: (LivingEntity) attacker;
							applyToNearbyEntities(source, 1.5F,
									getCanHealPredicate(source),
									(LivingEntity nearbyEntity) -> {
										nearbyEntity.heal(level);
										PROXY.spawnParticles(nearbyEntity,
												ParticleTypes.HEART);
									});
						});
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity entity = event.getEntity();
			BurningMobEnchant.doEffect(entity);
			ChillingMobEnchant.doEffect(entity);
			GravityPulseMobEnchant.doEffect(entity);
			RegenerationMobEnchant.doEffect(entity);
		}
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity defender = event.getEntity();
			Entity entity = event.getSource().getEntity();
			EchoMobEnchant.doEffect(defender, entity, event.getSource(), event.getAmount());
		}
	}
}
