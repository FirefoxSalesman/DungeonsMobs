package net.firefoxsalesman.dungeonsmobs.mod;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.obscuria.fragmentum.api.registry.Deferred;
import kotlin.jvm.functions.Function0;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.items.BlueNethershroomItem;
import net.firefoxsalesman.dungeonsmobs.items.ColoredTridentItem;
import net.firefoxsalesman.dungeonsmobs.items.GeomancerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.IceWandItem;
import net.firefoxsalesman.dungeonsmobs.items.MountaineerAxeItem;
import net.firefoxsalesman.dungeonsmobs.items.NecromancerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.NecromancerTridentItem;
import net.firefoxsalesman.dungeonsmobs.items.WindcallerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.armor.WindcallerArmorGear;
import net.firefoxsalesman.dungeonsmobs.items.shield.RoyalGuardShieldItem;
import net.firefoxsalesman.dungeonsmobs.items.shield.VanguardShieldItem;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tiers;

public class ModItems {
	private static List<Deferred<Item>> ITEMS = new ArrayList<>();
	public static final Map<ResourceLocation, Deferred<Item>> ARMORS = new HashMap<>();

	public static final Map<ResourceLocation, Deferred<Item>> ARTIFACTS = new HashMap<>();

	public static final Item.Properties ARMOR_PROPERTIES = new Item.Properties();
	public static final Deferred<Item> ROYAL_GUARD_SHIELD = registerItems("royal_guard_shield",
			() -> new RoyalGuardShieldItem(
					new Item.Properties().durability(336)));

	public static final Deferred<Item> VANGUARD_SHIELD = registerItems("vanguard_shield",
			() -> new VanguardShieldItem(
					new Item.Properties().durability(336)));

	// Armour
	public static final ArmorSet WINDCALLER_ARMOR = registerArmorSetWindcaller("windcaller_armor",
			"windcaller_helmet", "windcaller_chestplate", null, null);

	// SPATULA
	public static final Deferred<Item> WOODEN_LADLE = registerItems("wooden_ladle",
			() -> new ShovelItem(Tiers.WOOD, 0.5F, (2.0F - 4.0F), new Item.Properties()));

	// MOUNTAINEER AXES
	public static final Deferred<Item> MOUNTAINEER_AXE = registerItems("mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final Deferred<Item> GOLD_MOUNTAINEER_AXE = registerItems("gold_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final Deferred<Item> DIAMOND_MOUNTAINEER_AXE = registerItems("diamond_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.DIAMOND, 1, (1.2F - 4.0F), new Item.Properties()));

	// ARTIFACTS
	public static final Deferred<Item> WINDCALLER_STAFF = registerArtifact("windcaller_staff",
			() -> new WindcallerStaffItem(new Item.Properties()));
	public static final Deferred<Item> GEOMANCER_STAFF = registerArtifact("geomancer_staff",
			() -> new GeomancerStaffItem(new Item.Properties()));
	public static final Deferred<Item> NECROMANCER_STAFF = registerArtifact("necromancer_staff",
			() -> new NecromancerStaffItem(new Item.Properties()));
	public static final Deferred<Item> NECROMANCER_TRIDENT = registerArtifact("necromancer_trident",
			() -> new NecromancerTridentItem(new Item.Properties()));
	public static final Deferred<Item> ICE_WAND = registerArtifact("ice_wand",
			() -> new IceWandItem(new Item.Properties()));

	// PROJECTILES
	public static final Deferred<Item> BLUE_NETHERSHROOM = registerItems("blue_nethershroom",
			() -> new BlueNethershroomItem(new Item.Properties().stacksTo(16)));
	// TRIDENTS
	public static final Deferred<Item> YELLOW_TRIDENT = registerItems("yellow_trident",
			() -> new ColoredTridentItem((new Item.Properties().durability(250)), DyeColor.YELLOW));

	public static final Deferred<Item> PURPLE_TRIDENT = registerItems("purple_trident",
			() -> new ColoredTridentItem((new Item.Properties().durability(250)), DyeColor.PURPLE));

	private static Deferred<Item> registerArmor(String armorId, Function0<Item> itemSupplier) {
		if (armorId == null)
			return null;
		Deferred<Item> item = registerItems(armorId, itemSupplier);
		ARMORS.put(GeneralHelper.modLoc(armorId), item);
		return item;
	}

	private static ArmorSet registerArmorSetWindcaller(String armorSetId, String helmetId, String chestId,
			String legsId, String bootsId) {
		ResourceLocation armorSet = modLoc(armorSetId);
		ResourceLocation modelLocation = modLoc("geo/armor/" + armorSetId + ".geo.json");
		ResourceLocation textureLocation = modLoc("textures/models/armor/" + armorSetId + ".png");
		ResourceLocation animationFileLocation = modLoc("animations/armor/cloaked_armor.animation.json");
		return new ArmorSet(
				armorSet,
				registerArmor(helmetId,
						() -> new WindcallerArmorGear(Type.HELMET, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(chestId,
						() -> new WindcallerArmorGear(Type.CHESTPLATE, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(legsId,
						() -> new WindcallerArmorGear(Type.LEGGINGS, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(bootsId,
						() -> new WindcallerArmorGear(Type.BOOTS, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)));
	}

	public static List<Deferred<Item>> getEntries() {
		return ITEMS;
	}

	public static void init() {
	}

	private static Deferred<Item> registerItems(String name, Function0<? extends Item> fn) {
		Deferred<Item> item = DungeonsMobs.REGISTRAR.register(Registries.ITEM, GeneralHelper.modLoc(name), fn);
		ITEMS.add(item);
		return item;
	}

	private static Deferred<Item> registerArtifact(String meleeWeaponId, Function0<Item> itemSupplier) {
		Deferred<Item> register = registerItems(meleeWeaponId, itemSupplier);
		ARTIFACTS.put(GeneralHelper.modLoc(meleeWeaponId), register);
		return register;
	}
}
