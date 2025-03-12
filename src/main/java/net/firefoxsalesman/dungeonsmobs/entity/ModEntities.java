package net.firefoxsalesman.dungeonsmobs.entity;

import java.util.List;

import com.google.common.base.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlastlingBulletEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.SunkenSkeletonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
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
			DungeonsMobs.MOD_ID);
	public static final List<String> ENTITY_IDS = new ObjectArrayList<>();

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, DungeonsMobs.MOD_ID);

	// ZOMBIES
	public static final RegistryObject<EntityType<JungleZombieEntity>> JUNGLE_ZOMBIE = registerEntity(
			"jungle_zombie",
			() -> EntityType.Builder.<JungleZombieEntity>of(JungleZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "jungle_zombie").toString()),
			0x4f7d33, 0x00afa8);

	public static final RegistryObject<EntityType<FrozenZombieEntity>> FROZEN_ZOMBIE = registerEntity(
			"frozen_zombie",
			() -> EntityType.Builder.<FrozenZombieEntity>of(FrozenZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "frozen_zombie").toString()),
			0x639694, 0xbae1ec);

	// SKELETONS
	public static final RegistryObject<EntityType<MossySkeletonEntity>> MOSSY_SKELETON = registerEntity(
			"mossy_skeleton",
			() -> EntityType.Builder.<MossySkeletonEntity>of(MossySkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "mossy_skeleton").toString()),
			0xd6d7c6, 0x4a5d18);
	// CREEPER

	public static final RegistryObject<EntityType<IcyCreeperEntity>> ICY_CREEPER = registerEntity("icy_creeper",
			() -> EntityType.Builder.<IcyCreeperEntity>of(IcyCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "icy_creeper").toString()),
			0x5ccea5, 0xd9eef2);

	// REDSTONE
	public static final RegistryObject<EntityType<RedstoneGolemEntity>> REDSTONE_GOLEM = registerEntity(
			"redstone_golem",
			() -> EntityType.Builder.<RedstoneGolemEntity>of(RedstoneGolemEntity::new, MobCategory.MONSTER)
					.sized(2.66F, 3.83F)
					.clientTrackingRange(10)
					.fireImmune()
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "redstone_golem").toString()),
			0xaeaaa6, 0xe3260c);
	// GOLEM
	public static final RegistryObject<EntityType<SquallGolemEntity>> SQUALL_GOLEM = registerEntity("squall_golem",
			() -> EntityType.Builder.<SquallGolemEntity>of(SquallGolemEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 2.75F) // 42 px wide, 29px tall + 16px of height
					.clientTrackingRange(10)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "squall_golem").toString()),
			0x828f8f, 0xffd426);

	// WATER
	public static final RegistryObject<EntityType<SunkenSkeletonEntity>> SUNKEN_SKELETON = registerEntity(
			"sunken_skeleton", () -> EntityType.Builder.of(SunkenSkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "sunken_skeleton").toString()),
			0x87a964, 0xc06fe5);

	// ENDER
	public static final RegistryObject<EntityType<BlastlingEntity>> BLASTLING = registerEntity("blastling",
			() -> EntityType.Builder.of(BlastlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "blastling").toString()),
			0x03030a, 0x8900b0);

	public static final RegistryObject<EntityType<WatchlingEntity>> WATCHLING = registerEntity("watchling",
			() -> EntityType.Builder.of(WatchlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "watchling").toString()),
			0x110e13, 0xff84f7);

	public static final RegistryObject<EntityType<SnarelingEntity>> SNARELING = registerEntity("snareling",
			() -> EntityType.Builder.of(SnarelingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "snareling").toString()),
			0x161616, 0xdbe64e);

	// PROJECTILES
	public static final RegistryObject<EntityType<RedstoneMineEntity>> REDSTONE_MINE = registerEntityWithoutEgg(
			"redstone_mine",
			() -> EntityType.Builder.<RedstoneMineEntity>of(RedstoneMineEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 0.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "redstone_mine").toString()));

	public static final RegistryObject<EntityType<NecromancerOrbEntity>> NECROMANCER_ORB = ENTITY_TYPES.register(
			"necromancer_orb",
			() -> EntityType.Builder.<NecromancerOrbEntity>of(NecromancerOrbEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "necromancer_orb")
							.toString()));

	public static final RegistryObject<EntityType<AreaDamageEntity>> AREA_DAMAGE = registerEntityWithoutEgg(
			"area_damage",
			() -> EntityType.Builder.<AreaDamageEntity>of(AreaDamageEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.updateInterval(1)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "area_damage").toString()));

	public static final RegistryObject<EntityType<BlastlingBulletEntity>> BLASTLING_BULLET = registerEntityWithoutEgg(
			"blastling_bullet",
			() -> EntityType.Builder.<BlastlingBulletEntity>of(BlastlingBulletEntity::new, MobCategory.MISC)
					.sized(0.3F, 0.3F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "blastling_bullet")
							.toString()));

	public static final RegistryObject<EntityType<SnarelingGlobEntity>> SNARELING_GLOB = registerEntityWithoutEgg(
			"snareling_glob",
			() -> EntityType.Builder.<SnarelingGlobEntity>of(SnarelingGlobEntity::new, MobCategory.MISC)
					.sized(0.6F, 0.6F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(new ResourceLocation(DungeonsMobs.MOD_ID, "snareling_glob").toString()));

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

	private static <T extends Entity> RegistryObject<EntityType<T>> registerEntityWithoutEgg(String key,
			Supplier<EntityType<T>> sup) {
		ENTITY_IDS.add(key);
		RegistryObject<EntityType<T>> entityType = ENTITY_TYPES.register(key, sup);

		return entityType;
	}
}
