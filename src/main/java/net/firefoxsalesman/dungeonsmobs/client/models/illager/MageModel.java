package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class MageModel<T extends AbstractIllager & GeoAnimatable> extends GeoModel<T> {

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		return new ResourceLocation(MOD_ID, "animations/mage.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		return new ResourceLocation(MOD_ID, "geo/geo_illager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/mage.png");
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone illagerArms = this.getAnimationProcessor().getBone("illagerArms");

		illagerArms.setHidden(true);

		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");
		if (entity instanceof SpawnArmoredMob && entity instanceof Mob) {
			Mob mobEntity = (Mob) entity;
			cape.setHidden(mobEntity.getItemBySlot(EquipmentSlot.CHEST)
					.getItem() != ((SpawnArmoredMob) entity).getArmorSet().getChest().get());
		}

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}

	@Override
	public void applyMolangQueries(T animatable, double currentTick) {
		super.applyMolangQueries(animatable, currentTick);

		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 15);
	}
}
