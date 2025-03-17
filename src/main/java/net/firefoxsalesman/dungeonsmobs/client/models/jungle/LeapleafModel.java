package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.LeapleafEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class LeapleafModel extends GeoModel<LeapleafEntity> {

	@Override
	public ResourceLocation getAnimationResource(LeapleafEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/leapleaf.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LeapleafEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/leapleaf.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LeapleafEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/jungle/leapleaf.png");
	}

    // @Override
	// public void applyMolangQueries(LeapleafEntity animatable, double animTime) {
	// 	super.applyMolangQueries(animatable, animTime);
	// 	LivingEntity livingEntity = (LivingEntity) animatable;
	// 	Vec3 velocity = livingEntity.getDeltaMovement();
	// 	float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
	// 	MathParser.setValue("query.ground_speed", () -> groundSpeed * 17.5);
	// }
}
