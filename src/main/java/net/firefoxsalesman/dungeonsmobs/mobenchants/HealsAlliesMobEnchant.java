package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;

public class HealsAlliesMobEnchant extends MobEnchant {
	private static final float HEAL_PERCENTAGE = 0.10F;

	public HealsAlliesMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, float amount) {
		NewMobEnchantUtils.executeIfPresentWithLevel(defender, ModMobEnchants.HEALS_ALLIES.get(), (level) -> {
			AreaOfEffectHelper.applyToNearbyEntities(defender, 1.5F,
					AreaOfEffectHelper.getCanHealPredicate(defender),
					(LivingEntity nearbyEntity) -> nearbyEntity
							.heal(amount * HEAL_PERCENTAGE * level));
		});
	}
}
