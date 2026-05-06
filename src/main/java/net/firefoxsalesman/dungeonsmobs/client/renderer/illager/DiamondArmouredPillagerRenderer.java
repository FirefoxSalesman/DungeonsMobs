package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class DiamondArmouredPillagerRenderer extends AbstractArmouredPillagerRenderer<DiamondArmouredPillagerEntity> {
	public DiamondArmouredPillagerRenderer(Context pContext) {
		super(pContext, GeneralHelper.modLoc("textures/entity/illager/armored_pillager_diamond.png"));
	}
}
