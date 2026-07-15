package net.firefoxsalesman.dungeonsmobs.gear.items;

import net.firefoxsalesman.dungeonsmobs.gear.registry.ItemInit;
import net.firefoxsalesman.dungeonslibs.items.RangedItemModelProperties;

public class GearRangedItemModelProperties {
	public static void init() {
		ItemInit.RANGED_WEAPONS.forEach((resourceLocation, itemRegistryObject) -> RangedItemModelProperties
				.addRangedModelProperties(itemRegistryObject));
	}
}
