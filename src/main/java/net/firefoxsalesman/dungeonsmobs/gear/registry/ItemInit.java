package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.firefoxsalesman.dungeonsmobs.gear.items.melee.ShearsGear;
import net.firefoxsalesman.dungeonsmobs.gear.items.melee.StaffGear;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.AxeGear;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemInit {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
	public static final Item.Properties MELEE_WEAPON_PROPERTIES = new Item.Properties();
	public static final Map<ResourceLocation, RegistryObject<Item>> MELEE_WEAPONS = new HashMap<>();
	private static Supplier<Item> meleeSupplier = () -> new MeleeGear(MELEE_WEAPON_PROPERTIES);
	private static Supplier<Item> staffSupplier = () -> new StaffGear(MELEE_WEAPON_PROPERTIES);
	private static Supplier<Item> axeSupplier = () -> new AxeGear(MELEE_WEAPON_PROPERTIES);

	// DPS 9.6 (19.2), crits once per 2.5 (1.25) seconds
	public static final RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger", meleeSupplier);
	public static final RegistryObject<Item> FANG_OF_FROST = registerMeleeWeapon("fang_of_frost", meleeSupplier);
	public static final RegistryObject<Item> MOON_DAGGER = registerMeleeWeapon("moon_dagger", meleeSupplier);
	public static final RegistryObject<Item> SHEAR_DAGGER = registerMeleeWeapon("shear_dagger",
			() -> new ShearsGear(MELEE_WEAPON_PROPERTIES));
	// DPS 10 (20), crits once per 3 (1.5) seconds
	public static final RegistryObject<Item> SICKLE = registerMeleeWeapon("sickle", meleeSupplier);
	public static final RegistryObject<Item> NIGHTMARES_BITE = registerMeleeWeapon("nightmares_bite",
			meleeSupplier);
	public static final RegistryObject<Item> THE_LAST_LAUGH = registerMeleeWeapon("the_last_laugh", meleeSupplier);
	// DPS 9.6/8 (19.2/16), crits once per 2.92/1 (1.46/0.5) seconds
	public static final RegistryObject<Item> GAUNTLET = registerMeleeWeapon("gauntlet", meleeSupplier);
	public static final RegistryObject<Item> FIGHTERS_BINDING = registerMeleeWeapon("fighters_binding",
			meleeSupplier);
	public static final RegistryObject<Item> MAULER = registerMeleeWeapon("mauler", meleeSupplier);
	public static final RegistryObject<Item> SOUL_FIST = registerMeleeWeapon("soul_fist", meleeSupplier);

	// DPS 8, crits once per 3.5 seconds
	public static final RegistryObject<Item> RAPIER = registerMeleeWeapon("rapier", meleeSupplier);
	public static final RegistryObject<Item> BEE_STINGER = registerMeleeWeapon("bee_stinger", meleeSupplier);
	public static final RegistryObject<Item> FREEZING_FOIL = registerMeleeWeapon("freezing_foil", meleeSupplier);

	// DPS 9.1, crits once per 1.53 seconds
	public static final RegistryObject<Item> SOUL_SCYTHE = registerMeleeWeapon("soul_scythe", meleeSupplier);
	public static final RegistryObject<Item> FROST_SCYTHE = registerMeleeWeapon("frost_scythe", meleeSupplier);
	public static final RegistryObject<Item> JAILORS_SCYTHE = registerMeleeWeapon("jailors_scythe", meleeSupplier);

	// DPS 7.2, crits once per 1.11 seconds
	public static final RegistryObject<Item> CUTLASS = registerMeleeWeapon("cutlass", meleeSupplier);
	public static final RegistryObject<Item> DANCERS_SWORD = registerMeleeWeapon("dancers_sword", meleeSupplier);
	public static final RegistryObject<Item> NAMELESS_BLADE = registerMeleeWeapon("nameless_blade", meleeSupplier);
	public static final RegistryObject<Item> SPARKLER = registerMeleeWeapon("sparkler", meleeSupplier);

	// DPS 9.6, crits once per 1.875 seconds
	public static final RegistryObject<Item> HAWKBRAND = registerMeleeWeapon("hawkbrand", meleeSupplier);
	public static final RegistryObject<Item> SINISTER_SWORD = registerMeleeWeapon("sinister_sword", meleeSupplier);

	// DPS 12, crits once per 5 seconds
	public static final RegistryObject<Item> BATTLESTAFF = registerMeleeWeapon("battlestaff", staffSupplier);
	public static final RegistryObject<Item> BATTLESTAFF_OF_TERROR = registerMeleeWeapon("battlestaff_of_terror",
			staffSupplier);
	public static final RegistryObject<Item> GROWING_STAFF = registerMeleeWeapon("growing_staff", staffSupplier);

	// DPS 10, crits once per 3 seconds, disables shields
	public static final RegistryObject<Item> FIREBRAND = registerMeleeWeapon("firebrand", axeSupplier);
	public static final RegistryObject<Item> HIGHLAND_AXE = registerMeleeWeapon("highland_axe", axeSupplier);

	// DPS 9, crits once per 1.1 seconds, disables shields
	public static final RegistryObject<Item> DOUBLE_AXE = registerMeleeWeapon("double_axe", axeSupplier);
	public static final RegistryObject<Item> CURSED_AXE = registerMeleeWeapon("cursed_axe", axeSupplier);
	public static final RegistryObject<Item> WHIRLWIND = registerMeleeWeapon("whirlwind", axeSupplier);

	// DPS 9.8, crits once per 2.1 seconds, disables shields
	public static final RegistryObject<Item> MACE = registerMeleeWeapon("mace", meleeSupplier);
	public static final RegistryObject<Item> FLAIL = registerMeleeWeapon("flail", meleeSupplier);
	public static final RegistryObject<Item> SUNS_GRACE = registerMeleeWeapon("suns_grace", meleeSupplier);

	// DPS 8.8, crits once per 0.9 seconds, disables shields
	public static final RegistryObject<Item> GREAT_HAMMER = registerMeleeWeapon("great_hammer", meleeSupplier);
	public static final RegistryObject<Item> HAMMER_OF_GRAVITY = registerMeleeWeapon("hammer_of_gravity",
			meleeSupplier);
	public static final RegistryObject<Item> STORMLANDER = registerMeleeWeapon("stormlander", meleeSupplier);

	// DPS 11.2, crits once per 2.1 seconds
	public static final RegistryObject<Item> KATANA = registerMeleeWeapon("katana", meleeSupplier);
	public static final RegistryObject<Item> DARK_KATANA = registerMeleeWeapon("dark_katana", meleeSupplier);
	public static final RegistryObject<Item> MASTERS_KATANA = registerMeleeWeapon("masters_katana", meleeSupplier);

	// DPS 7.2, crits once per 1.25 seconds
	public static final RegistryObject<Item> SOUL_KNIFE = registerMeleeWeapon("soul_knife", meleeSupplier);
	public static final RegistryObject<Item> ETERNAL_KNIFE = registerMeleeWeapon("eternal_knife", meleeSupplier);
	public static final RegistryObject<Item> TRUTHSEEKER = registerMeleeWeapon("truthseeker", meleeSupplier);

	// DPS 9.6, crits once per 3.3 seconds
	public static final RegistryObject<Item> CLAYMORE = registerMeleeWeapon("claymore", meleeSupplier);
	public static final RegistryObject<Item> BROADSWORD = registerMeleeWeapon("broadsword", meleeSupplier);
	public static final RegistryObject<Item> HEARTSTEALER = registerMeleeWeapon("heartstealer", meleeSupplier);
	// public static final RegistryObject<Item> GREAT_AXEBLADE =
	// registerMeleeWeapon("great_axeblade", meleeSupplier);
	public static final RegistryObject<Item> FROST_SLAYER = registerMeleeWeapon("frost_slayer", meleeSupplier);

	// DPS 8.4, crits once per 2.14 seconds, +2 reach
	// public static final RegistryObject<Item> SPEAR = registerMeleeWeapon("spear",
	// meleeSupplier);
	// public static final RegistryObject<Item> FORTUNE_SPEAR =
	// registerMeleeWeapon("fortune_spear", meleeSupplier);
	// public static final RegistryObject<Item> WHISPERING_SPEAR =
	// registerMeleeWeapon("whispering_spear",
	// meleeSupplier);

	// DPS 9.6, crits once per 2.5 seconds, +2 reach
	// public static final RegistryObject<Item> GLAIVE =
	// registerMeleeWeapon("glaive",
	// meleeSupplier);
	// public static final RegistryObject<Item> GRAVE_BANE =
	// registerMeleeWeapon("grave_bane",
	// meleeSupplier);
	// public static final RegistryObject<Item> VENOM_GLAIVE =
	// registerMeleeWeapon("venom_glaive",
	// meleeSupplier);

	// DPS 7.2, crits once per 1.25 seconds
	public static final RegistryObject<Item> TEMPEST_KNIFE = registerMeleeWeapon("tempest_knife", meleeSupplier);
	public static final RegistryObject<Item> RESOLUTE_TEMPEST_KNIFE = registerMeleeWeapon("resolute_tempest_knife",
			meleeSupplier);
	public static final RegistryObject<Item> CHILL_GALE_KNIFE = registerMeleeWeapon("chill_gale_knife",
			meleeSupplier);

	// DPS 10.4, crits once per 2.5 seconds
	public static final RegistryObject<Item> BONECLUB = registerMeleeWeapon("boneclub", meleeSupplier);
	public static final RegistryObject<Item> BONE_CUDGEL = registerMeleeWeapon("bone_cudgel", meleeSupplier);

	// DPS 8, crits once per 1.25 seconds
	public static final RegistryObject<Item> ANCHOR = registerMeleeWeapon("anchor", meleeSupplier);
	public static final RegistryObject<Item> ENCRUSTED_ANCHOR = registerMeleeWeapon("encrusted_anchor",
			meleeSupplier);

	private static RegistryObject<Item> registerMeleeWeapon(String meleeWeaponId, Supplier<Item> itemSupplier) {
		RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
		MELEE_WEAPONS.put(GeneralHelper.modLoc(meleeWeaponId), register);
		return register;
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	public static Collection<RegistryObject<Item>> getEntries() {
		return ITEMS.getEntries();
	}
}
