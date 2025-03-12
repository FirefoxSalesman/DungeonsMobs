package net.firefoxsalesman.dungeonsmobs.client.models.redstone;// Made with Blockbench 3.6.6

// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RedstoneMineModel extends GeoModel<RedstoneMineEntity> {

	@Override
	public ResourceLocation getAnimationResource(RedstoneMineEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/redstone_mine.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RedstoneMineEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/redstone_mine.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RedstoneMineEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/redstone/redstone_mine.png");
	}

	// @Override
	// public void setCustomAnimations(RedstoneMineEntity entity, long uniqueID,
	// AnimationState<RedstoneMineEntity> customPredicate) {
	// super.setCustomAnimations(entity, uniqueID, customPredicate);

	// }
}
