package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredMountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class GoldArmouredMountaineerRenderer extends AbstractMountaineerRenderer<GoldArmouredMountaineerEntity> {
	private static final ResourceLocation TEXTURE = GeneralHelper
			.modLoc("textures/entity/illager/mountaineer_expedition.png");

	public GoldArmouredMountaineerRenderer(Context pContext) {
		super(pContext, TEXTURE);
	}
}
