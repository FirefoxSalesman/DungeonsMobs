package net.firefoxsalesman.dungeonsmobs.client.renderer.golem;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.golem.SquallGolemModel;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SquallGolemRenderer extends GeoEntityRenderer<SquallGolemEntity> {

	public SquallGolemRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SquallGolemModel());
	}

	protected void applyRotations(SquallGolemEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.0f;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}

}
