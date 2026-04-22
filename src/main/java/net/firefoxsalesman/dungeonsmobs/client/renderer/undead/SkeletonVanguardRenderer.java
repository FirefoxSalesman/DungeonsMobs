package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.SkeletonVanguardModel;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SkeletonVanguardRenderer
		extends MobRenderer<SkeletonVanguardEntity, SkeletonVanguardModel<SkeletonVanguardEntity>> {

	public SkeletonVanguardRenderer(Context pContext) {
		super(pContext, new SkeletonVanguardModel<>(pContext.bakeLayer(ModModelLayers.SKELETON_VANGUARD_BODY)),
				0.5f);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(SkeletonVanguardEntity pEntity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/skeleton/skeleton_vanguard.png");
	}

}
