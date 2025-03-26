package net.firefoxsalesman.dungeonsmobs.client.models.ender;

import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class EndersentModel extends GeoModel<AbstractEnderlingEntity> {

	@Override
	public ResourceLocation getAnimationResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/endersent.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/endersent.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/ender/endersent.png");
	}

	@Override
	public void setCustomAnimations(AbstractEnderlingEntity entity, long uniqueID,
			AnimationState<AbstractEnderlingEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone head = getAnimationProcessor().getBone("head");

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}
}
