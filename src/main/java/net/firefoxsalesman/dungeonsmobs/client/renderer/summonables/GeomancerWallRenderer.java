package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.GeomancerConstructModel;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerWallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GeomancerWallRenderer extends GeoEntityRenderer<GeomancerWallEntity> {
	public GeomancerWallRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GeomancerConstructModel<GeomancerWallEntity>("textures/entity/constructs/geomancer_wall.png"));
	}
}
