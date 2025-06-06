package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.IceCloudModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;

public class IceCloudRenderer extends ProjectileRenderer<IceCloudEntity> {
    public IceCloudRenderer(EntityRendererProvider.Context renderManager) {
	super(renderManager, new IceCloudModel());
    }

    @Override
    protected int getBlockLightLevel(IceCloudEntity iceCloud, BlockPos pos) {
	return iceCloud.level().getBrightness(LightLayer.BLOCK, pos) > 10
	    ? iceCloud.level().getBrightness(LightLayer.BLOCK, pos)
	    : 5;
    }

    @Override
    public RenderType getRenderType(IceCloudEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource,
        float partialTick) {
	return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
