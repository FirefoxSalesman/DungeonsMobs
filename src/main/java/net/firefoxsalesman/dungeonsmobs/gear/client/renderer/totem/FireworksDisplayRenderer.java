package net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem;

import net.firefoxsalesman.dungeonsmobs.gear.client.models.totem.FireworksDisplayModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.entities.FireworksDisplayEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireworksDisplayRenderer extends ProjectileRenderer<FireworksDisplayEntity> {
	public FireworksDisplayRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FireworksDisplayModel());
	}

	@Override
	public RenderType getRenderType(FireworksDisplayEntity animatable, ResourceLocation texture,
			MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));

	}
}
