package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.GeomancerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class GeomancerRenderer extends MobRenderer<GeomancerEntity, GeomancerModel<GeomancerEntity>> {
	public GeomancerRenderer(Context pContext) {
		super(pContext, new GeomancerModel<>(pContext.bakeLayer(ModModelLayers.GEOMANCER_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(GeomancerEntity pEntity) {
		return GeneralHelper.modLoc("textures/entity/illager/geomancer.png");
	}
}
