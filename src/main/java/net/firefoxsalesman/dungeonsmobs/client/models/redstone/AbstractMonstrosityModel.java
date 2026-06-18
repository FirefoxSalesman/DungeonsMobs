package net.firefoxsalesman.dungeonsmobs.client.models.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.AbstractMonstrosityEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AbstractMonstrosityModel<T extends AbstractMonstrosityEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return modLoc("geo/redstone_monstrosity.v2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return modLoc("textures/entity/redstone/redstone_monstrosity_death.png");
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return modLoc("animations/redstone_monstrosity.v2.animation.json");
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID,
			AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}
