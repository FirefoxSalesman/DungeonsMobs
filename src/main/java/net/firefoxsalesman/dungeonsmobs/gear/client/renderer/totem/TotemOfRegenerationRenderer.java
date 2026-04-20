package net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem;

import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.models.totem.TotemOfRegenerationModel;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfRegenerationEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TotemOfRegenerationRenderer extends ProjectileRenderer<TotemOfRegenerationEntity> {
	public TotemOfRegenerationRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TotemOfRegenerationModel());
	}

	@Override
	public RenderType getRenderType(TotemOfRegenerationEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
