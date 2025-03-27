package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ArmorSet {
    private final ResourceLocation registryName;
    private final RegistryObject<Item> head;
    private final RegistryObject<Item> chest;
    private final RegistryObject<Item> legs;
    private final RegistryObject<Item> feet;

    public ArmorSet(ResourceLocation registryName, RegistryObject<Item> head, RegistryObject<Item> chest, RegistryObject<Item> legs, RegistryObject<Item> feet) {
        this.registryName = registryName;
        this.head = head;
        this.chest = chest;
        this.legs = legs;
        this.feet = feet;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public RegistryObject<Item> getHead() {
        return head;
    }

    public RegistryObject<Item> getChest() {
        return chest;
    }

    public RegistryObject<Item> getLegs() {
        return legs;
    }

    public RegistryObject<Item> getFeet() {
        return feet;
    }
}
