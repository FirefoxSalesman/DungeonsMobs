package net.firefoxsalesman.dungeonsmobs.gear.utilities;

import net.minecraft.resources.ResourceLocation;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class GeneralHelper {
	public static ResourceLocation modLoc(String resource) {
		return new ResourceLocation(MOD_ID, resource);
	}
}
