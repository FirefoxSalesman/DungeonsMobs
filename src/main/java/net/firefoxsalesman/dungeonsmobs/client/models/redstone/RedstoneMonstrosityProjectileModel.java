package net.firefoxsalesman.dungeonsmobs.client.models.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.RedstoneMonstrosityProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RedstoneMonstrosityProjectileModel extends GeoModel<RedstoneMonstrosityProjectileEntity> {
	@Override
	public ResourceLocation getModelResource(RedstoneMonstrosityProjectileEntity animatable) {
		return modLoc("geo/monstrosity_projectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RedstoneMonstrosityProjectileEntity animatable) {
		return modLoc("textures/entity/redstone/redstone_monstrosity_projectile.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RedstoneMonstrosityProjectileEntity animatable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAnimationResource'");
	}

}
