package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.projectile.PoisonQuillModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PoisonQuillRenderer extends ProjectileRenderer<PoisonQuillEntity> {

	public PoisonQuillRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PoisonQuillModel());
	}

	@Override
	public void render(PoisonQuillEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

}
