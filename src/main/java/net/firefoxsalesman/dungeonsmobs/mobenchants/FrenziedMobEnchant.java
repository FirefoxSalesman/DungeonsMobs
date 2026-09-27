package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FrenziedMobEnchant extends MobEnchant {
	public FrenziedMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, Entity entity, float amount, LivingDamageEvent event) {
		if (entity instanceof LivingEntity attacker) {
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.FRENZIED.get(),
					(level) -> {
						if (attacker.getHealth() <= attacker.getMaxHealth() / 2)
							event.setAmount(amount + (amount * .1F * level));
					});
		}
	}
}
