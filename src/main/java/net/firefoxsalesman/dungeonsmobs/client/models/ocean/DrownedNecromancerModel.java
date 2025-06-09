package net.firefoxsalesman.dungeonsmobs.client.models.ocean;// Made with Blockbench 3.8.4

// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;

@OnlyIn(Dist.CLIENT)
public class DrownedNecromancerModel extends GeoModel<DrownedNecromancerEntity> {
	@Override
	public ResourceLocation getAnimationResource(DrownedNecromancerEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/drowned_necromancer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DrownedNecromancerEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/drowned_necromancer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DrownedNecromancerEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/ocean/drowned_necromancer.png");
	}

	@Override
	public void setCustomAnimations(DrownedNecromancerEntity entity, long uniqueID,
			AnimationState<DrownedNecromancerEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");
		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");

		CoreGeoBone particles = this.getAnimationProcessor().getBone("staffParticles");

		if (particles instanceof GeoBone && entity.isSpellcasting()) {
			GeoBone particleBone = ((GeoBone) particles);
			entity.level().addParticle(
					entity.isInWaterOrBubble() ? ParticleTypes.BUBBLE_COLUMN_UP
							: ModParticleTypes.NECROMANCY.get(),
					particleBone.getWorldPosition().x, particleBone.getWorldPosition().y,
					particleBone.getWorldPosition().z, 0, 0, 0);
		}

		cape.setHidden(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() != entity.getArmorSet().getChest()
				.get());

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);

		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}

	@Override
	public void applyMolangQueries(DrownedNecromancerEntity animatable, double animTime) {
			Vec3 velocity = animatable.getDeltaMovement();
			float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
			MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 20);
	}
}
