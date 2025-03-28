package net.firefoxsalesman.dungeonsmobs.entity;

import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public class SpawnEquipmentHelper {
	public static void equipMainhand(ItemStack stack, Mob mob) {
		mob.setItemSlot(EquipmentSlot.MAINHAND, stack);
	}

	public static void equipOffhand(ItemStack stack, Mob mob) {
		mob.setItemSlot(EquipmentSlot.OFFHAND, stack);
	}

	public static void equipArmorSet(ArmorSet armorSet, Mob mobEntity) {
		if (armorSet.getHead() != null) {
			mobEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(armorSet.getHead().get()));
		}
		if (armorSet.getChest() != null) {
			mobEntity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(armorSet.getChest().get()));
		}
		if (armorSet.getLegs() != null) {
			mobEntity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(armorSet.getLegs().get()));
		}
		if (armorSet.getFeet() != null) {
			mobEntity.setItemSlot(EquipmentSlot.FEET, new ItemStack(armorSet.getFeet().get()));
		}
	}
}
