package net.firefoxsalesman.dungeonsmobs.gear.registry;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.minecraft.core.registries.Registries.DAMAGE_TYPE;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageSourceInit {
	public static ResourceKey<DamageType> OFFHAND;

	@SubscribeEvent
	public void registerDamageTypes(ServerStartedEvent event) {
		OFFHAND = mkDamageType("offhand");
	}

	private ResourceKey<DamageType> mkDamageType(String name) {
		return ResourceKey.create(DAMAGE_TYPE, new ResourceLocation(MOD_ID, name));
	}
}
