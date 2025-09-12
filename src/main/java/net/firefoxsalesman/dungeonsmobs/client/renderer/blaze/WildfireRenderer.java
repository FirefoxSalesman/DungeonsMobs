package net.firefoxsalesman.dungeonsmobs.client.renderer.blaze;

import net.firefoxsalesman.dungeonsmobs.client.models.blaze.WildfireModel;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WildfireRenderer extends MobRenderer<WildfireEntity, WildfireModel<WildfireEntity>> {
	public WildfireRenderer(Context pContext) {
		super(pContext, new WildfireModel<>(pContext.bakeLayer(ModModelLayers.WILDFIRE_BODY)), 2);
	}

	@Override
	protected int getBlockLightLevel(WildfireEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(WildfireEntity pEntity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/blaze/wildfire.png");
	}
}
