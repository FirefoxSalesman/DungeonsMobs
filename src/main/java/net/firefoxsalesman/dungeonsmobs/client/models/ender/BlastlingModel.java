package net.firefoxsalesman.dungeonsmobs.client.models.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.core.animation.AnimationState;

public class BlastlingModel extends GeoModel<AbstractEnderlingEntity> {

	@Override
	public ResourceLocation getAnimationResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/blastling.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/blastling.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbstractEnderlingEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/blastling"
				+ (1 + ((int) ((BlastlingEntity) entity).flameTicks) % 3) + ".png");
	}

	@Override
	public void setCustomAnimations(AbstractEnderlingEntity entity, long uniqueID,
			AnimationState<AbstractEnderlingEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}
