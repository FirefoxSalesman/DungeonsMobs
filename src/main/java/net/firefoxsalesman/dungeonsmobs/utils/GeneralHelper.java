package net.firefoxsalesman.dungeonsmobs.utils;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import dev.obscuria.fragmentum.api.ModCompat;

public class GeneralHelper {
	public static ModCompat DUNGEONSGEAR = ModCompat.Companion.create("dungeonsgear");

	public static ResourceLocation modLoc(String resource) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, resource);
	}
}
