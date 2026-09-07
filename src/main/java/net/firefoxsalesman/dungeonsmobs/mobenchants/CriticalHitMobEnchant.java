package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class CriticalHitMobEnchant extends MobEnchant {
	private static final float CRIT_CHANCE = 0.05f;

	public CriticalHitMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, LivingEntity attacker, LivingDamageEvent event) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.CRITICAL_HIT.get(),
				(level) -> {
					if (attacker.getRandom().nextFloat() <= CRIT_CHANCE * (level + 1)) {
						event.setAmount(event.getAmount() * 3);
					}
				});
	}
}
