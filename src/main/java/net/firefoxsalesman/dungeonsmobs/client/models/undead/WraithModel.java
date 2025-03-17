package net.firefoxsalesman.dungeonsmobs.client.models.undead;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class WraithModel extends GeoModel<WraithEntity> {

	@Override
	public ResourceLocation getAnimationResource(WraithEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/wraith.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WraithEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/wraith.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WraithEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/wraith/wraith.png");
	}

	@Override
	public void setCustomAnimations(WraithEntity entity, long uniqueID,
			AnimationState<WraithEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");

		cape.setHidden(true);

		CoreGeoBone leftHand = this.getAnimationProcessor().getBone("bipedHandLeft");
		CoreGeoBone rightHand = this.getAnimationProcessor().getBone("bipedHandRight");

		if (entity.tickCount % 2 == 0 && rightHand instanceof CoreGeoBone && leftHand instanceof CoreGeoBone
				&& entity.isSpellcasting()) {
			CoreGeoBone leftHandBone = ((CoreGeoBone) leftHand);
			CoreGeoBone rightHandBone = ((CoreGeoBone) rightHand);
			entity.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, leftHandBone.getPosX(),
					leftHandBone.getPosY(), leftHandBone.getPosZ(),
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
			entity.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, rightHandBone.getPosX(),
					rightHandBone.getPosY(), rightHandBone.getPosZ(),
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
		}

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);

		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}
}
