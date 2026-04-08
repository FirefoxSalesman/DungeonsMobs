package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.firefoxsalesman.dungeonsmobs.gear.items.melee.ShearsGear;
import net.firefoxsalesman.dungeonsmobs.gear.items.melee.StaffGear;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper;
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

	// DPS 9.6 (19.2), crits once per 2.5 (1.25) seconds
	public static final RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger", meleeSupplier);
	public static final RegistryObject<Item> FANG_OF_FROST = registerMeleeWeapon("fang_of_frost", meleeSupplier);
	// public static final RegistryObject<Item> MOON_DAGGER =
	// registerMeleeWeapon("moon_dagger",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> SHEAR_DAGGER =
	// registerMeleeWeapon("shear_dagger",
	// () -> new ShearsGear(MELEE_WEAPON_PROPERTIES));
	// DPS 10 (20), crits once per 3 (1.5) seconds
	public static final RegistryObject<Item> SICKLE = registerMeleeWeapon("sickle", meleeSupplier);
	// public static final RegistryObject<Item> NIGHTMARES_BITE =
	// registerMeleeWeapon("nightmares_bite",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	public static final RegistryObject<Item> THE_LAST_LAUGH = registerMeleeWeapon("the_last_laugh", meleeSupplier);
	// DPS 9.6/8 (19.2/16), crits once per 2.92/1 (1.46/0.5) seconds
	public static final RegistryObject<Item> GAUNTLET = registerMeleeWeapon("gauntlet", meleeSupplier);
	public static final RegistryObject<Item> FIGHTERS_BINDING = registerMeleeWeapon("fighters_binding",
			meleeSupplier);
	public static final RegistryObject<Item> MAULER = registerMeleeWeapon("mauler", meleeSupplier);
	// public static final RegistryObject<Item> SOUL_FIST =
	// registerMeleeWeapon("soul_fist",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));

	// DPS 8, crits once per 3.5 seconds
	public static final RegistryObject<Item> RAPIER = registerMeleeWeapon("rapier", meleeSupplier);
	// public static final RegistryObject<Item> BEE_STINGER =
	// registerMeleeWeapon("bee_stinger",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	public static final RegistryObject<Item> FREEZING_FOIL = registerMeleeWeapon("freezing_foil", meleeSupplier);

	// DPS 9.1, crits once per 1.53 seconds
	// public static final RegistryObject<Item> SOUL_SCYTHE =
	// registerMeleeWeapon("soul_scythe",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
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

	// DPS 9.8, crits once per 2.1 seconds, disables shields
	public static final RegistryObject<Item> MACE = registerMeleeWeapon("mace", meleeSupplier);
	public static final RegistryObject<Item> FLAIL = registerMeleeWeapon("flail", meleeSupplier);
	public static final RegistryObject<Item> SUNS_GRACE = registerMeleeWeapon("suns_grace", meleeSupplier);

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
