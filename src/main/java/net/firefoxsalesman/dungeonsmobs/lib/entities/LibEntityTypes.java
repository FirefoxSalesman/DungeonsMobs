package net.firefoxsalesman.dungeonsmobs.lib.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public final class LibEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

	public static final RegistryObject<EntityType<SoulOrbEntity>> SOUL_ORB = ENTITY_TYPES.register("soul_orb",
			() -> EntityType.Builder.<SoulOrbEntity>of(SoulOrbEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(6).updateInterval(20)
					.build(new ResourceLocation(MOD_ID, "soul_orb").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
