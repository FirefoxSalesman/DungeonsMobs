package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
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

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
	    int packedOverlay) {
	    float glow = Math.max(minimumPulseAmount, Mth.cos(animatable.tickCount *
							      pulseSpeed) * pulseAmount);
	    RenderType emmissiveRenderType = getRenderType(animatable);
	    getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, emmissiveRenderType, bufferSource.getBuffer(emmissiveRenderType), partialTick, 15728640, OverlayTexture.NO_OVERLAY, glow, glow, glow, 1.0F);
	}

	@Override
	protected RenderType getRenderType(T animatable) {
		return RenderType.eyes(textureLocation);
	}
}
