package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.WraithModel;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WraithRenderer extends GeoEntityRenderer<WraithEntity> {

	public WraithRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WraithModel());
		addRenderLayer(new PulsatingGlowLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/wraith/wraith_glow.png"),
				0.1F, 1.0F, 0.25F));
	}

	@Override
	public void renderRecursively(PoseStack poseStack, WraithEntity animatable, GeoBone bone, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (isArmorBone(bone)) {
			bone.setHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick,
				packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected boolean isArmorBone(CoreGeoBone bone) {
		return bone.getName().startsWith("armor");
	}
}
