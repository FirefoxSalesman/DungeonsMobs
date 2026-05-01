package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KelpTrapModel<T extends KelpTrapEntity> extends GeoModel<T> {

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
		return modLoc("textures/entity/kelp_trap.png");
	}
}
