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

	public static final RegistryObject<SimpleParticleType> SNOWFLAKE = registerParticle("snowflake");
	public static final RegistryObject<SimpleParticleType> REDSTONE_SPARK = registerParticle("redstone_spark");
	public static final RegistryObject<SimpleParticleType> DUST = registerParticle("dust");
	public static final RegistryObject<SimpleParticleType> NECROMANCY = registerParticle("necromancy");

	private static RegistryObject<SimpleParticleType> registerParticle(String name) {
		return PARTICLES.register(name, () -> new SimpleParticleType(true));
	}

	public static void register(IEventBus eventBus) {
		PARTICLES.register(eventBus);
	}
}
