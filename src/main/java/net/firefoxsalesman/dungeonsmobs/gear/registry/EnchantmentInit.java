package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.BusyBeeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ChainsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.FreezingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ProspectorEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RadianceEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RampagingEnchantment;

public class EnchantmentInit {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
	public static RegistryObject<FreezingEnchantment> FREEZING = ENCHANTMENTS.register("freezing",
			() -> new FreezingEnchantment());
	public static RegistryObject<BusyBeeEnchantment> BUSY_BEE = ENCHANTMENTS.register("busy_bee",
			() -> new BusyBeeEnchantment());
	public static RegistryObject<ChainsEnchantment> CHAINS = ENCHANTMENTS.register("chains",
			() -> new ChainsEnchantment());
	public static RegistryObject<ProspectorEnchantment> PROSPECTOR = ENCHANTMENTS.register("prospector",
			() -> new ProspectorEnchantment());
	public static RegistryObject<RadianceEnchantment> RADIANCE = ENCHANTMENTS.register("radiance",
			() -> new RadianceEnchantment());
	public static RegistryObject<RampagingEnchantment> RAMPAGING = ENCHANTMENTS.register("rampaging",
			() -> new RampagingEnchantment());

	public static void register(IEventBus event) {
		ENCHANTMENTS.register(event);
	}
}
