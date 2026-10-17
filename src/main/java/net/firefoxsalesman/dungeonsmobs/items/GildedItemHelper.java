package net.firefoxsalesman.dungeonsmobs.items;

import java.util.List;

import com.google.common.collect.Lists;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.utils.RandomHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, value = Dist.CLIENT)
public class GildedItemHelper {
	public static ItemStack getGildedItem(RandomSource random, ItemStack itemStack) {
		List<Enchantment> list1 = getAvailableEnchantmentResults(itemStack, true);
		Enchantment enchantment = RandomHelper.getRandomElement(random, list1);
		itemStack.enchant(enchantment, enchantment.getMaxLevel() + 1);
		itemStack.setHoverName(Component.translatable("dungeonsmobs.gilded").append(" ")
				.append(itemStack.getHoverName()));
		return itemStack;
	}

	private static List<Enchantment> getAvailableEnchantmentResults(ItemStack itemStack, boolean includeTreasures) {
		List<Enchantment> list = Lists.newArrayList();
		boolean flag = itemStack.getItem() == Items.BOOK;

		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if ((!enchantment.isTreasureOnly() || includeTreasures) && enchantment.isDiscoverable()
					&& (enchantment.canApplyAtEnchantingTable(itemStack)
							|| (flag && enchantment.isAllowedOnBooks()))
					&& enchantment.getMaxLevel() > 1) {
				list.add(enchantment);
			}
		}

		return list;
	}
}
