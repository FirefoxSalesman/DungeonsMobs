package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.firefoxsalesman.dungeonsmobs.gear.items.melee.ShearsGear;
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

	// DPS 9.6 (19.2), crits once per 2.5 (1.25) seconds
	public static final RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger",
			() -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> FANG_OF_FROST =
	// registerMeleeWeapon("fang_of_frost",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> MOON_DAGGER =
	// registerMeleeWeapon("moon_dagger",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> SHEAR_DAGGER =
	// registerMeleeWeapon("shear_dagger",
	// () -> new ShearsGear(MELEE_WEAPON_PROPERTIES));
	// DPS 10 (20), crits once per 3 (1.5) seconds
	public static final RegistryObject<Item> SICKLE = registerMeleeWeapon("sickle",
			() -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> NIGHTMARES_BITE =
	// registerMeleeWeapon("nightmares_bite",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> THE_LAST_LAUGH =
	// registerMeleeWeapon("the_last_laugh",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// DPS 9.6/8 (19.2/16), crits once per 2.92/1 (1.46/0.5) seconds
	public static final RegistryObject<Item> GAUNTLET = registerMeleeWeapon("gauntlet",
			() -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	public static final RegistryObject<Item> FIGHTERS_BINDING = registerMeleeWeapon("fighters_binding",
			() -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> MAULER =
	// registerMeleeWeapon("mauler",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));
	// public static final RegistryObject<Item> SOUL_FIST =
	// registerMeleeWeapon("soul_fist",
	// () -> new MeleeGear(MELEE_WEAPON_PROPERTIES));

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
