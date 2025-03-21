package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.SimpleTrapModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class SimpleTrapRenderer extends ProjectileRenderer<SimpleTrapEntity> {
	public SimpleTrapRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SimpleTrapModel<SimpleTrapEntity>());
	}

	@Override
	public void preRender(PoseStack poseStack, SimpleTrapEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scaleFactor = 2.0F;
		if (animatable.lifeTime <= 1) {
			scaleFactor = 0.0F;
		} else {
			scaleFactor = 2.0F;
		}
		poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
	}

}
