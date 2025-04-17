package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MageMissileEntity;

public class MageMissileModel extends GeoModel<MageMissileEntity> {

	@Override
	public ResourceLocation getAnimationResource(MageMissileEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/mage_missile.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MageMissileEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/mage_missile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MageMissileEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/projectile/mage_missile.png");
	}

	@Override
	public void setCustomAnimations(MageMissileEntity entity, long uniqueID, AnimationState<MageMissileEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone everything = getAnimationProcessor().getBone("everything");

		everything.setRotY(-1.5708F);
	}
}
