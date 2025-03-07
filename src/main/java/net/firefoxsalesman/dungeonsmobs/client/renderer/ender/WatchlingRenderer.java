package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.WatchlingModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WatchlingRenderer extends GeoEntityRenderer<AbstractEnderlingEntity> {
	public WatchlingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WatchlingModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/watchling_eyes.png")));
	}
}
