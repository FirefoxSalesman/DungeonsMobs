package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.CobwebProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CobwebProjectileModel extends GeoModel<CobwebProjectileEntity> {
	@Override
	public ResourceLocation getAnimationResource(CobwebProjectileEntity entity) {
		return modLoc("animations/web_projectile.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CobwebProjectileEntity entity) {
		return modLoc("geo/web_projectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CobwebProjectileEntity entity) {
		return modLoc("textures/entity/projectile/web_projectile.png");
	}
}
