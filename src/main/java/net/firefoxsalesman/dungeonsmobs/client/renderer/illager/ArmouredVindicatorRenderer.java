package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmouredVindicatorModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmouredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ArmouredVindicatorRenderer
		extends MobRenderer<ArmouredVindicatorEntity, ArmouredVindicatorModel<ArmouredVindicatorEntity>> {
	private static ResourceLocation textureLocation = GeneralHelper
			.modLoc("textures/entity/illager/armored_vindicator_gold.png");

	public ArmouredVindicatorRenderer(Context pContext) {
		super(pContext, new ArmouredVindicatorModel<>(
				pContext.bakeLayer(ModModelLayers.ARMOURED_VINDICATOR_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ArmouredVindicatorEntity pEntity) {
		return textureLocation;
	}
}
