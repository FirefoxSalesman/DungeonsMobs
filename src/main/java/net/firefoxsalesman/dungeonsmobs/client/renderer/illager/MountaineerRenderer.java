package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class MountaineerRenderer extends AbstractMountaineerRenderer<MountaineerEntity> {
	private static final ResourceLocation TEXTURE = GeneralHelper.modLoc("textures/entity/illager/mountaineer.png");

	public MountaineerRenderer(Context pContext) {
		super(pContext, TEXTURE);
	}
}
