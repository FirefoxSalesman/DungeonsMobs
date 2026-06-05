package net.firefoxsalesman.dungeonsmobs.lib.combat;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.lib.config.DungeonsLibrariesConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfigRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class DualWieldHandler {
	public static void switchHand(ServerPlayer player) {
		if (!DungeonsLibrariesConfig.ENABLE_DUAL_WIELDING.get())
			return;
		ItemStack mainHandItem = player.getMainHandItem();
		ItemStack offHandItem = player.getOffhandItem();
		MeleeGearConfig config = MeleeGearConfigRegistry
				.getConfig(ForgeRegistries.ITEMS.getKey(mainHandItem.getItem()));
		if (config.isLight() && !offHandItem.isEmpty() && offHandItem.getItem() instanceof TieredItem) {
			player.setItemInHand(InteractionHand.MAIN_HAND, player.getOffhandItem());
			player.setItemInHand(InteractionHand.OFF_HAND, mainHandItem);
		}
	}
}
