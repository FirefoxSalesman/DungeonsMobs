package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import net.firefoxsalesman.dungeonsmobs.entity.redstone.MooshroomMonstrosityEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class MooshroomMonstrosityRenderer extends AbstractMonstrosityRenderer<MooshroomMonstrosityEntity> {

	public MooshroomMonstrosityRenderer(Context renderManager) {
		super(renderManager, "textures/entity/redstone/mooshroom_monstrosity_full.png",
				"textures/entity/redstone/mooshroom_monstrosity_eyes.png");
	}
}
