package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.client.models.ender.BlastlingModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.BlastlingEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlastlingRenderer extends GeoEntityRenderer<BlastlingEntity> {
	public BlastlingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BlastlingModel());
		addRenderLayer(new BlastlingEyeLayer<>(this));
	}

	@Override
	public RenderType getRenderType(BlastlingEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
