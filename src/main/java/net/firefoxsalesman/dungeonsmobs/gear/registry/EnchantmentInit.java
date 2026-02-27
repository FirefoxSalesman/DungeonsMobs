package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.BusyBeeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ChainsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.CommittedEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ExplodingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.FreezingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.IllagersBaneEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.LeechingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ProspectorEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RadianceEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RampagingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RushdownEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.StunningEnchantment;

public class EnchantmentInit {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
	public static RegistryObject<FreezingEnchantment> FREEZING = ENCHANTMENTS.register("freezing",
			() -> new FreezingEnchantment());
	public static RegistryObject<BusyBeeEnchantment> BUSY_BEE = ENCHANTMENTS.register("busy_bee",
			() -> new BusyBeeEnchantment());
	public static RegistryObject<ChainsEnchantment> CHAINS = ENCHANTMENTS.register("chains",
			() -> new ChainsEnchantment());
	public static RegistryObject<CommittedEnchantment> COMMITTED = ENCHANTMENTS.register("committed",
			() -> new CommittedEnchantment());
	public static RegistryObject<ExplodingEnchantment> EXPLODING = ENCHANTMENTS.register("exploding",
			() -> new ExplodingEnchantment());
	public static RegistryObject<IllagersBaneEnchantment> ILLAGERS_BANE = ENCHANTMENTS.register("illagers_bane",
			() -> new IllagersBaneEnchantment());
	public static RegistryObject<LeechingEnchantment> LEECHING = ENCHANTMENTS.register("leeching",
			() -> new LeechingEnchantment());
	public static RegistryObject<ProspectorEnchantment> PROSPECTOR = ENCHANTMENTS.register("prospector",
			() -> new ProspectorEnchantment());
	public static RegistryObject<RadianceEnchantment> RADIANCE = ENCHANTMENTS.register("radiance",
			() -> new RadianceEnchantment());
	public static RegistryObject<RampagingEnchantment> RAMPAGING = ENCHANTMENTS.register("rampaging",
			() -> new RampagingEnchantment());
	public static RegistryObject<RushdownEnchantment> RUSHDOWN = ENCHANTMENTS.register("rushdown",
			() -> new RushdownEnchantment());
	public static RegistryObject<StunningEnchantment> STUNNING = ENCHANTMENTS.register("stunning",
			() -> new StunningEnchantment());

	public static void register(IEventBus event) {
		ENCHANTMENTS.register(event);
	}
}
