package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.MooshroomMonstrosityProjectileModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MooshroomMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.ProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class MooshroomMonstrosityProjectileRenderer extends ProjectileRenderer<MooshroomMonstrosityProjectileEntity> {
	public MooshroomMonstrosityProjectileRenderer(Context renderManager) {
		super(renderManager, new MooshroomMonstrosityProjectileModel());
	}
}
