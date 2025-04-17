package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class RoyalGuardModel extends GeoModel<RoyalGuardEntity> {

	@Override
	public ResourceLocation getAnimationResource(RoyalGuardEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/royal_guard.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RoyalGuardEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/geo_illager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RoyalGuardEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/royal_guard.png");
	}

	@Override
	public void setCustomAnimations(RoyalGuardEntity entity, long uniqueID,
			AnimationState<RoyalGuardEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = getAnimationProcessor().getBone("bipedHeadBaseRotator");
		CoreGeoBone illagerArms = getAnimationProcessor().getBone("illagerArms");
		CoreGeoBone cape = getAnimationProcessor().getBone("bipedCape");

		illagerArms.setHidden(true);
		cape.setHidden(true);

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}

	@Override
	public void applyMolangQueries(RoyalGuardEntity animatable, double animTime) {
		super.applyMolangQueries(animatable, animTime);
		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 20);
	}
}
