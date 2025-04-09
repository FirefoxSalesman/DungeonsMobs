package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.items.MountaineerAxeItem;
import net.firefoxsalesman.dungeonsmobs.items.armor.MageArmorGear;
import net.firefoxsalesman.dungeonsmobs.items.shield.RoyalGuardShieldItem;
import net.firefoxsalesman.dungeonsmobs.items.shield.VanguardShieldItem;
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
	public static final RegistryObject<Item> ROYAL_GUARD_SHIELD = ITEMS.register("royal_guard_shield",
			() -> new RoyalGuardShieldItem(
					new Item.Properties().durability(336)));

	public static final RegistryObject<Item> VANGUARD_SHIELD = ITEMS.register("vanguard_shield",
			() -> new VanguardShieldItem(
					new Item.Properties().durability(336)));

	// Armour
	public static final ArmorSet CHEF_ARMOR = registerArmorSet("chef_armor", "chef_helmet", "chef_chestplate", null,
			null);
	public static final ArmorSet ROYAL_GUARD_ARMOR = registerArmorSet("royal_guard_armor", "royal_guard_helmet",
			"royal_guard_chestplate", "royal_guard_leggings", "royal_guard_boots");
	public static final ArmorSet VANGUARD_ARMOR = registerArmorSet("vanguard_armor", "vanguard_helmet",
			"vanguard_chestplate", "vanguard_leggings", null);
	public static final ArmorSet MOUNTAINEER_ARMOR = registerArmorSet("mountaineer_armor", "mountaineer_helmet",
			"mountaineer_chestplate", "mountaineer_leggings", "mountaineer_boots");
	public static final ArmorSet MAGE_ARMOR = registerArmorSetMage("mage_armor", "mage_helmet", "mage_chestplate",
			"mage_leggings", "mage_boots");

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

	private static ArmorSet registerArmorSetMage(String armorSetId, String helmetId, String chestId, String legsId,
			String bootsId) {
		ResourceLocation armorSet = new ResourceLocation(MOD_ID, armorSetId);
		ResourceLocation modelLocation = new ResourceLocation(MOD_ID, "geo/armor/" + armorSetId + ".geo.json");
		ResourceLocation textureLocation = new ResourceLocation(MOD_ID,
				"textures/models/armor/" + armorSetId + ".png");
		ResourceLocation animationFileLocation = new ResourceLocation(MOD_ID,
				"animations/armor/cloaked_armor.animation.json");
		return new ArmorSet(
				armorSet,
				registerArmor(helmetId,
						() -> new MageArmorGear(Type.HELMET, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(chestId,
						() -> new MageArmorGear(Type.CHESTPLATE, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(legsId,
						() -> new MageArmorGear(Type.LEGGINGS, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(bootsId, () -> new MageArmorGear(Type.BOOTS, ARMOR_PROPERTIES,
						armorSet, modelLocation, textureLocation, animationFileLocation)));
	}
}
