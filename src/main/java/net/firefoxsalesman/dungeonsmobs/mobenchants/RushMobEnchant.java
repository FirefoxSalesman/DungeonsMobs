package net.firefoxsalesman.dungeonsmobs.mobenchants;

import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RushMobEnchant extends MobEnchant {

	public RushMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender) {
		NewMobEnchantUtils.executeIfPresentWithLevel(defender, ModMobEnchants.RUSH.get(), (level) -> {
			defender.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 + 20 * level, 3,
					false,
					false));
		});
	}
}
