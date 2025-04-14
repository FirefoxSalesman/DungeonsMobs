package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.SnarelingGlobModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers.SNARELING_GLOB;

@OnlyIn(Dist.CLIENT)
public class SnarelingGlobRenderer extends EntityRenderer<SnarelingGlobEntity> {
    private static final ResourceLocation LLAMA_SPIT_LOCATION = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/snareling_glob.png");
    private final SnarelingGlobModel<SnarelingGlobEntity> model;

    public SnarelingGlobRenderer(EntityRendererProvider.Context renderContext) {
        super(renderContext);
        this.model = new SnarelingGlobModel<>(renderContext.bakeLayer(SNARELING_GLOB));
    }

    protected int getBlockLightLevel(SnarelingGlobEntity p_225624_1_, BlockPos p_225624_2_) {
        return 10;
    }

    public void render(SnarelingGlobEntity p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.translate(0.0D, 0.15F, 0.0D);
        p_225623_4_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_225623_3_, p_225623_1_.yRotO, p_225623_1_.getYRot())));
        p_225623_4_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_225623_3_, p_225623_1_.xRotO, p_225623_1_.getXRot())));
        this.model.setupAnim(p_225623_1_, p_225623_3_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(LLAMA_SPIT_LOCATION));
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    public ResourceLocation getTextureLocation(SnarelingGlobEntity p_110775_1_) {
        return LLAMA_SPIT_LOCATION;
    }
}
