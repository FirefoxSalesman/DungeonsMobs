package net.firefoxsalesman.dungeonsmobs.client.models.redstone;
// Made with Blockbench 3.6.6

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RedstoneGolemModel extends GeoModel<RedstoneGolemEntity> {

	@Override
	public ResourceLocation getAnimationResource(RedstoneGolemEntity entity) {
		return modLoc("animations/redstone_golem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RedstoneGolemEntity entity) {
		return modLoc("geo/redstone_golem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RedstoneGolemEntity entity) {
		return modLoc("textures/entity/redstone/redstone_golem.png");
	}

	@Override
	public void setCustomAnimations(RedstoneGolemEntity entity, long uniqueID,
			AnimationState<RedstoneGolemEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}
