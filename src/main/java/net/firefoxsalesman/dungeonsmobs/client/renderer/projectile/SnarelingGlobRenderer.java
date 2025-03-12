package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.SnarelingGlobModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnarelingGlobRenderer extends EntityRenderer<SnarelingGlobEntity> {
	private static final ResourceLocation LLAMA_SPIT_LOCATION = new ResourceLocation(DungeonsMobs.MOD_ID,
			"textures/entity/snareling_glob.png");
	private final SnarelingGlobModel<SnarelingGlobEntity> model;

	public SnarelingGlobRenderer(EntityRendererProvider.Context renderContext) {
		super(renderContext);
		model = new SnarelingGlobModel<>(renderContext.bakeLayer(ModModelLayers.SNARELING_GLOB));
	}

	protected int getBlockLightLevel(SnarelingGlobEntity snarelingGlob, BlockPos blockPos) {
		return 10;
	}

	public void render(SnarelingGlobEntity snarelingGlob, float p_225623_2_, float p_225623_3_, PoseStack poseStack,
			MultiBufferSource bufferSource, int p_225623_6_) {
		poseStack.pushPose();
		poseStack.translate(0.0D, 0.15F, 0.0D);
		Vector3f v1 = new Vector3f();
		Vector3f v2 = new Vector3f();
		poseStack.mulPose(v1.rotationTo(p_225623_3_, snarelingGlob.yRotO, snarelingGlob.getYRot(),
				new Quaternionf()));
		poseStack.mulPose(v2.rotationTo(p_225623_2_, snarelingGlob.xRotO, snarelingGlob.getXRot(),
				new Quaternionf()));
		model.setupAnim(snarelingGlob, p_225623_3_, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer ivertexbuilder = bufferSource.getBuffer(model.renderType(LLAMA_SPIT_LOCATION));
		model.renderToBuffer(poseStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
				1.0F, 1.0F);
		poseStack.popPose();
		super.render(snarelingGlob, p_225623_2_, p_225623_3_, poseStack, bufferSource, p_225623_6_);
	}

	public ResourceLocation getTextureLocation(SnarelingGlobEntity snarelingGlob) {
		return LLAMA_SPIT_LOCATION;
	}
}
