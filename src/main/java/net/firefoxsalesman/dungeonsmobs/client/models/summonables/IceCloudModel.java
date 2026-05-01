package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IceCloudModel extends GeoModel<IceCloudEntity> {

	@Override
	public ResourceLocation getAnimationResource(IceCloudEntity entity) {
		return modLoc("animations/ice_chunk.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IceCloudEntity entity) {
		return modLoc("geo/ice_chunk.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IceCloudEntity entity) {
		return modLoc("textures/entity/ice_chunk.png");
	}
}
