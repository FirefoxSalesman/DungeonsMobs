package net.firefoxsalesman.dungeonsmobs.client.renderer.jungle;

import net.firefoxsalesman.dungeonsmobs.client.models.jungle.QuickGrowingVineModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuickGrowingVineRenderer extends AbstractVineRenderer<QuickGrowingVineModel> {
	public QuickGrowingVineRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new QuickGrowingVineModel());
		addRenderLayer(new GeoEyeLayer<>(this,
				GeneralHelper.modLoc("textures/entity/jungle/quick_growing_vine_glow.png")));
	}
}
