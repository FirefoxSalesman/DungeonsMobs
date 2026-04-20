package net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem;

import net.firefoxsalesman.dungeonsmobs.gear.client.models.totem.TotemOfShieldingModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfShieldingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TotemOfShieldingRenderer extends ProjectileRenderer<TotemOfShieldingEntity> {
	public TotemOfShieldingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TotemOfShieldingModel());
	}

	@Override
	public RenderType getRenderType(TotemOfShieldingEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
