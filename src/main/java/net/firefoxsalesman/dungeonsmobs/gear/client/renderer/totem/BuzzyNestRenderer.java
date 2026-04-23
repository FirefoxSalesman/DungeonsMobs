package net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem;

import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.models.totem.BuzzyNestModel;
import net.firefoxsalesman.dungeonsmobs.gear.entities.BuzzyNestEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BuzzyNestRenderer extends ProjectileRenderer<BuzzyNestEntity> {
	public BuzzyNestRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BuzzyNestModel());
	}

	@Override
	public RenderType getRenderType(BuzzyNestEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
