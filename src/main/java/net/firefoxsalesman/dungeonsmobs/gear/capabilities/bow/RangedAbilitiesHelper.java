package net.firefoxsalesman.dungeonsmobs.gear.capabilities.bow;

import net.minecraft.world.item.ItemStack;

import static net.firefoxsalesman.dungeonsmobs.gear.capabilities.GearCapabilities.RANGED_ABILITIES_CAPABILITY;

public class RangedAbilitiesHelper {

	public static RangedAbilities getRangedAbilitiesCapability(ItemStack itemStack) {
		return itemStack.getCapability(RANGED_ABILITIES_CAPABILITY).orElse(new RangedAbilities());
	}

}
