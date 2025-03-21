package net.firefoxsalesman.dungeonsmobs.client.renderer.jungle;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.jungle.QuickGrowingVineModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuickGrowingVineRenderer extends AbstractVineRenderer<QuickGrowingVineModel> {
    public QuickGrowingVineRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuickGrowingVineModel());
        addRenderLayer(new GeoEyeLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/jungle/quick_growing_vine_glow.png")));
    }
}
