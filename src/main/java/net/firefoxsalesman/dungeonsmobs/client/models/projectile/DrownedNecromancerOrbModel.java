package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.DrownedNecromancerOrbEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class DrownedNecromancerOrbModel extends GeoModel<DrownedNecromancerOrbEntity> {

	@Override
	public ResourceLocation getAnimationResource(DrownedNecromancerOrbEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/drowned_necromancer_orb.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DrownedNecromancerOrbEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/drowned_necromancer_orb.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DrownedNecromancerOrbEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/projectile/drowned_necromancer_orb_"
				+ entity.textureChange % 2 + ".png");
	}
}
