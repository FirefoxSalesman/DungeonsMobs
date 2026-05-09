package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class GoldArmouredVindicatorRenderer extends AbstractArmouredVindicatorRenderer<GoldArmouredVindicatorEntity> {

	public GoldArmouredVindicatorRenderer(Context pContext) {
		super(pContext, GeneralHelper.modLoc("textures/entity/illager/armored_vindicator_gold.png"));
	}
}
