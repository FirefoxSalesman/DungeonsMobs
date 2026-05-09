package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredMountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DiamondArmouredMountaineerRenderer extends AbstractMountaineerRenderer<DiamondArmouredMountaineerEntity> {
	private static final ResourceLocation TEXTURE = GeneralHelper
			.modLoc("textures/entity/illager/mountaineer_alpine.png");

	public DiamondArmouredMountaineerRenderer(Context pContext) {
		super(pContext, TEXTURE);
	}
}
