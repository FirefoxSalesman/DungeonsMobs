package net.firefoxsalesman.dungeonsmobs.lib.utils;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, path);
    }

    public static ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }
}
