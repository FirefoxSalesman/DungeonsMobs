package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.TridentStormModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class TridentStormRenderer extends ProjectileRenderer<TridentStormEntity> {
	public TridentStormRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TridentStormModel());
	}

	@Override
	public void preRender(PoseStack poseStack, TridentStormEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay,
				red, green, blue, alpha);
		poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getYRot() * ((float) Math.PI / 180F)));

		if (animatable.lifeTime <= 1) {
			float scaleFactor = 0.0F;
			poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
		}
	}

	@Override
	protected int getBlockLightLevel(TridentStormEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	public RenderType getRenderType(TridentStormEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
