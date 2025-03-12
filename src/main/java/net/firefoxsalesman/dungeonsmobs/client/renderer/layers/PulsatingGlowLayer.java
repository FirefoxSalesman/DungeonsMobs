package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class PulsatingGlowLayer<T extends LivingEntity & GeoAnimatable> extends AutoGlowingGeoLayer<T> {

	public ResourceLocation textureLocation;

	public float pulseSpeed;
	public float pulseAmount;
	public float minimumPulseAmount;

	public PulsatingGlowLayer(GeoRenderer<T> endermanReplacementRenderer, ResourceLocation textureLocation,
			float pulseSpeed, float pulseAmount, float minimumPulseAmount) {
		super(endermanReplacementRenderer);
		this.textureLocation = textureLocation;
		this.pulseSpeed = pulseSpeed;
		this.pulseAmount = pulseAmount;
		this.minimumPulseAmount = minimumPulseAmount;
	}

	// @Override
	// public void render(PoseStack poseStack, T animatable, BakedGeoModel
	// bakedModel, RenderType renderType,
	// MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int
	// packedLight,
	// int packedOverlay) {
	// float glow = Math.max(minimumPulseAmount, Mth.cos(animatable.tickCount *
	// pulseSpeed) * pulseAmount);
	// super.render(poseStack, animatable, bakedModel, renderType, bufferSource,
	// buffer, partialTick,
	// packedLight,
	// packedOverlay);
	// }

	// @Override
	// public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int
	// packedLightIn,
	// T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float
	// partialTicks,
	// float ageInTicks, float netHeadYaw, float headPitch) {

	// GeoModelProvider<T> geomodel = this.getEntityModel();

	// // original speed: 0.045F
	// // original amount: 0.25F

	// float glow = Math.max(minimumPulseAmount, Mth.cos(ageInTicks * pulseSpeed) *
	// pulseAmount);
	// renderModel(geomodel, textureLocation, matrixStackIn, bufferIn,
	// packedLightIn, entitylivingbaseIn, 1.0F,
	// glow, glow, glow);
	// }

	@Override
	protected RenderType getRenderType(T animatable) {
		return RenderType.eyes(textureLocation);
	}
}
