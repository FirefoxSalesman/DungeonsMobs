package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
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

// Model and animation received from CQR and DerToaster
public class MountaineerModel extends GeoModel<MountaineerEntity> {

	@Override
	public ResourceLocation getAnimationResource(MountaineerEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/vindicator.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MountaineerEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/geo_illager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MountaineerEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/mountaineer.png");
	}

	@Override
	public void setCustomAnimations(MountaineerEntity entity, long uniqueID,
			AnimationState<MountaineerEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone illagerArms = this.getAnimationProcessor().getBone("illagerArms");

		illagerArms.setHidden(true);

		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");
		cape.setHidden(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() != entity.getArmorSet().getChest()
				.get());

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}

	@Override
	public void applyMolangQueries(MountaineerEntity animatable, double currentTick) {
		super.applyMolangQueries(animatable, currentTick);

		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 20);
	}
}
