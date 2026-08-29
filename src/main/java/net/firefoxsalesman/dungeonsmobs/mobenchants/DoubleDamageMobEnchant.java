package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class DoubleDamageMobEnchant extends MobEnchant {
	public DoubleDamageMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, LivingEntity attacker, LivingDamageEvent event) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.DOUBLE_DAMAGE.get(),
				(level) -> event.setAmount(event.getAmount() * 2));
	}
}
