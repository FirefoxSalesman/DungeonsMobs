package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class BlastlingEyeLayer<T extends BlastlingEntity> extends GeoEyeLayer<T> {
	public BlastlingEyeLayer(GeoRenderer<T> endermanReplacementRenderer) {
		super(endermanReplacementRenderer,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/blastling1_eyes.png"));
	}

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay) {
		RenderType emmissiveRenderType = getRenderType(animatable);
		textureLocation = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/blastling"
				+ (1 + ((int) ((BlastlingEntity) animatable).flameTicks) % 3)
				+ "_eyes.png");
		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, emmissiveRenderType,
				bufferSource.getBuffer(emmissiveRenderType), partialTick, 15728640,
				OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
