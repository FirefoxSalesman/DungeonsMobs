package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.event.BowEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class OverchargeEnchantment extends DungeonsEnchantment {

	public OverchargeEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.BOW, ModEnchantmentTypes.WEAPON_SLOT);
	}

	@SubscribeEvent
	public static void onOverchargeBow(BowEvent.Overcharge event) {
		LivingEntity livingEntity = event.getEntity();
		int overchargeLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.OVERCHARGE.get(),
				livingEntity);
		if (overchargeLevel > 0) {
			event.setCharges(event.getCharges() + overchargeLevel);
		}
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return enchantment != Enchantments.QUICK_CHARGE
				&& enchantment != EnchantmentInit.ACCELERATE.get();
	}
}
