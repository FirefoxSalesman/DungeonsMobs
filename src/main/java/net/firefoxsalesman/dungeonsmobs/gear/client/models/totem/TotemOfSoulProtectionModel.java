package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import static net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfSoulProtectionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;;

public class TotemOfSoulProtectionModel extends GeoModel<TotemOfSoulProtectionEntity> {

	@Override
	public ResourceLocation getAnimationResource(TotemOfSoulProtectionEntity entity) {
		return modLoc("animations/totem_of_soul_protection.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemOfSoulProtectionEntity entity) {
		return modLoc("geo/totem_of_soul_protection.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemOfSoulProtectionEntity entity) {
		return modLoc("textures/entity/totem_of_soul_protection.png");
	}
}
