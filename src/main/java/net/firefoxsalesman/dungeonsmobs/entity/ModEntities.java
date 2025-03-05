package net.firefoxsalesman.dungeonsmobs.entity;

import java.util.List;

import com.google.common.base.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.entity.entities.creepers.IcyCreeper;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.JungleZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS,
			Dungeonsmobs.MOD_ID);
	public static final List<String> ENTITY_IDS = new ObjectArrayList<>();

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, Dungeonsmobs.MOD_ID);

	public static final RegistryObject<EntityType<JungleZombie>> JUNGLE_ZOMBIE = registerEntity("jungle_zombie",
			() -> EntityType.Builder.<JungleZombie>of(JungleZombie::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(Dungeonsmobs.MOD_ID, "jungle_zombie").toString()),
			0x4f7d33, 0x00afa8);

	// CREEPER

	public static final RegistryObject<EntityType<IcyCreeper>> ICY_CREEPER = registerEntity("icy_creeper",
			() -> EntityType.Builder.<IcyCreeper>of(IcyCreeper::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(Dungeonsmobs.MOD_ID, "icy_creeper").toString()),
			0x5ccea5, 0xd9eef2);

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
		SPAWN_EGGS.register(eventBus);
	}

	private static <T extends Mob> RegistryObject<EntityType<T>> registerEntity(String key,
			Supplier<EntityType<T>> sup, int primaryColor, int secondaryColor) {
		ENTITY_IDS.add(key);
		RegistryObject<EntityType<T>> entityType = ENTITY_TYPES.register(key, sup);

		SPAWN_EGGS.register(key + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, primaryColor,
				secondaryColor, new Item.Properties()));

		return entityType;
	}
}
