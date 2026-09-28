package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RampagingMobEnchant extends MobEnchant {
	public RampagingMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity attacker) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.RAMPAGING.get(),
				level -> attacker.addEffect(
						new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100 * level, 2)));
	}
}
