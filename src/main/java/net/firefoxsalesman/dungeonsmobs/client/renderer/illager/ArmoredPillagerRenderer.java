package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredPillagerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmoredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;

public class ArmoredPillagerRenderer
		extends MobRenderer<ArmoredPillagerEntity, ArmoredPillagerModel<ArmoredPillagerEntity>> {
	private static ResourceLocation TEXTURE = GeneralHelper
			.modLoc("textures/entity/illager/armored_pillager_gold.png");

	public ArmoredPillagerRenderer(Context pContext) {
		super(pContext, new ArmoredPillagerModel<>(pContext.bakeLayer(ModModelLayers.ARMORED_PILLAGER_BODY)),
				0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ArmoredPillagerEntity pEntity) {
		return TEXTURE;
	}
}
