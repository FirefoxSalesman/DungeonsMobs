package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WraithFireModel extends GeoModel<WraithFireEntity> {

	@Override
	public ResourceLocation getAnimationResource(WraithFireEntity entity) {
		return modLoc("animations/wraith_fire.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WraithFireEntity entity) {
		return modLoc("geo/wraith_fire.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WraithFireEntity entity) {
		return modLoc("textures/entity/wraith_fire/wraith_fire_" + entity.textureChange % 31 + ".png");
	}
}
