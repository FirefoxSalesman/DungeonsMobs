package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class WeakeningMobEnchant extends MobEnchant {
	public WeakeningMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, LivingEntity attacker) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.WEAKENING.get(),
				(level) -> {
					defender.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, level - 1));
				});
	}
}
