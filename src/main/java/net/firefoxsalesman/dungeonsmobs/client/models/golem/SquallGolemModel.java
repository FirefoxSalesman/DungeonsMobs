package net.firefoxsalesman.dungeonsmobs.client.models.golem;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SquallGolemModel extends GeoModel<SquallGolemEntity> {

	@Override
	public ResourceLocation getAnimationResource(SquallGolemEntity entity) {
		return modLoc("animations/squall_golem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SquallGolemEntity entity) {
		return modLoc("geo/squall_golem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SquallGolemEntity entity) {
		return modLoc("textures/entity/golem/squall_golem.png");
	}

	@Override
	public void setCustomAnimations(SquallGolemEntity entity, long uniqueID,
			AnimationState<SquallGolemEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		CoreGeoBone eye = getAnimationProcessor().getBone("head2");
		CoreGeoBone eyeBrow = getAnimationProcessor().getBone("head3");
		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			eye.setPosX((float) Math.max(Math.min(
					(extraData.netHeadYaw() / 80) + Math.sin(eye.getPosX() * Math.PI / 180F), 1),
					-1));
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotZ(head.getRotZ() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
			eye.setPosY(Math.max(Math.min(extraData.headPitch() / 80, 0.15F), -0.2F));
			eyeBrow.setPosY(Math.max(Math.min(extraData.headPitch() / 80, 0.15F), 0.2F));
		}
	}

}
