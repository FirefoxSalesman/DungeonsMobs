package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.SummonSpotModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class SummonSpotRenderer extends ProjectileRenderer<SummonSpotEntity> {
	public SummonSpotRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SummonSpotModel<SummonSpotEntity>());
	}

	@Override
	public void preRender(PoseStack poseStack, SummonSpotEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay,
				red, green, blue, alpha);
		if (animatable.lifeTime <= 1) {
			float scaleFactor = 0.0F;
			poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
		}
	}

	@Override
	protected int getBlockLightLevel(SummonSpotEntity entity, BlockPos position) {
		return 15;
	}

	@Override
	public RenderType getRenderType(SummonSpotEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
