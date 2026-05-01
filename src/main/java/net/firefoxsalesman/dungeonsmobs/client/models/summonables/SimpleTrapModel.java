package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SimpleTrapModel<T extends SimpleTrapEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		return modLoc("animations/trap.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		return modLoc("geo/trap.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		if (entity.getTrapType() == 0) {
			return modLoc("textures/entity/web_trap.png");
		} else if (entity.getTrapType() == 1) {
			return modLoc("textures/entity/vine_trap.png");
		} else {
			return modLoc("textures/entity/web_trap.png");
		}
	}
}
