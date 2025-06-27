package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.SnarelingModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnarelingRenderer extends GeoEntityRenderer<AbstractEnderlingEntity> {
	public SnarelingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SnarelingModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/snareling_eyes.png")));
	}

	@Override
	public RenderType getRenderType(AbstractEnderlingEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
