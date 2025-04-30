package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.client.models.summonables.WindcallerTornadoModel;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class WindcallerTornadoRenderer extends ProjectileRenderer<WindcallerTornadoEntity> {
	public WindcallerTornadoRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WindcallerTornadoModel());
	}

	@Override
	public void preRender(PoseStack poseStack, WindcallerTornadoEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (!animatable.isBlast()) {
			float scaleFactor = 1.25F;
			poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
		} else {
			poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getYRot() * ((float) Math.PI / 180F)));
		}

		if (animatable.lifeTime <= 1) {
			float scaleFactor = 0.0F;
			poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
		}
	}

}
