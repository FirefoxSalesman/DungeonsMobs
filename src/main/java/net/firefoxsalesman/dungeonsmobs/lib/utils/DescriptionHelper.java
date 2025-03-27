package net.firefoxsalesman.dungeonsmobs.lib.utils;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.builtinenchantments.BuiltInEnchantments;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.builtinenchantments.BuiltInEnchantmentsHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfigRegistry.GEAR_CONFIG_BUILTIN_RESOURCELOCATION;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class DescriptionHelper {

	// Rewrite to a mixin inside ItemStack::getTooltipLines. Figure out a way to
	// have all styles available.
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		BuiltInEnchantments cap = BuiltInEnchantmentsHelper
				.getBuiltInEnchantmentsCapability(event.getItemStack());
		List<EnchantmentInstance> builtInEnchantments = cap
				.getBuiltInEnchantments(GEAR_CONFIG_BUILTIN_RESOURCELOCATION);
		builtInEnchantments.forEach(enchantmentInstance -> {
			event.getToolTip().add(enchantmentInstance.enchantment.getFullname(enchantmentInstance.level)
					.copy().withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FF8100"))));
		});
	}

	public static void addLoreDescription(List<Component> list, ResourceLocation registryName) {
		list.add(Component.translatable(
				"lore." + registryName.getNamespace() + "." + registryName.getPath())
				.withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
	}

	public static void addFullDescription(List<Component> list, ItemStack itemStack) {
		ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
		addLoreDescription(list, registryName);
	}
}
