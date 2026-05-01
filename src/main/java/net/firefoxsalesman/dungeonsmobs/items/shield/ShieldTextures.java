package net.firefoxsalesman.dungeonsmobs.items.shield;

import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

public class ShieldTextures {

	public static final Material LOCATION_ROYAL_GUARD_SHIELD_BASE = makeMaterial("royal_guard_shield_base");
	public static final Material LOCATION_ROYAL_GUARD_SHIELD_NO_PATTERN = makeMaterial(
			"royal_guard_shield_base_no_pattern");
	public static final Material LOCATION_VANGUARD_SHIELD = makeMaterial("vanguard_shield");

	private static Material makeMaterial(String location) {
		return new Material(TextureAtlas.LOCATION_BLOCKS, GeneralHelper.modLoc("item/" + location));
	}
}
