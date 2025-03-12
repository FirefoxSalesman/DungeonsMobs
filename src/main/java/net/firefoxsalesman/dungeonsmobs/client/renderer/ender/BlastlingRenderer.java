package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.BlastlingModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlastlingRenderer extends GeoEntityRenderer<AbstractEnderlingEntity> {
    public BlastlingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlastlingModel());
        addRenderLayer(new GeoEyeLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/blastling1_eyes.png")));
    }
}
