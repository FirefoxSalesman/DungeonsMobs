package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.CobwebProjectileEntity;

public class CobwebProjectileModel extends GeoModel<CobwebProjectileEntity> {
	@Override
	public ResourceLocation getAnimationResource(CobwebProjectileEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/web_projectile.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CobwebProjectileEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/web_projectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CobwebProjectileEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/projectile/web_projectile.png");
	}
}
