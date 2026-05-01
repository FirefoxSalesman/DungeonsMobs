package net.firefoxsalesman.dungeonsmobs.client.models.summonables;
// Made with Blockbench 3.6.6

// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.ConstructEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class GeomancerConstructModel<T extends ConstructEntity & GeoAnimatable> extends GeoModel<T> {
	private String texturePath;

	public GeomancerConstructModel(String texturePath) {
		this.texturePath = texturePath;
	}

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		return modLoc("animations/geomancer_pillar.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		return modLoc("geo/geomancer_pillar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		return modLoc(texturePath);
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
	}
}
