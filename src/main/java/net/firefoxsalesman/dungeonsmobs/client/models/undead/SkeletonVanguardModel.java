package net.firefoxsalesman.dungeonsmobs.client.models.undead;

import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
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

public class SkeletonVanguardModel extends GeoModel<SkeletonVanguardEntity> {

	@Override
	public ResourceLocation getAnimationResource(SkeletonVanguardEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/skeleton_vanguard.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SkeletonVanguardEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/geo_skeleton.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SkeletonVanguardEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/skeleton/skeleton_vanguard.png");
	}

	@Override
	public void setCustomAnimations(SkeletonVanguardEntity entity, long uniqueID,
			AnimationState<SkeletonVanguardEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");

		cape.setHidden(true);

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}

	@Override
	public void applyMolangQueries(SkeletonVanguardEntity animatable, double animTime) {
		super.applyMolangQueries(animatable, animTime);

		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 20);
	}
}
