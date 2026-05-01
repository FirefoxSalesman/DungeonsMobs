package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import static net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.gear.entities.FireworksDisplayEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireworksDisplayModel extends GeoModel<FireworksDisplayEntity> {

	@Override
	public ResourceLocation getAnimationResource(FireworksDisplayEntity entity) {
		return modLoc("animations/fireworks_box.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FireworksDisplayEntity entity) {
		return modLoc("geo/fireworks_box.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FireworksDisplayEntity entity) {
		return modLoc("textures/entity/fireworks_display.png");
	}
}
