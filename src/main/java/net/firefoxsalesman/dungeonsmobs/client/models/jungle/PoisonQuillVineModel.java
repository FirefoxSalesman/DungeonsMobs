package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

public class PoisonQuillVineModel extends AbstractVineModel {
	@Override
	public ResourceLocation getAnimationResource(AbstractVineEntity entity) {
		return modLoc("animations/poison_quill_vine.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbstractVineEntity entity) {
		return modLoc("geo/poison_quill_vine.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbstractVineEntity entity) {
		return modLoc("textures/entity/jungle/poison_quill_vine.png");
	}

	@Override
	public void setCustomAnimations(AbstractVineEntity entity, long uniqueID,
			AnimationState<AbstractVineEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		CoreGeoBone headRotator = getAnimationProcessor().getBone("headRotator");

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));

			headRotator.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}
}
