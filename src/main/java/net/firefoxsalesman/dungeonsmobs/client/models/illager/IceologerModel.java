package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.IceologerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class IceologerModel extends GeoModel<IceologerEntity> {

	@Override
	public ResourceLocation getAnimationResource(IceologerEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/iceologer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IceologerEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/geo_illager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IceologerEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/iceologer.png");
	}

	@Override
	public void setCustomAnimations(IceologerEntity entity, long uniqueID,
			AnimationState<IceologerEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone illagerArms = this.getAnimationProcessor().getBone("illagerArms");

		illagerArms.setHidden(true);

		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");
		cape.setHidden(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() != entity.getArmorSet().getChest()
				.get());

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}

	@Override
	public void applyMolangQueries(IceologerEntity animatable, double animTime) {
		super.applyMolangQueries(animatable, animTime);
		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 20);
	}

}
