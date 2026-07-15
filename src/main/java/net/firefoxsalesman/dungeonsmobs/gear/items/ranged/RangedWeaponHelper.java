package net.firefoxsalesman.dungeonsmobs.gear.items.ranged;

import net.firefoxsalesman.dungeonsmobs.gear.registry.ItemInit;
import net.firefoxsalesman.dungeonslibs.items.interfaces.IRangedWeapon;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class RangedWeaponHelper {
	public static List<Item> getRangedWeaponList(boolean unique) {
		return ItemInit.RANGED_WEAPONS.values().stream().map(RegistryObject::get).filter(
				entry -> entry instanceof IRangedWeapon && ((IRangedWeapon) entry).isUnique() == unique)
				.collect(Collectors.toList());
	}
}
