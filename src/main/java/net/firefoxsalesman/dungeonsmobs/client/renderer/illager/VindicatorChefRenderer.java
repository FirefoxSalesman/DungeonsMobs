package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.VindicatorChefModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.VindicatorChefEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class VindicatorChefRenderer
		extends MobRenderer<VindicatorChefEntity, VindicatorChefModel<VindicatorChefEntity>> {
	public VindicatorChefRenderer(Context pContext) {
		super(pContext, new VindicatorChefModel<>(pContext.bakeLayer(ModModelLayers.VINDICATOR_CHEF_BODY)),
				0.5f);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(VindicatorChefEntity pEntity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/vindicator_chef.png");
	}
}
