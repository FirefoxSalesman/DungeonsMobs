package net.firefoxsalesman.dungeonsmobs.items;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class PiglinHelmetItem extends ArmorItem {

    public PiglinHelmetItem(ArmorMaterial armorMaterial, Type slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return String.format(DungeonsMobs.MOD_ID + ":textures/models/armor/%s.png", ForgeRegistries.ITEMS.getKey(this).getPath());
    }
}
