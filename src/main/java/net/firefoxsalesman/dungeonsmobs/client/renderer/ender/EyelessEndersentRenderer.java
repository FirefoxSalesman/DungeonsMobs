package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.client.models.ender.EyelessEndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GenericEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EyelessEndersentEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class EyelessEndersentRenderer
		extends MobRenderer<EyelessEndersentEntity, EyelessEndersentModel<EyelessEndersentEntity>> {
	public EyelessEndersentRenderer(Context pContext) {
		super(pContext, new EyelessEndersentModel<>(pContext.bakeLayer(ModModelLayers.EYELESS_ENDERSENT_BODY)),
				2);
		addLayer(new GenericEyeLayer<>(this, "textures/entity/ender/eyeless_endersent_eyes.png"));
	}

	@Override
	public ResourceLocation getTextureLocation(EyelessEndersentEntity pEntity) {
		return GeneralHelper.modLoc("textures/entity/ender/eyeless_endersent.png");
	}

	protected void setupRotations(EyelessEndersentEntity pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks,
			float pRotationYaw,
			float pPartialTicks) {
		if (this.isShaking(pEntityLiving)) {
			pRotationYaw += (float) (Math.cos((double) pEntityLiving.tickCount * 3.25D) * Math.PI
					* (double) 0.4F);
		}

		if (!pEntityLiving.hasPose(Pose.SLEEPING)) {
			pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
		}

		if (pEntityLiving.isAutoSpinAttack()) {
			pMatrixStack.mulPose(Axis.XP.rotationDegrees(-90.0F - pEntityLiving.getXRot()));
			pMatrixStack.mulPose(Axis.YP
					.rotationDegrees(((float) pEntityLiving.tickCount + pPartialTicks) * -75.0F));
		}
		if (isEntityUpsideDown(pEntityLiving)) {
			pMatrixStack.translate(0.0F, pEntityLiving.getBbHeight() + 0.1F, 0.0F);
			pMatrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
		}
	}
}
