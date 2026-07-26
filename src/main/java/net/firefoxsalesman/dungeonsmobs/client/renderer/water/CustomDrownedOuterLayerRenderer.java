package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class CustomDrownedOuterLayerRenderer<T extends Drowned> extends DrownedOuterLayer<T> {
	private static final ResourceLocation ORDINARY_TEXTURE = new ResourceLocation(
			"textures/entity/zombie/drowned_outer_layer.png");
	private static final ResourceLocation SEAWEED_TEXTURE = GeneralHelper
			.modLoc("textures/entity/ocean/seaweed_armored_drowned_outer_layer.png");
	private static final ResourceLocation PALE_TEXTURE = GeneralHelper
			.modLoc("textures/entity/ocean/pale_armored_drowned_outer_layer.png");
	private final DrownedModel<T> model;
	private DrownedEliteState state = DrownedEliteState.ORDINARY;

	public CustomDrownedOuterLayerRenderer(RenderLayerParent<T, DrownedModel<T>> renderer,
			EntityModelSet pModelSet) {
		super(renderer, pModelSet);
		this.model = new DrownedModel<>(pModelSet.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
	}

	/**
	 * Sets this layer's elite state, which determines which texture to use when
	 * rendering the layer.
	 */
	public void setEliteState(DrownedEliteState state) {
		this.state = state;
	}

	@Override
	public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity,
			float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks,
			float pNetHeadYaw, float pHeadPitch) {
		ResourceLocation texture;
		switch (this.state) {
			case SEAWEED:
				texture = SEAWEED_TEXTURE;
				break;
			case PALE:
				texture = PALE_TEXTURE;
				break;
			default:
				texture = ORDINARY_TEXTURE;
				break;
		}
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, texture,
				pMatrixStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount,
				pAgeInTicks, pNetHeadYaw, pHeadPitch, pPartialTicks, 1.0F, 1.0F, 1.0F);
	}
}
