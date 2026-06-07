package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredPillagerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmouredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;

public class ArmouredPillagerRenderer
		extends MobRenderer<ArmouredPillagerEntity, ArmoredPillagerModel<ArmouredPillagerEntity>> {
	private static ResourceLocation TEXTURE = GeneralHelper
			.modLoc("textures/entity/illager/armored_pillager_gold.png");

	public ArmouredPillagerRenderer(Context pContext) {
		super(pContext, new ArmoredPillagerModel<>(pContext.bakeLayer(ModModelLayers.ARMOURED_PILLAGER_BODY)),
				0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ArmouredPillagerEntity pEntity) {
		return TEXTURE;
	}
}
