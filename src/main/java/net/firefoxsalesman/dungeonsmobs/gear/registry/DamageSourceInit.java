package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.core.registries.Registries.DAMAGE_TYPE;

import net.minecraft.core.Holder;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class DamageSourceInit {
	public static Holder<DamageType> ELECTRIC_SHOCK;
	public static Holder<DamageType> OFFHAND;

	@SubscribeEvent
	public void registerDamageTypes(ServerStartedEvent event) {
		Registry<DamageType> registry = event.getServer().registryAccess().registry(DAMAGE_TYPE)
				.get();
		ELECTRIC_SHOCK = mkDamageType("electric_shock", registry);
		OFFHAND = mkDamageType("offhand", registry);
	}

	private Holder.Reference<DamageType> mkDamageType(String name, Registry<DamageType> registry) {
		return registry.getHolderOrThrow(ResourceKey.create(DAMAGE_TYPE, new ResourceLocation(MOD_ID, name)));
	}
}
