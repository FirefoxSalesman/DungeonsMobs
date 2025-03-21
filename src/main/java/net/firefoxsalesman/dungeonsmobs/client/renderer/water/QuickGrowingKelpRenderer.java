package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.jungle.QuickGrowingVineModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.AbstractVineRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuickGrowingKelpRenderer extends AbstractVineRenderer<QuickGrowingVineModel> {

    private static final ResourceLocation QUICK_GROWING_KELP_TEXTURE = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/quick_growing_kelp.png");

    public QuickGrowingKelpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuickGrowingVineModel());
        addRenderLayer(new GeoEyeLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/quick_growing_kelp_glow.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractVineEntity entity) {
        return QUICK_GROWING_KELP_TEXTURE;
    }
}
