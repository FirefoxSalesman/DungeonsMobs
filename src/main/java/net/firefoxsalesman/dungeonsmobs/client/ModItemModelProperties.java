package net.firefoxsalesman.dungeonsmobs.client;

import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemModelProperties {

    public static void registerProperties() {
        ItemProperties.register(ModItems.ROYAL_GUARD_SHIELD.get(),
                new ResourceLocation("blocking"),
                (stack, clientWorld, livingEntity, i) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ModItems.VANGUARD_SHIELD.get(),
                new ResourceLocation("blocking"),
                (stack, clientWorld, livingEntity, i) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
                });
    }
}
