package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.AbstractMonstrosityModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.AbstractMonstrosityEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AbstractMonstrosityRenderer<T extends AbstractMonstrosityEntity> extends GeoEntityRenderer<T> {
	protected static final ResourceLocation REDSTONE_TEXTURE = modLoc(
			"textures/entity/redstone/redstone_monstrosity_layer.png");

	public AbstractMonstrosityRenderer(Context renderManager, String bodyTexture, String eyeTexture) {
		super(renderManager, new AbstractMonstrosityModel<>(bodyTexture));
		addRenderLayer(new GeoEyeLayer<>(this,
				modLoc(eyeTexture)) {
			@Override
			public void render(PoseStack poseStack, T animatable,
					BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource,
					VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
				RenderType emmissiveRenderType = getRenderType(animatable);
				getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable,
						emmissiveRenderType,
						bufferSource.getBuffer(emmissiveRenderType), partialTick, 15728640,
						OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		});
		addRenderLayer(new PulsatingGlowLayer<>(this, REDSTONE_TEXTURE, 0.1F, 0.5F, 0.0F));
		addRenderLayer(new GeoEyeLayer<>(this, REDSTONE_TEXTURE));
	}
}
