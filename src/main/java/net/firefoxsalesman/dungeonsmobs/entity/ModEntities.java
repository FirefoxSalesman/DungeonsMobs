package net.firefoxsalesman.dungeonsmobs.entity;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.IceologerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageCloneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.WindcallerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.LeapleafEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.PoisonQuillVineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.QuickGrowingVineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WaveWhispererEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WhispererEntity;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.FungusThrowerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.ZombifiedFungusThrowerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlastlingBulletEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlueNethershroomEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.CobwebProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MageMissileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.WindcallerBlastProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerBombEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerWallEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.PoisonAnemoneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.QuickGrowingKelpEntity;
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

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class ModEntities {
	private static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS,
			MOD_ID);
	public static final List<String> ENTITY_IDS = new ObjectArrayList<>();

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

	// ZOMBIES
	public static final RegistryObject<EntityType<JungleZombieEntity>> JUNGLE_ZOMBIE = registerEntity(
			"jungle_zombie",
			() -> EntityType.Builder.<JungleZombieEntity>of(JungleZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "jungle_zombie").toString()),
			0x4f7d33, 0x00afa8);

	public static final RegistryObject<EntityType<FrozenZombieEntity>> FROZEN_ZOMBIE = registerEntity(
			"frozen_zombie",
			() -> EntityType.Builder.<FrozenZombieEntity>of(FrozenZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "frozen_zombie").toString()),
			0x639694, 0xbae1ec);

	// SKELETONS
	public static final RegistryObject<EntityType<MossySkeletonEntity>> MOSSY_SKELETON = registerEntity(
			"mossy_skeleton",
			() -> EntityType.Builder.<MossySkeletonEntity>of(MossySkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "mossy_skeleton").toString()),
			0xd6d7c6, 0x4a5d18);

	public static final RegistryObject<EntityType<SkeletonVanguardEntity>> SKELETON_VANGUARD = registerEntity(
			"skeleton_vanguard",
			() -> EntityType.Builder
					.<SkeletonVanguardEntity>of(SkeletonVanguardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.1F, 1.99F * 1.1F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "skeleton_vanguard").toString()),
			0x493615, 0xe8b42f);

	// ILLAGER
	public static final RegistryObject<EntityType<RoyalGuardEntity>> ROYAL_GUARD = registerEntity("royal_guard",
			() -> EntityType.Builder.<RoyalGuardEntity>of(RoyalGuardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.2F, 1.95F * 1.2F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "royal_guard").toString()),
			0x676767, 0x014675);

	public static final RegistryObject<EntityType<IceologerEntity>> ICEOLOGER = registerEntity("iceologer",
			() -> EntityType.Builder.<IceologerEntity>of(IceologerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "iceologer").toString()),
			0x173873, 0xb6c6ca);

	public static final RegistryObject<EntityType<MageEntity>> MAGE = registerEntity("mage",
			() -> EntityType.Builder.of(MageEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "mage").toString()),
			0x951f75, 0xe3ab58);

	public static final RegistryObject<EntityType<MageCloneEntity>> MAGE_CLONE = registerEntityWithoutEgg(
			"mage_clone", () -> EntityType.Builder.of(MageCloneEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "mage_clone").toString()));

	public static final RegistryObject<EntityType<GeomancerEntity>> GEOMANCER = registerEntity("geomancer",
			() -> EntityType.Builder.of(GeomancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "geomancer").toString()),
			0x373b3b, 0x8b5ea3);

	public static final RegistryObject<EntityType<WindcallerEntity>> WINDCALLER = registerEntity("windcaller",
			() -> EntityType.Builder.<WindcallerEntity>of(WindcallerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "windcaller").toString()),
			0x348179, 0xdc6c46);

	public static final RegistryObject<EntityType<MountaineerEntity>> MOUNTAINEER = registerEntity("mountaineer",
			() -> EntityType.Builder.<MountaineerEntity>of(MountaineerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "mountaineer").toString()),
			0x715039, 0xe6e4d4);

	// CREEPER

	public static final RegistryObject<EntityType<IcyCreeperEntity>> ICY_CREEPER = registerEntity("icy_creeper",
			() -> EntityType.Builder.<IcyCreeperEntity>of(IcyCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "icy_creeper").toString()),
			0x5ccea5, 0xd9eef2);
	// WRAITH

	public static final RegistryObject<EntityType<WraithEntity>> WRAITH = registerEntity("wraith",
			() -> EntityType.Builder.of(WraithEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "wraith").toString()),
			0x0a2c40, 0x82d8f8);

	// REDSTONE
	public static final RegistryObject<EntityType<RedstoneGolemEntity>> REDSTONE_GOLEM = registerEntity(
			"redstone_golem",
			() -> EntityType.Builder.<RedstoneGolemEntity>of(RedstoneGolemEntity::new, MobCategory.MONSTER)
					.sized(2.66F, 3.83F)
					.clientTrackingRange(10)
					.fireImmune()
					.build(new ResourceLocation(MOD_ID, "redstone_golem").toString()),
			0xaeaaa6, 0xe3260c);
	// GOLEM
	public static final RegistryObject<EntityType<SquallGolemEntity>> SQUALL_GOLEM = registerEntity("squall_golem",
			() -> EntityType.Builder.<SquallGolemEntity>of(SquallGolemEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 2.75F) // 42 px wide, 29px tall + 16px of height
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "squall_golem").toString()),
			0x828f8f, 0xffd426);

	// PIGLIN
	public static final RegistryObject<EntityType<FungusThrowerEntity>> FUNGUS_THROWER = registerEntity(
			"fungus_thrower", () -> EntityType.Builder.of(FungusThrowerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "fungus_thrower").toString()),
			10051392, 0x336baf);

	public static final RegistryObject<EntityType<ZombifiedFungusThrowerEntity>> ZOMBIFIED_FUNGUS_THROWER = registerEntity(
			"zombified_fungus_thrower",
			() -> EntityType.Builder.of(ZombifiedFungusThrowerEntity::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "zombified_fungus_thrower").toString()),
			15373203, 0x336baf);
	// JUNGLE
	public static final RegistryObject<EntityType<WhispererEntity>> WHISPERER = registerEntity("whisperer",
			() -> EntityType.Builder.of(WhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "whisperer").toString()),
			0x80a242, 0xe20703);

	public static final RegistryObject<EntityType<LeapleafEntity>> LEAPLEAF = registerEntity("leapleaf",
			() -> EntityType.Builder.<LeapleafEntity>of(LeapleafEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 1.9F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "leapleaf").toString()),
			0x818a1a, 0x8a54ef);

	public static final RegistryObject<EntityType<QuickGrowingVineEntity>> QUICK_GROWING_VINE = registerEntity(
			"quick_growing_vine",
			() -> EntityType.Builder.of(QuickGrowingVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "quick_growing_vine")
							.toString()),
			0x90ad49, 0xfbc883);

	public static final RegistryObject<EntityType<PoisonQuillVineEntity>> POISON_QUILL_VINE = registerEntity(
			"poison_quill_vine",
			() -> EntityType.Builder.of(PoisonQuillVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "poison_quill_vine")
							.toString()),
			0x90ad49, 0x632cbb);

	public static final RegistryObject<EntityType<QuickGrowingKelpEntity>> QUICK_GROWING_KELP = registerEntity(
			"quick_growing_kelp",
			() -> EntityType.Builder.of(QuickGrowingKelpEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "quick_growing_kelp")
							.toString()),
			0x2b9477, 0x0d8f99);

	public static final RegistryObject<EntityType<PoisonAnemoneEntity>> POISON_ANEMONE = registerEntity(
			"poison_anemone", () -> EntityType.Builder.of(PoisonAnemoneEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "poison_anemone").toString()),
			0x2b9477, 0xc436cd);

	// WATER
	public static final RegistryObject<EntityType<WaveWhispererEntity>> WAVEWHISPERER = registerEntity(
			"wavewhisperer",
			() -> EntityType.Builder.of(WaveWhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "wavewhisperer").toString()),
			0x48a867, 0x69ebff);

	public static final RegistryObject<EntityType<SunkenSkeletonEntity>> SUNKEN_SKELETON = registerEntity(
			"sunken_skeleton", () -> EntityType.Builder.of(SunkenSkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "sunken_skeleton").toString()),
			0x87a964, 0xc06fe5);

	// ENDER
	public static final RegistryObject<EntityType<EndersentEntity>> ENDERSENT = registerEntity("endersent",
			() -> EntityType.Builder.of(EndersentEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 5.6F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "endersent").toString()),
			1447446, 0);

	public static final RegistryObject<EntityType<BlastlingEntity>> BLASTLING = registerEntity("blastling",
			() -> EntityType.Builder.of(BlastlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "blastling").toString()),
			0x03030a, 0x8900b0);

	public static final RegistryObject<EntityType<WatchlingEntity>> WATCHLING = registerEntity("watchling",
			() -> EntityType.Builder.of(WatchlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "watchling").toString()),
			0x110e13, 0xff84f7);

	public static final RegistryObject<EntityType<SnarelingEntity>> SNARELING = registerEntity("snareling",
			() -> EntityType.Builder.of(SnarelingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(new ResourceLocation(MOD_ID, "snareling").toString()),
			0x161616, 0xdbe64e);

	// PROJECTILES
	public static final RegistryObject<EntityType<BlueNethershroomEntity>> BLUE_NETHERSHROOM = registerEntityWithoutEgg(
			"blue_nethershroom",
			() -> EntityType.Builder
					.<BlueNethershroomEntity>of(BlueNethershroomEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build(new ResourceLocation(MOD_ID, "blue_nethershroom").toString()));

	public static final RegistryObject<EntityType<GeomancerWallEntity>> GEOMANCER_WALL = registerEntityWithoutEgg(
			"geomancer_wall",
			() -> EntityType.Builder.<GeomancerWallEntity>of(GeomancerWallEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "geomancer_wall").toString()));

	public static final RegistryObject<EntityType<GeomancerBombEntity>> GEOMANCER_BOMB = registerEntityWithoutEgg(
			"geomancer_bomb",
			() -> EntityType.Builder.<GeomancerBombEntity>of(GeomancerBombEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "geomancer_bomb").toString()));

	public static final RegistryObject<EntityType<RedstoneMineEntity>> REDSTONE_MINE = registerEntityWithoutEgg(
			"redstone_mine",
			() -> EntityType.Builder.<RedstoneMineEntity>of(RedstoneMineEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 0.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "redstone_mine").toString()));

	public static final RegistryObject<EntityType<WindcallerTornadoEntity>> TORNADO = registerEntityWithoutEgg(
			"tornado",
			() -> EntityType.Builder
					.<WindcallerTornadoEntity>of(WindcallerTornadoEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 6F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "tornado").toString()));

	public static final RegistryObject<EntityType<WindcallerBlastProjectileEntity>> WINDCALLER_BLAST_PROJECTILE = ENTITY_TYPES
			.register("windcaller_blast_projectile", () -> EntityType.Builder
					.<WindcallerBlastProjectileEntity>of(WindcallerBlastProjectileEntity::new,
							MobCategory.MISC)
					.fireImmune()
					.sized(2F, 2F)
					.build(new ResourceLocation(MOD_ID, "windcaller_blast_projectile").toString()));

	public static final RegistryObject<EntityType<NecromancerOrbEntity>> NECROMANCER_ORB = ENTITY_TYPES.register(
			"necromancer_orb",
			() -> EntityType.Builder.<NecromancerOrbEntity>of(NecromancerOrbEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1)
					.build(new ResourceLocation(MOD_ID, "necromancer_orb")
							.toString()));

	public static final RegistryObject<EntityType<PoisonQuillEntity>> POISON_QUILL = ENTITY_TYPES.register(
			"poison_quill",
			() -> EntityType.Builder.<PoisonQuillEntity>of(PoisonQuillEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1)
					.build(new ResourceLocation(MOD_ID, "poison_quill").toString()));

	public static final RegistryObject<EntityType<MageMissileEntity>> MAGE_MISSILE = ENTITY_TYPES.register(
			"mage_missile",
			() -> EntityType.Builder.<MageMissileEntity>of(MageMissileEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1)
					.build(new ResourceLocation(MOD_ID, "mage_missile").toString()));

	public static final RegistryObject<EntityType<SummonSpotEntity>> SUMMON_SPOT = registerEntityWithoutEgg(
			"summon_spot",
			() -> EntityType.Builder.<SummonSpotEntity>of(SummonSpotEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.0F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "summon_spot").toString()));

	public static final RegistryObject<EntityType<CobwebProjectileEntity>> COBWEB_PROJECTILE = registerEntityWithoutEgg(
			"cobweb_projectile",
			() -> EntityType.Builder
					.<CobwebProjectileEntity>of(CobwebProjectileEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build(new ResourceLocation(MOD_ID, "cobweb_projectile").toString()));

	public static final RegistryObject<EntityType<SimpleTrapEntity>> SIMPLE_TRAP = registerEntityWithoutEgg(
			"simple_trap", () -> EntityType.Builder.of(SimpleTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "simple_trap").toString()));

	public static final RegistryObject<EntityType<KelpTrapEntity>> KELP_TRAP = registerEntityWithoutEgg("kelp_trap",
			() -> EntityType.Builder.of(KelpTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "kelp_trap").toString()));

	public static final RegistryObject<EntityType<WraithFireEntity>> WRAITH_FIRE = registerEntityWithoutEgg(
			"wraith_fire", () -> EntityType.Builder.of(WraithFireEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 1.25F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(MOD_ID, "wraith_fire").toString()));

	public static final RegistryObject<EntityType<AreaDamageEntity>> AREA_DAMAGE = registerEntityWithoutEgg(
			"area_damage",
			() -> EntityType.Builder.<AreaDamageEntity>of(AreaDamageEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.updateInterval(1)
					.build(new ResourceLocation(MOD_ID, "area_damage").toString()));

	public static final RegistryObject<EntityType<BlastlingBulletEntity>> BLASTLING_BULLET = registerEntityWithoutEgg(
			"blastling_bullet",
			() -> EntityType.Builder.<BlastlingBulletEntity>of(BlastlingBulletEntity::new, MobCategory.MISC)
					.sized(0.3F, 0.3F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "blastling_bullet")
							.toString()));

	public static final RegistryObject<EntityType<SnarelingGlobEntity>> SNARELING_GLOB = registerEntityWithoutEgg(
			"snareling_glob",
			() -> EntityType.Builder.<SnarelingGlobEntity>of(SnarelingGlobEntity::new, MobCategory.MISC)
					.sized(0.6F, 0.6F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(new ResourceLocation(MOD_ID, "snareling_glob").toString()));

	public static final RegistryObject<EntityType<IceCloudEntity>> ICE_CLOUD = registerEntityWithoutEgg("ice_cloud",
			() -> EntityType.Builder.<IceCloudEntity>of(IceCloudEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 1.0F)
					.clientTrackingRange(6)
					.updateInterval(1)
					.build(new ResourceLocation(MOD_ID, "ice_cloud").toString()));

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

	public static Collection<RegistryObject<Item>> getEntries() {
		return SPAWN_EGGS.getEntries();
	}
}
