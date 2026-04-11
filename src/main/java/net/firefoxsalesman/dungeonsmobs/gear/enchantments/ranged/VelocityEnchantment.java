package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.event.BowEvent;
import net.firefoxsalesman.dungeonsmobs.lib.event.CrossbowEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class VelocityEnchantment extends DungeonsEnchantment {

	public VelocityEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return false;
	}

	@SubscribeEvent
	public static void onBowVelocity(BowEvent.Velocity event) {
		ItemStack itemStack = event.getItemStack();
		int velocityLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.VELOCITY.get(),
				itemStack);
		if (velocityLevel > 0) {
			event.setVelocity(event.getVelocity() + 0.8F * velocityLevel);
		}
	}

	@SubscribeEvent
	public static void onCrossbowVelocity(CrossbowEvent.Velocity event) {
		ItemStack itemStack = event.getItemStack();
		int velocityLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.VELOCITY.get(),
				itemStack);
		if (velocityLevel > 0) {
			event.setVelocity(event.getVelocity() + 0.8F * velocityLevel);
		}
	}

}
