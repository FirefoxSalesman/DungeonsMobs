package net.firefoxsalesman.dungeonsmobs.lib.combat;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.lib.config.DungeonsLibrariesConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfigRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class TwoHandedHandler {

	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (!DungeonsLibrariesConfig.ENABLE_TWO_HANDED_WEAPON.get())
			return;
		MeleeGearConfig configTo = MeleeGearConfigRegistry
				.getConfig(ForgeRegistries.ITEMS.getKey(event.getTo().getItem()));
		if (configTo.isTwoHanded()) {
			if (event.getSlot().equals(EquipmentSlot.MAINHAND)) {
				ItemStack offhandItem = event.getEntity().getOffhandItem();
				if (!offhandItem.isEmpty()) {
					event.getEntity().setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
					event.getEntity().spawnAtLocation(offhandItem);
				}
			} else if (event.getSlot().equals(EquipmentSlot.OFFHAND)) {
				ItemStack mainhandItem = event.getEntity().getMainHandItem();
				event.getEntity().setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
				event.getEntity().setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
				event.getEntity().setItemInHand(InteractionHand.MAIN_HAND, event.getTo());
				if (!mainhandItem.isEmpty()) {
					event.getEntity().spawnAtLocation(mainhandItem);
				}
			}
		} else if (!event.getTo().isEmpty()) {
			ItemStack mainhandItem = event.getEntity().getMainHandItem();
			MeleeGearConfig configMainHand = MeleeGearConfigRegistry
					.getConfig(ForgeRegistries.ITEMS.getKey(mainhandItem.getItem()));
			if (configMainHand.isTwoHanded() && event.getSlot().equals(EquipmentSlot.OFFHAND)) {
				event.getEntity().setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
				event.getEntity().spawnAtLocation(mainhandItem);
			}
		}

	}

}
