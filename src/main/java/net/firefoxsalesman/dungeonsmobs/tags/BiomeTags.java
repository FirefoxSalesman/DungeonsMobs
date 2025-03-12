package net.firefoxsalesman.dungeonsmobs.tags;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeTags {

    public static final TagKey<Biome> MOUNTAINEEER_RAIDS_IN = tag("mountaineer_raids_in");
    public static final TagKey<Biome> ICEOLOGER_RAIDS_IN = tag("iceologer_raids_in");
    public static final TagKey<Biome> WINDCALLER_RAIDS_IN = tag("windcaller_raids_in");
    public static final TagKey<Biome> SQUALL_GOLEM_RAIDS_IN = tag("squall_golem_raids_in");

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(DungeonsMobs.MOD_ID, name));
    }

    private static TagKey<Biome> forgeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation("forge", name));
    }

    public static void register() {
        // NOOP
    }
}
