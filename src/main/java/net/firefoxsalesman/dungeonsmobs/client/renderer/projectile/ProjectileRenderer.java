package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProjectileRenderer<T extends Projectile & GeoAnimatable> extends GeoEntityRenderer<T> {
	public ProjectileRenderer(Context renderManager, GeoModel<T> model) {
		super(renderManager, model);
	}

	@Override
	public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource,
			VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight,
			int packedOverlay, float red,
			float green, float blue, float alpha) {
		poseStack.mulPose(Axis.YP
				.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot())));
		poseStack.mulPose(Axis.ZP
				.rotationDegrees(Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot())));
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay,
				red, green, blue, alpha);
	}
}
