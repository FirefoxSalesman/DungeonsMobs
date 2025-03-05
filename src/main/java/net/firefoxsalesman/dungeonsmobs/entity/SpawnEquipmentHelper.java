package net.firefoxsalesman.dungeonsmobs.entity;

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
}
