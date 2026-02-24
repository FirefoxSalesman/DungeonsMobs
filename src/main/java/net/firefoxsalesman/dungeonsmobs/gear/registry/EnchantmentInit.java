package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.FreezingEnchantment;

public class EnchantmentInit {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
	public static RegistryObject<FreezingEnchantment> FREEZING = ENCHANTMENTS.register("freezing",
			() -> new FreezingEnchantment());

	public static void register(IEventBus event) {
		ENCHANTMENTS.register(event);
	}
}
