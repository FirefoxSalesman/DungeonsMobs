package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.WhispererRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WaveWhispererEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WhispererEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WavewhispererRenderer extends WhispererRenderer<WaveWhispererEntity> {

    private static final ResourceLocation WAVEWHISPERER_TEXTURE = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/wavewhisperer.png");

    public WavewhispererRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        addRenderLayer(new GeoEyeLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/wavewhisperer_glow.png")));
    }

    public boolean isShaking(WhispererEntity whisperer) {
        return whisperer.isInWrongHabitat();
    }

    @Override
    protected void applyRotations(WaveWhispererEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
                                  float rotationYaw, float partialTicks) {
        if (isShaking(entityLiving)) {
            rotationYaw += (float) (Math.cos((double) entityLiving.tickCount * 3.25D) * Math.PI * (double) 0.4F);
        }
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(WaveWhispererEntity entity) {
        return WAVEWHISPERER_TEXTURE;
    }
}
