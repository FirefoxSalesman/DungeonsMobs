package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities;
import net.minecraft.world.entity.Entity;

public class AncientHelper {

	public static Ancient getAncientCapability(Entity entity) {
		return entity.getCapability(ModCapabilities.ANCIENT_CAPABILITY).orElse(new Ancient());
	}
}
