package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class GeoEyeLayer<T extends LivingEntity & GeoEntity> extends AutoGlowingGeoLayer<T> {

	public ResourceLocation textureLocation;
	// private Random random;
	// int tick;

	public GeoEyeLayer(GeoRenderer<T> endermanReplacementRenderer, ResourceLocation textureLocation) {
		super(endermanReplacementRenderer);
		this.textureLocation = textureLocation;
		// random = new Random();
	}

	@Override
	protected ResourceLocation getTextureResource(T animatable) {
		return textureLocation;
	}

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay) {
		if (animatable instanceof BlastlingEntity) {
			textureLocation = new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/blastling"
					+ (1 + ((int) ((BlastlingEntity) animatable).flameTicks) % 3)
					+ "_eyes.png");
		}
		super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick,
				packedLight,
				packedOverlay);
	}

	@Override
	protected RenderType getRenderType(T animatable) {
		return RenderType.eyes(textureLocation);
	}
}
