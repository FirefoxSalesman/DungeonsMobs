package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.resources.ResourceLocation;

public class QuickGrowingVineModel extends AbstractVineModel {
	@Override
	public ResourceLocation getAnimationResource(AbstractVineEntity entity) {
		return modLoc("animations/quick_growing_vine.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbstractVineEntity entity) {
		return modLoc("geo/quick_growing_vine.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbstractVineEntity entity) {
		return modLoc("textures/entity/jungle/quick_growing_vine.png");
	}
}
