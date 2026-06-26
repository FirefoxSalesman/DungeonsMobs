package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredVindicatorModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmoredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ArmoredVindicatorRenderer
		extends MobRenderer<ArmoredVindicatorEntity, ArmoredVindicatorModel<ArmoredVindicatorEntity>> {
	private static ResourceLocation textureLocation = GeneralHelper
			.modLoc("textures/entity/illager/armored_vindicator_gold.png");

	public ArmoredVindicatorRenderer(Context pContext) {
		super(pContext, new ArmoredVindicatorModel<>(
				pContext.bakeLayer(ModModelLayers.ARMORED_VINDICATOR_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ArmoredVindicatorEntity pEntity) {
		return textureLocation;
	}
}
