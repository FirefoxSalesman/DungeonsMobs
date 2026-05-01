package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TridentStormModel extends GeoModel<TridentStormEntity> {

	@Override
	public ResourceLocation getAnimationResource(TridentStormEntity entity) {
		return modLoc("animations/trident_storm.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TridentStormEntity entity) {
		return modLoc("geo/trident_storm.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TridentStormEntity entity) {
		return modLoc("textures/entity/trident_storm.png");
	}
}
