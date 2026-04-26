package net.firefoxsalesman.dungeonsmobs.gear.client.renderer.ghosts;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.gear.client.models.ghosts.SoulWizardModel;
import net.firefoxsalesman.dungeonsmobs.gear.entities.SoulWizardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoulWizardRenderer extends GeoEntityRenderer<SoulWizardEntity> {

	public SoulWizardRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SoulWizardModel());
		this.addRenderLayer(new PulsatingGlowLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID,
						"textures/entity/ghosts/soul_wizard_glow.png"),
				0.2F, 1.0F, 0.25F));
	}

	@Override
	protected void applyRotations(SoulWizardEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 0.0F;
		if (entityLiving.appearAnimationTick < entityLiving.appearAnimationLength - 2) {
			scaleFactor = 1.0F;
		} else {
			scaleFactor = 0.0F;
		}
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public RenderType getRenderType(SoulWizardEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
