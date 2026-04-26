package net.firefoxsalesman.dungeonsmobs.gear.client.models.projectiles;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import net.firefoxsalesman.dungeonsmobs.gear.entities.SoulWizardOrbEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SoulWizardOrbModel extends GeoModel<SoulWizardOrbEntity> {

	@Override
	public ResourceLocation getAnimationResource(SoulWizardOrbEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/soul_wizard_orb.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SoulWizardOrbEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/soul_wizard_orb.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SoulWizardOrbEntity entity) {
		return new ResourceLocation(MOD_ID,
				"textures/entity/projectile/soul_wizard_orb_" + entity.textureChange % 2 + ".png");
	}

	@Override
	public void setCustomAnimations(SoulWizardOrbEntity animatable, long instanceId,
			AnimationState<SoulWizardOrbEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);
		CoreGeoBone everything = this.getAnimationProcessor().getBone("everything");

		everything.setRotY(-1.5708F);
	}
}
