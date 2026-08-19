package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanHealPredicate;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class RadianceMobEnchant extends MobEnchant {

	public RadianceMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		Entity attacker;
		if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
			attacker = event.getSource().getDirectEntity();
		} else {
			attacker = event.getSource().getEntity();
		}
		if (attacker instanceof LivingEntity)
			executeIfPresentWithLevel((LivingEntity) attacker, ModMobEnchants.RADIANCE.get(), (level) -> {
				LivingEntity source = event.getSource().is(DamageTypeTags.IS_PROJECTILE)
						? event.getEntity()
						: (LivingEntity) attacker;
				applyToNearbyEntities(source, 1.5F,
						getCanHealPredicate(source), (LivingEntity nearbyEntity) -> {
							nearbyEntity.heal(level);
							PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
						});
			});
	}
}
