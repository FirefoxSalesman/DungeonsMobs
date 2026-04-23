package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.entities.ArtifactBeamEntity;
import net.firefoxsalesman.dungeonsmobs.gear.entities.BuzzyNestEntity;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfRegenerationEntity;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfShieldingEntity;
import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfSoulProtectionEntity;

public final class EntityTypeInit {

	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

	public static final RegistryObject<EntityType<BuzzyNestEntity>> BUZZY_NEST = registerTotem("buzzy_nest",
			BuzzyNestEntity::new);

	public static final RegistryObject<EntityType<TotemOfRegenerationEntity>> TOTEM_OF_REGENERATION = registerTotem(
			"totem_of_regeneration", TotemOfRegenerationEntity::new);

	public static final RegistryObject<EntityType<TotemOfShieldingEntity>> TOTEM_OF_SHIELDING = registerTotem(
			"totem_of_shielding", TotemOfShieldingEntity::new);

	public static final RegistryObject<EntityType<TotemOfSoulProtectionEntity>> TOTEM_OF_SOUL_PROTECTION = registerTotem(
			"totem_of_soul_protection", TotemOfSoulProtectionEntity::new);

	public static final RegistryObject<EntityType<ArtifactBeamEntity>> BEAM_ENTITY = registerTotem("beam_entity",
			ArtifactBeamEntity::new);

	private static <P extends Entity> RegistryObject<EntityType<P>> registerTotem(String name,
			EntityType.EntityFactory<P> factory) {
		return ENTITY_TYPES.register(name,
				() -> EntityType.Builder.<P>of(factory, MobCategory.MISC).fireImmune()
						.setShouldReceiveVelocityUpdates(false).sized(2.0F, 1.0F)
						.clientTrackingRange(6).updateInterval(2)
						.build(new ResourceLocation(MOD_ID, name).toString()));
	}

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
