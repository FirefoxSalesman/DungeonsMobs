package net.firefoxsalesman.dungeonsmobs.client.models.redstone;
// Made with Blockbench 3.6.6

// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RedstoneMineModel extends GeoModel<RedstoneMineEntity> {

	@Override
	public ResourceLocation getAnimationResource(RedstoneMineEntity entity) {
		return modLoc("animations/redstone_mine.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RedstoneMineEntity entity) {
		return modLoc("geo/redstone_mine.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RedstoneMineEntity entity) {
		return modLoc("textures/entity/redstone/redstone_mine.png");
	}
}
