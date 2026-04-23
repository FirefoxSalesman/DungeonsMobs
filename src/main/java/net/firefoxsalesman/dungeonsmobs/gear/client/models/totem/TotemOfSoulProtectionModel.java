package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfSoulProtectionEntity;;

public class TotemOfSoulProtectionModel extends GeoModel<TotemOfSoulProtectionEntity> {

	@Override
	public ResourceLocation getAnimationResource(TotemOfSoulProtectionEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/totem_of_soul_protection.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemOfSoulProtectionEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/totem_of_soul_protection.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemOfSoulProtectionEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/totem_of_soul_protection.png");
	}
}
