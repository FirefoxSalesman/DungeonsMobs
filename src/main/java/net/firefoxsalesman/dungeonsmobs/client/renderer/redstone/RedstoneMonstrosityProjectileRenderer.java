package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneMonstrosityProjectileModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.RedstoneMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonslibs.client.renderer.ProjectileRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RedstoneMonstrosityProjectileRenderer extends ProjectileRenderer<RedstoneMonstrosityProjectileEntity> {

	public RedstoneMonstrosityProjectileRenderer(Context renderManager) {
		super(renderManager, new RedstoneMonstrosityProjectileModel());
	}

	@Override
	protected int getBlockLightLevel(RedstoneMonstrosityProjectileEntity pEntity, BlockPos pos) {
		return 15;
	}

	@Override
	public RenderType getRenderType(RedstoneMonstrosityProjectileEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.eyes(getTextureLocation(animatable));
	}
}
