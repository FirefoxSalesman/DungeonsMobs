package net.firefoxsalesman.dungeonsmobs.client.renderer.water;

import net.firefoxsalesman.dungeonslibs.capabilities.elite.EliteMob;
import net.firefoxsalesman.dungeonslibs.capabilities.elite.EliteMobHelper;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;

import static net.firefoxsalesman.dungeonslibs.entities.elite.EliteMobConfig.EMPTY_TEXTURE;

public class CustomDrownedRenderer extends DrownedRenderer {
	CustomDrownedOuterLayerRenderer<Drowned> outerLayer;

	public CustomDrownedRenderer(Context pContext) {
		super(pContext);
		layers.remove(4);
		outerLayer = new CustomDrownedOuterLayerRenderer<>(this, pContext.getModelSet());
		addLayer(outerLayer);
	}

	@Override
	public ResourceLocation getTextureLocation(Zombie pEntity) {
		EliteMob cap = EliteMobHelper.getEliteMobCapability(pEntity);
		if (cap.isElite() && !cap.getTexture().equals(EMPTY_TEXTURE)) {
			ResourceLocation texture = cap.getTexture();
			if (texture.equals(GeneralHelper.modLoc("textures/entity/ocean/seaweed_armored_drowned.png"))) {
				outerLayer.setEliteState(DrownedEliteState.SEAWEED);
			} else if (texture.equals(
					GeneralHelper.modLoc("textures/entity/ocean/pale_armored_drowned.png"))) {
				outerLayer.setEliteState(DrownedEliteState.PALE);
			} else {
				outerLayer.setEliteState(DrownedEliteState.ORDINARY);
			}
		} else {
			outerLayer.setEliteState(DrownedEliteState.ORDINARY);
		}

		return super.getTextureLocation(pEntity);
	}
}
