package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.jungle.PoisonQuillVineModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.AbstractVineRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoisonAnemoneRenderer extends AbstractVineRenderer<PoisonQuillVineModel> {

    private static final ResourceLocation POISON_ANEMONE_TEXTURE = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/poison_anemone.png");

    public PoisonAnemoneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PoisonQuillVineModel());
        this.addRenderLayer(new GeoEyeLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ocean/quick_growing_kelp_glow.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractVineEntity entity) {
        return POISON_ANEMONE_TEXTURE;
    }
}
