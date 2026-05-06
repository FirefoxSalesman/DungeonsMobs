package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class GoldArmouredPillagerRenderer extends AbstractArmouredPillagerRenderer<GoldArmouredPillagerEntity> {
	public GoldArmouredPillagerRenderer(Context pContext) {
		super(pContext, GeneralHelper.modLoc("textures/entity/illager/armored_pillager_gold.png"));
	}
}
