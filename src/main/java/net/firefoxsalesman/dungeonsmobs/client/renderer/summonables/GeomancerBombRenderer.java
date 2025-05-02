package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.GeomancerConstructModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerBombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class GeomancerBombRenderer extends GeoEntityRenderer<GeomancerBombEntity> {
	public GeomancerBombRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GeomancerConstructModel<GeomancerBombEntity>(
				"textures/entity/constructs/geomancer_bomb.png"));
		this.addRenderLayer(new PulsatingGlowLayer<GeomancerBombEntity>(this, new ResourceLocation(MOD_ID,
				"textures/entity/constructs/geomancer_bomb.png"), 0.5F, 0.6F, 0.2F) {
			@Override
			public void render(PoseStack poseStack, GeomancerBombEntity animatable,
					BakedGeoModel bakedModel,
					RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
					float partialTick, int packedLight, int packedOverlay) {

				if (animatable.getLifeTicks() < 60 && animatable.getLifeTicks() >= 30) {
					textureLocation = new ResourceLocation(MOD_ID,
							"textures/entity/constructs/geomancer_bomb_eyes_1.png");
				} else if (animatable.getLifeTicks() < 30
						&& animatable.getLifeTicks() >= 0) {
					pulseSpeed = 0.8F;
					textureLocation = new ResourceLocation(MOD_ID,
							"textures/entity/constructs/geomancer_bomb_eyes_2.png");
				}
				super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer,
						partialTick, packedLight, packedOverlay);
			}
		});
	}
}
