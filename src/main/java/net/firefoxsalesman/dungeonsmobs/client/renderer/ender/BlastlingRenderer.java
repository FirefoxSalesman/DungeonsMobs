package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.client.models.ender.BlastlingModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.BlastlingEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlastlingRenderer extends GeoEntityRenderer<BlastlingEntity> {
    public BlastlingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlastlingModel());
        addRenderLayer(new BlastlingEyeLayer<>(this));
    }
}
