package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.DrownedNecromancerOrbModel;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.DrownedNecromancerOrbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class DrownedNecromancerOrbRenderer extends ProjectileRenderer<DrownedNecromancerOrbEntity> {

	public DrownedNecromancerOrbRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DrownedNecromancerOrbModel());
	}

	@Override
	public void preRender(PoseStack poseStack, DrownedNecromancerOrbEntity animatable, BakedGeoModel model,
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
	protected int getBlockLightLevel(DrownedNecromancerOrbEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	public void render(DrownedNecromancerOrbEntity entityIn, float entityYaw, float partialTicks,
			PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public RenderType getRenderType(DrownedNecromancerOrbEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
