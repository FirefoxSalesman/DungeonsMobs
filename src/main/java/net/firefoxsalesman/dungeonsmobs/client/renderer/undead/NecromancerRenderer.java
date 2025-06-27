package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.NecromancerModel;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NecromancerRenderer extends GeoEntityRenderer<NecromancerEntity> {

	public NecromancerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new NecromancerModel());
		addRenderLayer(new PulsatingGlowLayer<>(this, new ResourceLocation(DungeonsMobs.MOD_ID,
				"textures/entity/skeleton/necromancer_eyes.png"), 0.2F, 1.0F, 0.5F));
		addRenderLayer(new ItemLayer<>(this));
		addRenderLayer(new ArmourLayer<>(this));
	}

	@Override
	protected void applyRotations(NecromancerEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.3F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public RenderType getRenderType(NecromancerEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(PoseStack poseStack, NecromancerEntity animatable, GeoBone bone,
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
			boolean isReRender,
			float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
			float alpha) {
		if (this.isArmorBone(bone)) {
			bone.setHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick,
				packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected boolean isArmorBone(GeoBone bone) {
		return bone.getName().startsWith("armor");
	}
}
