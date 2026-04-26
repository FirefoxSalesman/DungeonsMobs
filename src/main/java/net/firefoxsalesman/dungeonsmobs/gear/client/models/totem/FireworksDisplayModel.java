package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.FireworksDisplayEntity;

public class FireworksDisplayModel extends GeoModel<FireworksDisplayEntity> {

	@Override
	public ResourceLocation getAnimationResource(FireworksDisplayEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/fireworks_box.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FireworksDisplayEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/fireworks_box.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FireworksDisplayEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/fireworks_display.png");
	}
}
