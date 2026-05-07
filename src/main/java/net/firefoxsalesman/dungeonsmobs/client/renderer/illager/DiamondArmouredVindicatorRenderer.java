package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class DiamondArmouredVindicatorRenderer
		extends AbstractArmouredVindicatorRenderer<DiamondArmouredVindicatorEntity> {

	public DiamondArmouredVindicatorRenderer(Context pContext) {
		super(pContext, GeneralHelper.modLoc("textures/entity/illager/armored_vindicator_diamond.png"));
	}
}
