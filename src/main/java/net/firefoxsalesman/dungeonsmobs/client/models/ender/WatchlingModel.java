package net.firefoxsalesman.dungeonsmobs.client.models.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class WatchlingModel extends GeoModel<AbstractEnderlingEntity> {

	@Override
	public ResourceLocation getAnimationResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/watchling.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/watchling.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/watchling.png");
	}

	@Override
	public void setCustomAnimations(AbstractEnderlingEntity entity, long uniqueID,
			AnimationState<AbstractEnderlingEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone head = this.getAnimationProcessor().getBone("head");

		LivingEntity entityIn = (LivingEntity) entity;
		// EntityModelData extraData = customPredicate.getData(new DataTicket<>("0", EntityModelData.class));
		// if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
		// 	head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
		// 	head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		// }
	}
}
