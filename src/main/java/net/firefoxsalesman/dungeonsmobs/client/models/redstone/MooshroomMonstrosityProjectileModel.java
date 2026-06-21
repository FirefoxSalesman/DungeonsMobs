package net.firefoxsalesman.dungeonsmobs.client.models.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MooshroomMonstrosityProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MooshroomMonstrosityProjectileModel extends GeoModel<MooshroomMonstrosityProjectileEntity> {
	@Override
	public ResourceLocation getModelResource(MooshroomMonstrosityProjectileEntity animatable) {
		return modLoc("geo/mooshroom_monstrosity_projectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MooshroomMonstrosityProjectileEntity animatable) {
		return modLoc("textures/entity/projectile/mooshroom_monstrosity_projectile.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MooshroomMonstrosityProjectileEntity animatable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAnimationResource'");
	}

}
