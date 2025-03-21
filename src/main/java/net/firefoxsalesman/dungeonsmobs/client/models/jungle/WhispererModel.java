package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WhispererEntity;
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

public class WhispererModel<T extends WhispererEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/whisperer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/whisperer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/jungle/whisperer.png");
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("jaw");
		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");

		cape.setHidden(true);

		CoreGeoBone leftHand = this.getAnimationProcessor().getBone("bipedHandLeft");
		CoreGeoBone rightHand = this.getAnimationProcessor().getBone("bipedHandRight");

		if (entity.tickCount % 1 == 0 && rightHand instanceof CoreGeoBone && leftHand instanceof CoreGeoBone
				&& entity.isSpellcasting()) {
			CoreGeoBone leftHandBone = ((CoreGeoBone) leftHand);
			CoreGeoBone rightHandBone = ((CoreGeoBone) rightHand);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_MAGIC.get(),
					leftHandBone.getPosX(), leftHandBone.getPosY(),
					leftHandBone.getPosZ(), 0, 0, 0);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_MAGIC.get(),
					rightHandBone.getPosX(), rightHandBone.getPosY(),
					rightHandBone.getPosZ(), 0, 0, 0);
		}

		if (entity.tickCount % 2 == 0 && rightHand instanceof CoreGeoBone && leftHand instanceof CoreGeoBone
				&& entity.isSpellcasting()) {
			CoreGeoBone leftHandBone = ((CoreGeoBone) leftHand);
			CoreGeoBone rightHandBone = ((CoreGeoBone) rightHand);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_DUST.get(),
					leftHandBone.getPosX(), leftHandBone.getPosY(),
					leftHandBone.getPosZ(), entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_DUST.get(),
					rightHandBone.getPosX(), rightHandBone.getPosY(),
					rightHandBone.getPosZ(), entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
		}

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}

	@Override
	public void applyMolangQueries(WhispererEntity animatable, double currentTick) {
		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 12.5);
	}
}
