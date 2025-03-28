package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;

public class SummonSpotModel<T extends SummonSpotEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		if (entity.getSummonType() == 0) {
			return new ResourceLocation(MOD_ID, "animations/illusioner_summon_spot.animation.json");
		} else if (entity.getSummonType() == 1) {
			return new ResourceLocation(MOD_ID, "animations/wildfire_summon_spot.animation.json");
		} else if (entity.getSummonType() == 2) {
			return new ResourceLocation(MOD_ID, "animations/illusioner_summon_spot.animation.json");
		} else if (entity.getSummonType() == 3) {
			return new ResourceLocation(MOD_ID, "animations/illusioner_summon_spot.animation.json");
		} else {
			return new ResourceLocation(MOD_ID, "animations/illusioner_summon_spot.animation.json");
		}
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		if (entity.getSummonType() == 0) {
			return new ResourceLocation(MOD_ID, "geo/illusioner_summon_spot.geo.json");
		} else if (entity.getSummonType() == 1) {
			return new ResourceLocation(MOD_ID, "geo/wildfire_summon_spot.geo.json");
		} else if (entity.getSummonType() == 2) {
			return new ResourceLocation(MOD_ID, "geo/illusioner_summon_spot.geo.json");
		} else if (entity.getSummonType() == 3) {
			return new ResourceLocation(MOD_ID, "geo/illusioner_summon_spot.geo.json");
		} else {
			return new ResourceLocation(MOD_ID, "geo/illusioner_summon_spot.geo.json");
		}
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		if (entity.getSummonType() == 0) {
			return new ResourceLocation(MOD_ID, "textures/entity/illusioner_summon_spot.png");
		} else if (entity.getSummonType() == 1) {
			return new ResourceLocation(MOD_ID, "textures/entity/wildfire_summon_spot.png");
		} else if (entity.getSummonType() == 2) {
			return new ResourceLocation(MOD_ID, "textures/entity/necromancer_summon_spot.png");
		} else if (entity.getSummonType() == 3) {
			return new ResourceLocation(MOD_ID, "textures/entity/mage_summon_spot.png");
		} else {
			return new ResourceLocation(MOD_ID, "textures/entity/illusioner_summon_spot.png");
		}
	}
}
