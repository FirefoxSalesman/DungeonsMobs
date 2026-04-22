package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.RoyalGuardModel;

public class RoyalGuardRenderer extends MobRenderer<RoyalGuardEntity, RoyalGuardModel<RoyalGuardEntity>> {
	public RoyalGuardRenderer(Context pContext) {
		super(pContext, new RoyalGuardModel<>(pContext.bakeLayer(ModModelLayers.ROYAL_GUARD_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(RoyalGuardEntity pEntity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/royal_guard.png");
	}
}
