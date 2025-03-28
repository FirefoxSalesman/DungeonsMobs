package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.projectile.MageMissileModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MageMissileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class MageMissileRenderer extends ProjectileRenderer<MageMissileEntity> {

	public MageMissileRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new MageMissileModel());
	}

	protected int getBlockLightLevel(MageMissileEntity entity, BlockPos position) {
		return 15;
	}

	@Override
	public void render(MageMissileEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
