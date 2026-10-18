package net.firefoxsalesman.dungeonsmobs.utils;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;

public class GeneralHelper {
	public static ModHelper DUNGEONSGEAR = new ModHelper("dungeonsgear");

	public static ResourceLocation modLoc(String resource) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, resource);
	}
}
