package net.firefoxsalesman.dungeonsmobs.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProjectileRenderer<T extends Projectile & GeoAnimatable> extends GeoEntityRenderer<T> {
	public ProjectileRenderer(Context renderManager, GeoModel<T> model) {
		super(renderManager, model);
	}

	@Override
	protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw,
			float partialTick) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP
				.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP
				.rotationDegrees(Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot())));
		poseStack.popPose();
	}
}
