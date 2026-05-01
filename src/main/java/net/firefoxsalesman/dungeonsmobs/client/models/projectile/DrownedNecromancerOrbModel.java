package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.DrownedNecromancerOrbEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DrownedNecromancerOrbModel extends GeoModel<DrownedNecromancerOrbEntity> {

	@Override
	public ResourceLocation getAnimationResource(DrownedNecromancerOrbEntity entity) {
		return modLoc("animations/drowned_necromancer_orb.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DrownedNecromancerOrbEntity entity) {
		return modLoc("geo/drowned_necromancer_orb.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DrownedNecromancerOrbEntity entity) {
		return modLoc("textures/entity/projectile/drowned_necromancer_orb_" + entity.textureChange % 2
				+ ".png");
	}
}
