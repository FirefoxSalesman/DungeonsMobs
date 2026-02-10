package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.IceologerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.IceologerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class IceologerRenderer extends MobRenderer<IceologerEntity, IceologerModel<IceologerEntity>> {
	public IceologerRenderer(Context pContext) {
		super(pContext, new IceologerModel<>(pContext.bakeLayer(ModModelLayers.ICEOLOGER_BODY)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(IceologerEntity pEntity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/iceologer.png");
	}
}
