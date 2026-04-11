package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MageModel;
import net.firefoxsalesman.dungeonsmobs.interfaces.AnimatedMage;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class MageRenderer<T extends AbstractIllager & AnimatedMage> extends MobRenderer<T, MageModel<T>> {
	public MageRenderer(Context pContext) {
		super(pContext, new MageModel<>(pContext.bakeLayer(ModModelLayers.MAGE_BODY)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/mage.png");
	}
}
