package net.firefoxsalesman.dungeonsmobs.client.models.blaze;

import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
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

public class WildfireModel extends GeoModel<WildfireEntity> {

	@Override
	public ResourceLocation getAnimationResource(WildfireEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/wildfire.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WildfireEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/wildfire.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WildfireEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/blaze/wildfire.png");
	}

	@Override
	public void setCustomAnimations(WildfireEntity entity, long uniqueID,
			AnimationState<WildfireEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("head");

		CoreGeoBone shield1 = this.getAnimationProcessor().getBone("shield1");
		CoreGeoBone shield2 = this.getAnimationProcessor().getBone("shield2");
		CoreGeoBone shield3 = this.getAnimationProcessor().getBone("shield3");
		CoreGeoBone shield4 = this.getAnimationProcessor().getBone("shield4");

		WildfireEntity wildfire = ((WildfireEntity) entity);

		if (wildfire.getShields() >= 4) {
			shield1.setHidden(false);
			shield2.setHidden(false);
			shield3.setHidden(false);
			shield4.setHidden(false);
		} else if (wildfire.getShields() == 3) {
			shield1.setHidden(true);
			shield2.setHidden(false);
			shield3.setHidden(false);
			shield4.setHidden(false);
		} else if (wildfire.getShields() == 2) {
			shield1.setHidden(true);
			shield2.setHidden(true);
			shield3.setHidden(false);
			shield4.setHidden(false);
		} else if (wildfire.getShields() == 1) {
			shield1.setHidden(true);
			shield2.setHidden(true);
			shield3.setHidden(true);
			shield4.setHidden(false);
		} else if (wildfire.getShields() <= 0) {
			shield1.setHidden(true);
			shield2.setHidden(true);
			shield3.setHidden(true);
			shield4.setHidden(true);
		}

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}

	@Override
	public void applyMolangQueries(WildfireEntity animatable, double animTime) {
		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 30);
	}
}
