package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.ocean.DrownedNecromancerModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.VanillaPulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class DrownedNecromancerRenderer
		extends MobRenderer<DrownedNecromancerEntity, DrownedNecromancerModel<DrownedNecromancerEntity>> {

	public DrownedNecromancerRenderer(Context pContext) {
		super(pContext, new DrownedNecromancerModel<>(
				pContext.bakeLayer(ModModelLayers.DROWNED_NECROMANCER_BODY)), 0.5f);
		addLayer(new VanillaPulsatingGlowLayer<>(this, "textures/entity/ocean/drowned_necromancer_eyes.png",
				0.2F, 0.5F, 1.0F));
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(DrownedNecromancerEntity pEntity) {
		return new ResourceLocation(MOD_ID, "textures/entity/ocean/drowned_necromancer.png");
	}

}
