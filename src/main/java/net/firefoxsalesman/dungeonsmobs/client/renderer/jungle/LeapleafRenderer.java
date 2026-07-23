package net.firefoxsalesman.dungeonsmobs.client.renderer.jungle;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.jungle.LeapleafModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.LeapleafEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LeapleafRenderer extends GeoEntityRenderer<LeapleafEntity> {
	public LeapleafRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new LeapleafModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				GeneralHelper.modLoc("textures/entity/jungle/leapleaf_glow.png")));
	}

	protected void applyRotations(LeapleafEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 0.9375F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}
}
