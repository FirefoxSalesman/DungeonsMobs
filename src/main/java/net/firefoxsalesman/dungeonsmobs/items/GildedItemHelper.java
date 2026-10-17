package net.firefoxsalesman.dungeonsmobs.items;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, value = Dist.CLIENT)
public class GildedItemHelper {
	public static ItemStack getGildedItem(RandomSource random, ItemStack itemStack) {
		List<EnchantmentInstance> list1 = getAvailableEnchantmentResults(1, 1, itemStack, true);
		System.out.println(list1);
		Optional<EnchantmentInstance> randomItem = WeightedRandom.getRandomItem(random, list1, list1.size());
		randomItem.ifPresent(randomEnchantment -> {
			Enchantment enchantment = randomEnchantment.enchantment;
			itemStack.enchant(enchantment, enchantment.getMaxLevel() + 1);
			itemStack.setHoverName(Component.translatable("dungeonsmobs.gilded").append(" ")
					.append(itemStack.getHoverName()));
		});
		return itemStack;
	}

	private static List<EnchantmentInstance> getAvailableEnchantmentResults(int minLevel, int maxLevel,
			ItemStack itemStack, boolean includeTreasures) {
		List<EnchantmentInstance> list = Lists.newArrayList();
		boolean flag = itemStack.getItem() == Items.BOOK;

		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if ((!enchantment.isTreasureOnly() || includeTreasures) && enchantment.isDiscoverable()
					&& (enchantment.canApplyAtEnchantingTable(itemStack)
							|| (flag && enchantment.isAllowedOnBooks()))) {
				for (int i = Math.min(enchantment.getMaxLevel(), maxLevel); i > Math
						.min(enchantment.getMinLevel(), minLevel) - 1; --i) {
					list.add(new EnchantmentInstance(enchantment, i));
				}
			}
		}

		return list;
	}
}
