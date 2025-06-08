package net.firefoxsalesman.dungeonsmobs.client.renderer.blaze;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.blaze.WildfireModel;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WildfireRenderer extends GeoEntityRenderer<WildfireEntity> {
	public WildfireRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WildfireModel());
		addRenderLayer(new PulsatingGlowLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/blaze/wildfire.png"), 0.1F,
				1.0F, 0.25F));
		addRenderLayer(new ArmourLayer<>(this));
	}

	@Override
	protected int getBlockLightLevel(WildfireEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	protected void applyRotations(WildfireEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.25F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public RenderType getRenderType(WildfireEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource,
			float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(PoseStack poseStack, WildfireEntity animatable, GeoBone bone,
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
			boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		if (this.isArmorBone(bone)) {
			bone.setHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected boolean isArmorBone(GeoBone bone) {
		return bone.getName().startsWith("armor");
	}
}
