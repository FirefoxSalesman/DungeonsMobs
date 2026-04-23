package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.BuzzyNestEntity;

public class BuzzyNestModel extends GeoModel<BuzzyNestEntity> {

	@Override
	public ResourceLocation getAnimationResource(BuzzyNestEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/buzzy_nest.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BuzzyNestEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/buzzy_nest.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BuzzyNestEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/buzzy_nest.png");
	}
}
