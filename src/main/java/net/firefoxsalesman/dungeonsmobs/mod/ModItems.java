package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.items.MountaineerAxeItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
	private static final ResourceLocation DEFAULT_ANIMATION_RESOURCE = new ResourceLocation(MOD_ID,
			"animations/armor/armor_default.animation.json");

	public static final Map<ResourceLocation, RegistryObject<Item>> ARMORS = new HashMap<>();
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	public static final Item.Properties ARMOR_PROPERTIES = new Item.Properties();

	// Armour
	public static final ArmorSet MOUNTAINEER_ARMOR = registerArmorSet("mountaineer_armor", "mountaineer_helmet",
			"mountaineer_chestplate", "mountaineer_leggings", "mountaineer_boots");

	// SPATULA
	public static final RegistryObject<Item> WOODEN_LADLE = ITEMS.register("wooden_ladle",
			() -> new ShovelItem(Tiers.WOOD, 0.5F, (2.0F - 4.0F), new Item.Properties()));

	// MOUNTAINEER AXES
	public static final RegistryObject<Item> MOUNTAINEER_AXE = ITEMS.register("mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final RegistryObject<Item> GOLD_MOUNTAINEER_AXE = ITEMS.register("gold_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final RegistryObject<Item> DIAMOND_MOUNTAINEER_AXE = ITEMS.register("diamond_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.DIAMOND, 1, (1.2F - 4.0F), new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	public static Collection<RegistryObject<Item>> getEntries() {
		return ITEMS.getEntries();
	}

	private static ArmorSet registerArmorSet(String armorSetId, String helmetId, String chestId, String legsId,
			String bootsId) {
		return registerArmorSet(armorSetId, helmetId, chestId, legsId, bootsId, false);
	}

	private static ArmorSet registerArmorSet(String armorSetId, String helmetId, String chestId, String legsId,
			String bootsId, boolean animated) {
		ResourceLocation armorSet = new ResourceLocation(MOD_ID, armorSetId);
		ResourceLocation modelLocation = new ResourceLocation(MOD_ID, "geo/armor/" + armorSetId + ".geo.json");
		ResourceLocation textureLocation = new ResourceLocation(MOD_ID,
				"textures/models/armor/" + armorSetId + ".png");
		ResourceLocation animationFileLocation = animated
				? new ResourceLocation(MOD_ID, "animations/armor/" + armorSetId + ".animation.json")
				: DEFAULT_ANIMATION_RESOURCE;
		return new ArmorSet(
				armorSet,
				registerArmor(helmetId,
						() -> new ArmorGear(Type.HELMET, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(chestId,
						() -> new ArmorGear(Type.CHESTPLATE, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(legsId,
						() -> new ArmorGear(Type.LEGGINGS, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(bootsId, () -> new ArmorGear(Type.BOOTS, ARMOR_PROPERTIES,
						armorSet, modelLocation, textureLocation, animationFileLocation)));
	}

	private static RegistryObject<Item> registerArmor(String armorId, Supplier<Item> itemSupplier) {
		if (armorId == null)
			return null;
		RegistryObject<Item> register = ITEMS.register(armorId, itemSupplier);
		ARMORS.put(GeneralHelper.modLoc(armorId), register);
		return register;
	}
}
