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

    public void render(SnarelingGlobEntity glob, float p_225623_2_, float p_225623_3_, PoseStack stack, MultiBufferSource BufferSource, int p_225623_6_) {
        stack.pushPose();
        stack.translate(0.0D, 0.15F, 0.0D);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_225623_3_, glob.yRotO, glob.getYRot())));
        stack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_225623_3_, glob.xRotO, glob.getXRot())));
        this.model.setupAnim(glob, p_225623_3_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = BufferSource.getBuffer(this.model.renderType(LLAMA_SPIT_LOCATION));
        model.renderToBuffer(stack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
        super.render(glob, p_225623_2_, p_225623_3_, stack, BufferSource, p_225623_6_);
    }

    public ResourceLocation getTextureLocation(SnarelingGlobEntity p_110775_1_) {
        return LLAMA_SPIT_LOCATION;
    }
}
