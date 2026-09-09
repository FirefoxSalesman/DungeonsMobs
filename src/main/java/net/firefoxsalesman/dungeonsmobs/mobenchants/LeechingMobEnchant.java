package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;

public class LeechingMobEnchant extends MobEnchant {
	public LeechingMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity attacker, LivingEntity defender) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.LEECHING.get(),
				level -> attacker.heal((0.03F + (0.02F * level)) * defender.getMaxHealth()));
	}
}
