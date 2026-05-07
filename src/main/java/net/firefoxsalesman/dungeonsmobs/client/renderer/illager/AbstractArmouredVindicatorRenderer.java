package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmouredVindicatorModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.AbstractArmouredVindicatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class AbstractArmouredVindicatorRenderer<T extends AbstractArmouredVindicatorEntity>
		extends MobRenderer<T, ArmouredVindicatorModel<T>> {
	private ResourceLocation textureLocation;

	public AbstractArmouredVindicatorRenderer(Context pContext, ResourceLocation textureLocation) {
		super(pContext, new ArmouredVindicatorModel<>(
				pContext.bakeLayer(ModModelLayers.ARMOURED_VINDICATOR_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
		this.textureLocation = textureLocation;
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return textureLocation;
	}
}
