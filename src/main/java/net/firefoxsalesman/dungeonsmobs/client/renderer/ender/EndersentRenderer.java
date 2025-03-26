package net.firefoxsalesman.dungeonsmobs.client.renderer.ender;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EndersentRenderer extends GeoEntityRenderer<AbstractEnderlingEntity> {
	public EndersentRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new EndersentModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/endersent_eyes.png")));
	}

	protected float getDeathMaxRotation(AbstractEnderlingEntity p_77037_1_) {
		return 0.0F;
	}
}
