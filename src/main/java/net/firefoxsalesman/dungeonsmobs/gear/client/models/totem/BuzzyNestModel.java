package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import static net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.gear.entities.BuzzyNestEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BuzzyNestModel extends GeoModel<BuzzyNestEntity> {

	@Override
	public ResourceLocation getAnimationResource(BuzzyNestEntity entity) {
		return modLoc("animations/buzzy_nest.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BuzzyNestEntity entity) {
		return modLoc("geo/buzzy_nest.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BuzzyNestEntity entity) {
		return modLoc("textures/entity/buzzy_nest.png");
	}
}
