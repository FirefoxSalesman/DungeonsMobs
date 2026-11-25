package net.firefoxsalesman.dungeonsmobs.client.particle;

import dev.obscuria.fragmentum.api.registry.Deferred;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ModParticleTypes {
	public static final Deferred<SimpleParticleType> REDSTONE_SPARK = registerParticle("redstone_spark");
	public static final Deferred<SimpleParticleType> DUST = registerParticle("dust");
	public static final Deferred<SimpleParticleType> WIND = registerParticle("wind");
	public static final Deferred<SimpleParticleType> NECROMANCY = registerParticle("necromancy");
	public static final Deferred<SimpleParticleType> CORRUPTED_DUST = registerParticle("corrupted_dust");
	public static final Deferred<SimpleParticleType> CORRUPTED_MAGIC = registerParticle("corrupted_magic");
	public static final Deferred<SimpleParticleType> ELECTRIC_SHOCK = registerParticle("electric_shock");

	private static Deferred<SimpleParticleType> registerParticle(String name) {
		return DungeonsMobs.REGISTRAR.register(Registries.PARTICLE_TYPE, GeneralHelper.modLoc(name),
				() -> new SimpleParticleType(true)).as();
	}

	public static void init() {
	}
}
