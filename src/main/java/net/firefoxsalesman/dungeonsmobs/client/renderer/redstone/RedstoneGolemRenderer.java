package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneGolemModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedstoneGolemRenderer extends GeoEntityRenderer<RedstoneGolemEntity> {
	public RedstoneGolemRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new RedstoneGolemModel());
		addRenderLayer(new GeoEyeLayer<RedstoneGolemEntity>(this,
				modLoc("textures/entity/redstone/redstone_golem_light.png")) {
			@Override
			public void render(PoseStack poseStack, RedstoneGolemEntity animatable,
					BakedGeoModel bakedModel,
					RenderType renderType,
					MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
					int packedLight,
					int packedOverlay) {
				if (!animatable.isSummoningMines()) {
					super.render(poseStack, animatable, bakedModel, renderType, bufferSource,
							buffer, partialTick, packedLight, packedOverlay);
				}
			}

		});
		addRenderLayer(new PulsatingGlowLayer<>(this,
				modLoc("textures/entity/redstone/redstone_golem_yellow_light.png"),
				0.1F, 0.5F, 0.0F) {
			@Override
			public void render(PoseStack poseStack, RedstoneGolemEntity animatable,
					BakedGeoModel bakedModel,
					RenderType renderType,
					MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
					int packedLight,
					int packedOverlay) {
				if (!animatable.isSummoningMines()) {
					super.render(poseStack, animatable, bakedModel, renderType, bufferSource,
							buffer, partialTick, packedLight, packedOverlay);
				}
			}
		});
		addRenderLayer(new GeoEyeLayer<>(this,
				modLoc("textures/entity/redstone/redstone_golem_yellow_light.png")) {
			@Override
			public void render(PoseStack poseStack, RedstoneGolemEntity animatable,
					BakedGeoModel bakedModel,
					RenderType renderType,
					MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
					int packedLight,
					int packedOverlay) {
				if (animatable.isSummoningMines()) {
					super.render(poseStack, animatable, bakedModel, renderType, bufferSource,
							buffer, partialTick, packedLight, packedOverlay);
				}
			}
		});
		addRenderLayer(new PulsatingGlowLayer<>(this,
				modLoc("textures/entity/redstone/redstone_golem_white_light.png"),
				0.2F, 0.75F, 0.0F) {
			@Override
			public void render(PoseStack poseStack, RedstoneGolemEntity animatable,
					BakedGeoModel bakedModel,
					RenderType renderType,
					MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
					int packedLight,
					int packedOverlay) {
				if (!animatable.isSummoningMines()) {
					super.render(poseStack, animatable, bakedModel, renderType, bufferSource,
							buffer, partialTick, packedLight, packedOverlay);
				}
			}
		});
	}

	protected void applyRotations(RedstoneGolemEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.0f;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}

}
