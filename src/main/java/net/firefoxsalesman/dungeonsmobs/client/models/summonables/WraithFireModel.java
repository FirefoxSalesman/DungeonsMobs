package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WraithFireModel extends GeoModel<WraithFireEntity> {

	@Override
	public ResourceLocation getAnimationResource(WraithFireEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/wraith_fire.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WraithFireEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/wraith_fire.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WraithFireEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID,
				"textures/entity/wraith_fire/wraith_fire_" + entity.textureChange % 31 + ".png");
	}
}
