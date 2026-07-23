package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.client.models.ender.WatchlingModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WatchlingRenderer extends GeoEntityRenderer<AbstractEnderlingEntity> {
	public WatchlingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WatchlingModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				GeneralHelper.modLoc("textures/entity/ender/watchling_eyes.png")));
	}

	@Override
	public RenderType getRenderType(AbstractEnderlingEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
