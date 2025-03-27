package net.firefoxsalesman.dungeonsmobs.utils;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;

public class GeneralHelper {
    public static ResourceLocation modLoc(String resource) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, resource);
    }
}
