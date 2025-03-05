package net.firefoxsalesman.dungeonsmobs.tags;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTags {
    public static final TagKey<EntityType<?>> DONT_SHIELD_AGAINST = tag("dont_shield_against");

    public static final TagKey<EntityType<?>> CONVERTS_IN_WATER = tag("converts_in_water");

    public static final TagKey<EntityType<?>> PLANT_MOBS = tag("plant_mobs");

    public static final TagKey<EntityType<?>> PIGLINS = tag("piglins");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Dungeonsmobs.MOD_ID, name));
    }

    public static void register() {
        // NOOP
    }
}
