package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMonstrosityEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class RedstoneMonstrosityRenderer extends AbstractMonstrosityRenderer<RedstoneMonstrosityEntity> {

	public RedstoneMonstrosityRenderer(Context renderManager) {
		super(renderManager, "textures/entity/redstone/redstone_monstrosity_death.png",
				"textures/entity/redstone/redstone_monstrosity_eyes.png");
	}
}
