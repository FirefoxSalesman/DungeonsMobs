package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MountaineerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class MountaineerRenderer extends MobRenderer<MountaineerEntity, MountaineerModel<MountaineerEntity>> {
	private static final ResourceLocation TEXTURE = GeneralHelper.modLoc("textures/entity/illager/mountaineer.png");

	public MountaineerRenderer(Context pContext) {
		super(pContext, new MountaineerModel<>(pContext.bakeLayer(ModModelLayers.MOUNTAINEER_BODY)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(MountaineerEntity pEntity) {
		return TEXTURE;
	}
}
