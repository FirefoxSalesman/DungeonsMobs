package net.firefoxsalesman.dungeonsmobs.lib.items.interfaces;

import net.minecraft.world.item.ItemStack;

public interface IRangedWeapon {

    boolean isUnique();

    default boolean shootsHeavyArrows(ItemStack stack) {
        return false;
    }

    default boolean hasExtraMultishot(ItemStack stack) {
        return false;
    }

    default boolean hasMultishotWhenCharged(ItemStack stack) {
        return false;
    }

}
