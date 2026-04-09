package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class VanillaPulsatingGlowLayer<T extends Entity, M extends ConvenientModel<T>> extends GenericEyeLayer<T, M> {
	private float pulseSpeed;
	private float pulseAmount;
	private float minimumPulseAmount;

	public VanillaPulsatingGlowLayer(RenderLayerParent<T, M> parent, String path, float pulseSpeed,
			float pulseAmount, float minimumPulseAmount) {
		super(parent, path);
		this.pulseSpeed = pulseSpeed;
		this.pulseAmount = pulseAmount;
		this.minimumPulseAmount = minimumPulseAmount;
	}

	@Override
	public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity,
			float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks,
			float pNetHeadYaw, float pHeadPitch) {
		float glow = Math.max(minimumPulseAmount, Mth.cos(pLivingEntity.tickCount * pulseSpeed) * pulseAmount);
		VertexConsumer vertexconsumer = pBuffer.getBuffer(this.renderType());
		this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY,
				glow, glow, glow, 1.0F);
	}
}
