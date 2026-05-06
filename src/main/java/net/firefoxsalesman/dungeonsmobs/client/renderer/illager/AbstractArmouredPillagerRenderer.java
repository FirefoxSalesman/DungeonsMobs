package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.AbstractArmouredPillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredPillagerModel;

public class AbstractArmouredPillagerRenderer<T extends AbstractArmouredPillagerEntity>
		extends MobRenderer<T, ArmoredPillagerModel<T>> {
	private ResourceLocation textureLocation;

	public AbstractArmouredPillagerRenderer(Context pContext, ResourceLocation textureLocation) {
		super(pContext, new ArmoredPillagerModel<>(pContext.bakeLayer(ModModelLayers.ARMOURED_PILLAGER_BODY)),
				0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
		this.textureLocation = textureLocation;
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return textureLocation;
	}
}
