package net.firefoxsalesman.dungeonsmobs.gear.client.models.ghosts;

import static net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.gear.entities.SoulWizardEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SoulWizardModel extends GeoModel<SoulWizardEntity> {

	@Override
	public ResourceLocation getAnimationResource(SoulWizardEntity entity) {
		return modLoc("animations/soul_wizard.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SoulWizardEntity entity) {
		return modLoc("geo/soul_wizard.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SoulWizardEntity entity) {
		return modLoc("textures/entity/ghosts/soul_wizard.png");
	}

	@Override
	public void setCustomAnimations(SoulWizardEntity animatable, long instanceId,
			AnimationState<SoulWizardEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}
}
