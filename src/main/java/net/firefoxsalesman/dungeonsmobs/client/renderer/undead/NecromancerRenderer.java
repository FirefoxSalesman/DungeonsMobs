package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.NecromancerModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GenericEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class NecromancerRenderer extends MobRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>> {
	public NecromancerRenderer(Context pContext) {
		super(pContext, new NecromancerModel<>(pContext.bakeLayer(ModModelLayers.NECROMANCER_BODY)), 0.5f);
		addLayer(new GenericEyeLayer<>(this, modLoc("textures/entity/skeleton/necromancer_eyes.png")));
		addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(NecromancerEntity pEntity) {
		return modLoc("textures/entity/skeleton/necromancer.png");
	}
}
