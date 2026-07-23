package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.ocean.DrownedNecromancerModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.VanillaPulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class DrownedNecromancerRenderer
		extends MobRenderer<DrownedNecromancerEntity, DrownedNecromancerModel<DrownedNecromancerEntity>> {

	public DrownedNecromancerRenderer(Context pContext) {
		super(pContext, new DrownedNecromancerModel<>(
				pContext.bakeLayer(ModModelLayers.DROWNED_NECROMANCER_BODY)), 0.5f);
		addLayer(new VanillaPulsatingGlowLayer<>(this,
				modLoc("textures/entity/ocean/drowned_necromancer_eyes.png"),
				0.2F, 0.5F, 1.0F));
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(DrownedNecromancerEntity pEntity) {
		return modLoc("textures/entity/ocean/drowned_necromancer.png");
	}

}
