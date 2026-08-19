package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanHealPredicate;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;
import static net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants.HEALS_ALLIES;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class HealsAlliesMobEnchant extends MobEnchant {

	private static final float HEAL_PERCENTAGE = 0.10F;

	public HealsAlliesMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		LivingEntity defender = event.getEntity();
		executeIfPresentWithLevel(defender, HEALS_ALLIES.get(), (level) -> {
			applyToNearbyEntities(defender, 1.5F,
					getCanHealPredicate(defender), (LivingEntity nearbyEntity) -> nearbyEntity
							.heal(event.getAmount() * HEAL_PERCENTAGE * level));
		});
	}
}
