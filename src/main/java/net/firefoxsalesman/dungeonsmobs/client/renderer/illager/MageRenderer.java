package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MageModel;
import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class MageRenderer<T extends AbstractIllager & KeyframeEntity> extends MobRenderer<T, MageModel<T>> {
	public MageRenderer(Context pContext) {
		super(pContext, new MageModel<>(pContext.bakeLayer(ModModelLayers.MAGE_BODY)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return GeneralHelper.modLoc("textures/entity/illager/mage.png");
	}
}
