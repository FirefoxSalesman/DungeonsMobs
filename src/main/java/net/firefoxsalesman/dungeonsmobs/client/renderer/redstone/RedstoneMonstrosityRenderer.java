package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.AbstractMonstrosityModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMonstrosityEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedstoneMonstrosityRenderer extends GeoEntityRenderer<RedstoneMonstrosityEntity> {

	public RedstoneMonstrosityRenderer(Context renderManager) {
		super(renderManager, new AbstractMonstrosityModel<>());
		addRenderLayer(new GeoEyeLayer<>(this,
				modLoc("textures/entity/redstone/redstone_monstrosity_eyes.png")) {
			@Override
			public void render(PoseStack poseStack, RedstoneMonstrosityEntity animatable,
					BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource,
					VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
				RenderType emmissiveRenderType = getRenderType(animatable);
				getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable,
						emmissiveRenderType,
						bufferSource.getBuffer(emmissiveRenderType), partialTick, 15728640,
						OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		});
		addRenderLayer(new PulsatingGlowLayer<>(this,
				modLoc("textures/entity/redstone/redstone_monstrosity_layer.png"),
				0.1F, 0.5F, 0.0F));
		addRenderLayer(new GeoEyeLayer<>(this,
				modLoc("textures/entity/redstone/redstone_monstrosity_layer.png")));
	}
}
