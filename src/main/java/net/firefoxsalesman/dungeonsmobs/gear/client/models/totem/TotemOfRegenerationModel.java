package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfRegenerationEntity;

public class TotemOfRegenerationModel extends GeoModel<TotemOfRegenerationEntity> {

	@Override
	public ResourceLocation getAnimationResource(TotemOfRegenerationEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/totem_of_regeneration.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemOfRegenerationEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/totem_of_regeneration.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemOfRegenerationEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/totem_of_regeneration.png");
	}
}
