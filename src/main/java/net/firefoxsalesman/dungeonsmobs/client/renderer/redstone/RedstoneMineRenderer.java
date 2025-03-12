package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneMineModel;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedstoneMineRenderer extends GeoEntityRenderer<RedstoneMineEntity> {
	public RedstoneMineRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new RedstoneMineModel());
	}

	@Override
	protected int getBlockLightLevel(RedstoneMineEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	public void render(RedstoneMineEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		if (entityIn.getLifeTicks() > RedstoneMineEntity.LIFE_TIME) {
			return;
		}
		matrixStackIn.translate(0, 0.01F, 0);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
