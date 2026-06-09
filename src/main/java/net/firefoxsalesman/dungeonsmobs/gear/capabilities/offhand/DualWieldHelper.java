package net.firefoxsalesman.dungeonsmobs.gear.capabilities.offhand;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.GearCapabilities;
import net.minecraft.world.entity.Entity;

public class DualWieldHelper {

	public static DualWield getDualWieldCapability(Entity entity) {
		return entity.getCapability(GearCapabilities.DUAL_WIELD_CAPABILITY).orElse(new DualWield());
	}
}
