package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.items.MountaineerAxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
	// SPATULA
	// public static final RegistryObject<Item> WOODEN_LADLE =
	// ITEMS.register("wooden_ladle",
	// () -> new WoodenLadleItem(Tiers.WOOD, 0.5F, (2.0F - 4.0F), new
	// Item.Properties().tab(DungeonsMobs.DUNGEONS_MOBS_ITEMS)));

	// MOUNTAINEER AXES
	public static final RegistryObject<Item> MOUNTAINEER_AXE = ITEMS.register("mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final RegistryObject<Item> GOLD_MOUNTAINEER_AXE = ITEMS.register("gold_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final RegistryObject<Item> DIAMOND_MOUNTAINEER_AXE = ITEMS.register("diamond_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.DIAMOND, 1, (1.2F - 4.0F), new Item.Properties()));

	// TRIDENTS
	// public static final RegistryObject<Item> YELLOW_TRIDENT =
	// ITEMS.register("yellow_trident",
	// () -> new ColoredTridentItem((new
	// Item.Properties().durability(250).tab(DungeonsMobs.DUNGEONS_MOBS_ITEMS)),
	// DyeColor.YELLOW));

	// public static final RegistryObject<Item> PURPLE_TRIDENT =
	// ITEMS.register("purple_trident",
	// () -> new ColoredTridentItem((new
	// Item.Properties().durability(250).tab(DungeonsMobs.DUNGEONS_MOBS_ITEMS)),
	// DyeColor.PURPLE));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
