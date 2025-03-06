package net.firefoxsalesman.dungeonsmobs.client.particle;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, DungeonsMobs.MOD_ID);

	public static final RegistryObject<SimpleParticleType> SNOWFLAKE = PARTICLES.register("snowflake",
			() -> new SimpleParticleType(true));

	public static void register(IEventBus eventBus) {
		PARTICLES.register(eventBus);
	}
}
