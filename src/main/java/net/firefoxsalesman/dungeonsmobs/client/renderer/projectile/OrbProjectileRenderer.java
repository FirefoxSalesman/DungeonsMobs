package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.projectile.OrbProjectileModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrbProjectileRenderer extends GeoEntityRenderer<NecromancerOrbEntity> {

	private Color color;

	public OrbProjectileRenderer(EntityRendererProvider.Context renderManager) {
		this(renderManager, Color.WHITE.getColor(), true);
	}

	public OrbProjectileRenderer(EntityRendererProvider.Context renderManager, int color, boolean renderTrail) {
		super(renderManager, new OrbProjectileModel(renderTrail));
		// this.color = Color.ofTransparent(color);
		this.color = Color.ofOpaque(color);
	}

	@Override
	public void preRender(PoseStack poseStack, NecromancerOrbEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scaleFactor = 1.0F;
		if (animatable.lifeTime <= 3) {
			scaleFactor = 0.0F;
		} else {
			scaleFactor = 1.0F;
		}
		poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
	}

	@Override
	protected int getBlockLightLevel(NecromancerOrbEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	public void render(NecromancerOrbEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public Color getRenderColor(NecromancerOrbEntity animatable, float partialTick, int packedLight) {
		return color;
	}

	@Override
	public RenderType getRenderType(NecromancerOrbEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.eyes(getTextureLocation(animatable));
	}
}
