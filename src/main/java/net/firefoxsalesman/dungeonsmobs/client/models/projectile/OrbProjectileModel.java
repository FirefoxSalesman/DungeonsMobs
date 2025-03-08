package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class OrbProjectileModel extends GeoModel<NecromancerOrbEntity> {

	private final boolean renderTrail;

	public OrbProjectileModel(boolean renderTrail) {
		this.renderTrail = renderTrail;
	}

	@Override
	public ResourceLocation getAnimationResource(NecromancerOrbEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/necromancer_orb.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(NecromancerOrbEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/necromancer_orb.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NecromancerOrbEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID,
				"textures/entity/projectile/orb_projectile_" + entity.textureChange % 3 + ".png");
	}

	@Override
	public void setCustomAnimations(NecromancerOrbEntity entity, long uniqueID,
			AnimationState<NecromancerOrbEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone everything = this.getAnimationProcessor().getBone("everything");
		if (!renderTrail) {
			CoreGeoBone trail = this.getAnimationProcessor().getBone("trail1");
			trail.setHidden(true);
		}

		// everything.setRotY(entity.getYRot() % 360);
		// everything.setRotX(entity.getXRot() % 360);
	}
}
