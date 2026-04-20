package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfShieldingEntity;

public class TotemOfShieldingModel extends GeoModel<TotemOfShieldingEntity> {

	@Override
	public ResourceLocation getAnimationResource(TotemOfShieldingEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/totem_of_shielding.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemOfShieldingEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/totem_of_shielding.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemOfShieldingEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/totem_of_shielding.png");
	}
}
