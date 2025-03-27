package net.firefoxsalesman.dungeonsmobs.lib.capabilities.builtinenchantments;

import net.minecraft.world.item.ItemStack;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities.BUILT_IN_ENCHANTMENTS_CAPABILITY;


public class BuiltInEnchantmentsHelper {

    public static BuiltInEnchantments getBuiltInEnchantmentsCapability(ItemStack itemStack) {
        return itemStack.getCapability(BUILT_IN_ENCHANTMENTS_CAPABILITY).orElse(new BuiltInEnchantments(itemStack));
    }

}
