package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneMonstrosityModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMonstrosityEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedstoneMonstrosityRenderer extends GeoEntityRenderer<RedstoneMonstrosityEntity> {

	public RedstoneMonstrosityRenderer(Context renderManager) {
		super(renderManager, new RedstoneMonstrosityModel());
		addRenderLayer(new PulsatingGlowLayer<>(this,
				modLoc("textures/entity/redstone/redstone_monstrosity_layer.png"),
				0.1F, 0.5F, 0.0F));
		addRenderLayer(new GeoEyeLayer<>(this,
				modLoc("textures/entity/redstone/redstone_monstrosity_layer.png")));
	}
}
