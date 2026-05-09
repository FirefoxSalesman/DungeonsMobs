package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MountaineerModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbstractMountaineerRenderer<T extends MountaineerEntity> extends MobRenderer<T, MountaineerModel<T>> {
	ResourceLocation textureLocation;

	public AbstractMountaineerRenderer(Context pContext, ResourceLocation textureLocation) {
		super(pContext, new MountaineerModel<>(pContext.bakeLayer(ModModelLayers.MOUNTAINEER_BODY)), 0.5F);
		this.textureLocation = textureLocation;
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return textureLocation;
	}

}
