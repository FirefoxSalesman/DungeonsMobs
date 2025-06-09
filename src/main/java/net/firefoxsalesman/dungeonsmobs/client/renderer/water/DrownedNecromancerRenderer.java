package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.ocean.DrownedNecromancerModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class DrownedNecromancerRenderer extends GeoEntityRenderer<DrownedNecromancerEntity> {

	public DrownedNecromancerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DrownedNecromancerModel());
		addRenderLayer(new PulsatingGlowLayer<>(this,
				new ResourceLocation(MOD_ID, "textures/entity/ocean/drowned_necromancer_eyes.png"),
				0.2F, 0.5F, 1.0F));
		addRenderLayer(new ItemLayer<>(this));
		addRenderLayer(new ArmourLayer<>(this));
	}

	@Override
	protected void applyRotations(DrownedNecromancerEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.5F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public RenderType getRenderType(DrownedNecromancerEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(PoseStack poseStack, DrownedNecromancerEntity animatable, GeoBone bone,
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
