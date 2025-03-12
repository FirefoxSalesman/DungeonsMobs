package net.firefoxsalesman.dungeonsmobs.client.models.golem;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SquallGolemModel extends GeoModel<SquallGolemEntity> {

	@Override
	public ResourceLocation getAnimationResource(SquallGolemEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/squall_golem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SquallGolemEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/squall_golem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SquallGolemEntity entity) {
		// ChorusGormandizerEntity entityIn = (ChorusGormandizerEntity) entity;
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/golem/squall_golem.png");
	}

	@Override
	public void setCustomAnimations(SquallGolemEntity entity, long uniqueID,
			AnimationState<SquallGolemEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("head");
		CoreGeoBone eye = this.getAnimationProcessor().getBone("head2");
		CoreGeoBone eyeBrow = this.getAnimationProcessor().getBone("head3");
		if (head != null) {
			EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
			eye.setPosY(Math.max(Math.min(extraData.headPitch() / 80, 0.15F), -0.2F));
			eyeBrow.setPosY(Math.max(Math.min(extraData.headPitch() / 80, 0.15F), -0.2F));
		}
		// if (extraData.headPitch != 0 || extraData.netHeadYaw != 0) {
		// eye.setPositionX((float) Math.max(Math.min(
		// (extraData.netHeadYaw / 80) + Math.sin(eye.getPositionX() * Math.PI / 180F),
		// 1),
		// -1));
		// head.setRotationX(head.getRotationX() + (extraData.headPitch * ((float)
		// Math.PI / 180F)));
		// head.setRotationZ(head.getRotationZ() + (extraData.netHeadYaw * ((float)
		// Math.PI / 180F)));
		// eye.setPositionY(Math.max(Math.min(extraData.headPitch / 80, 0.15F), -0.2F));
		// eyeBrow.setPositionY(Math.max(Math.min(extraData.headPitch / 80, 0.15F),
		// -0.2F));
		// }
	}

}
