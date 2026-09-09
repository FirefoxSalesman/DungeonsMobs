package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class CommittedMobEnchant extends MobEnchant {
	public CommittedMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, LivingEntity attacker, LivingDamageEvent event) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.COMMITTED.get(),
				(level) -> {
					if (defender.getHealth() >= defender.getMaxHealth())
						return;
					event.setAmount(event.getAmount() + (event.getAmount() * .25F * (level + 1)));

				});
	}
}
