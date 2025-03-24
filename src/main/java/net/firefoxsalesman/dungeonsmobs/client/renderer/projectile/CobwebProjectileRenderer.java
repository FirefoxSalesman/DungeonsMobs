package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.CobwebProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.CobwebProjectileModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CobwebProjectileRenderer extends ProjectileRenderer<CobwebProjectileEntity> {

	public CobwebProjectileRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CobwebProjectileModel());
	}

	@Override
	public void render(CobwebProjectileEntity entityIn, float entityYaw, float partialTicks,
			PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

}
