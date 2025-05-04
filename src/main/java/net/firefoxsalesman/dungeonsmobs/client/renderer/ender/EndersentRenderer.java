package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EndersentRenderer extends MobRenderer<EndersentEntity, EndersentModel<EndersentEntity>> {
	public EndersentRenderer(Context pContext) {
		super(pContext, new EndersentModel<>(pContext.bakeLayer(ModModelLayers.ENDERSENT_BODY)), 2);
	}

	@Override
	public ResourceLocation getTextureLocation(EndersentEntity pEntity) {
	    return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/endersent.png");
	}
}
