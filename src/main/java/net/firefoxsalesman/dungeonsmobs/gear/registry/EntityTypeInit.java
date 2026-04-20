package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfRegenerationEntity;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfShieldingEntity;

public final class EntityTypeInit {

	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

	public static final RegistryObject<EntityType<TotemOfRegenerationEntity>> TOTEM_OF_REGENERATION = ENTITY_TYPES
			.register("totem_of_regeneration", () -> EntityType.Builder
					.of(TotemOfRegenerationEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 1.0F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "totem_of_shielding").toString()));

	public static final RegistryObject<EntityType<TotemOfShieldingEntity>> TOTEM_OF_SHIELDING = ENTITY_TYPES
			.register("totem_of_shielding", () -> EntityType.Builder
					.of(TotemOfShieldingEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 1.0F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "totem_of_shielding").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
