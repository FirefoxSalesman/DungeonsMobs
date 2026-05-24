package net.firefoxsalesman.dungeonsmobs.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EyelessEndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredMountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.DiamondArmouredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredMountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GoldArmouredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.IceologerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageCloneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.VindicatorChefEntity;
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
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.DrownedNecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MageMissileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.WindcallerBlastProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneCubeEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMonstrosityEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerBombEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerWallEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
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

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class ModEntities {
	public static final List<ResourceLocation> EGGS = new ArrayList<>();
	public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS,
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
					.build(modLoc("jungle_zombie").toString()),
			0x4f7d33, 0x00afa8);

	public static final RegistryObject<EntityType<FrozenZombieEntity>> FROZEN_ZOMBIE = registerEntity(
			"frozen_zombie",
			() -> EntityType.Builder.<FrozenZombieEntity>of(FrozenZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("frozen_zombie").toString()),
			0x639694, 0xbae1ec);

	// SKELETONS
	public static final RegistryObject<EntityType<MossySkeletonEntity>> MOSSY_SKELETON = registerEntity(
			"mossy_skeleton",
			() -> EntityType.Builder.<MossySkeletonEntity>of(MossySkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(modLoc("mossy_skeleton").toString()),
			0xd6d7c6, 0x4a5d18);

	public static final RegistryObject<EntityType<SkeletonVanguardEntity>> SKELETON_VANGUARD = registerEntity(
			"skeleton_vanguard",
			() -> EntityType.Builder
					.<SkeletonVanguardEntity>of(SkeletonVanguardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.1F, 1.99F * 1.1F)
					.clientTrackingRange(8)
					.build(modLoc("skeleton_vanguard").toString()),
			0x493615, 0xe8b42f);

	public static final RegistryObject<EntityType<NecromancerEntity>> NECROMANCER = registerEntity("necromancer",
			() -> EntityType.Builder.<NecromancerEntity>of(NecromancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.3F, 1.99F * 1.3F)
					.clientTrackingRange(8)
					.build(modLoc("necromancer").toString()),
			0x3f243d, 0x0b9cbb);

	// ILLAGER
	public static final RegistryObject<EntityType<RoyalGuardEntity>> ROYAL_GUARD = registerEntity("royal_guard",
			() -> EntityType.Builder.<RoyalGuardEntity>of(RoyalGuardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.2F, 1.95F * 1.2F)
					.clientTrackingRange(8)
					.build(modLoc("royal_guard").toString()),
			0x676767, 0x014675);

	public static final RegistryObject<EntityType<VindicatorChefEntity>> VINDICATOR_CHEF = registerEntity(
			"vindicator_chef",
			() -> EntityType.Builder
					.<VindicatorChefEntity>of(VindicatorChefEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)
					.build(modLoc("vindicator_chef").toString()),
			0x676767, 0x014475);
	public static final RegistryObject<EntityType<GoldArmouredPillagerEntity>> GOLD_ARMOURED_PILLAGER = registerEntity(
			"gold_armoured_pillager",
			() -> EntityType.Builder
					.<GoldArmouredPillagerEntity>of(GoldArmouredPillagerEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)
					.build(modLoc("gold_armoured_pillager").toString()),
			0x676767, 0x014575);

	public static final RegistryObject<EntityType<DiamondArmouredPillagerEntity>> DIAMOND_ARMOURED_PILLAGER = registerEntity(
			"diamond_armoured_pillager",
			() -> EntityType.Builder
					.<DiamondArmouredPillagerEntity>of(DiamondArmouredPillagerEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)
					.build(modLoc("diamond_armoured_pillager").toString()),
			0x676767, 0x013575);

	public static final RegistryObject<EntityType<GoldArmouredVindicatorEntity>> GOLD_ARMOURED_VINDICATOR = registerEntity(
			"gold_armoured_vindicator",
			() -> EntityType.Builder
					.<GoldArmouredVindicatorEntity>of(GoldArmouredVindicatorEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)
					.build(modLoc("gold_armoured_vindicator").toString()),
			0x676767, 0x014575);

	public static final RegistryObject<EntityType<DiamondArmouredVindicatorEntity>> DIAMOND_ARMOURED_VINDICATOR = registerEntity(
			"diamond_armoured_vindicator",
			() -> EntityType.Builder
					.<DiamondArmouredVindicatorEntity>of(DiamondArmouredVindicatorEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)
					.build(modLoc("diamond_armoured_vindicator").toString()),
			0x676767, 0x013575);

	public static final RegistryObject<EntityType<IceologerEntity>> ICEOLOGER = registerEntity("iceologer",
			() -> EntityType.Builder.<IceologerEntity>of(IceologerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("iceologer").toString()),
			0x173873, 0xb6c6ca);

	public static final RegistryObject<EntityType<MageEntity>> MAGE = registerEntity("mage",
			() -> EntityType.Builder.of(MageEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("mage").toString()),
			0x951f75, 0xe3ab58);

	public static final RegistryObject<EntityType<MageCloneEntity>> MAGE_CLONE = registerEntityWithoutEgg(
			"mage_clone", () -> EntityType.Builder.of(MageCloneEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("mage_clone").toString()));

	public static final RegistryObject<EntityType<GeomancerEntity>> GEOMANCER = registerEntity("geomancer",
			() -> EntityType.Builder.of(GeomancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("geomancer").toString()),
			0x373b3b, 0x8b5ea3);

	public static final RegistryObject<EntityType<WindcallerEntity>> WINDCALLER = registerEntity("windcaller",
			() -> EntityType.Builder.<WindcallerEntity>of(WindcallerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("windcaller").toString()),
			0x348179, 0xdc6c46);

	public static final RegistryObject<EntityType<MountaineerEntity>> MOUNTAINEER = registerEntity("mountaineer",
			() -> EntityType.Builder.<MountaineerEntity>of(MountaineerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("mountaineer").toString()),
			0x715039, 0xe6e4d4);

	public static final RegistryObject<EntityType<GoldArmouredMountaineerEntity>> GOLD_ARMOURED_MOUNTAINEER = registerEntity(
			"gold_armored_mountaineer",
			() -> EntityType.Builder
					.<GoldArmouredMountaineerEntity>of(GoldArmouredMountaineerEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("gold_armored_mountaineer").toString()),
			0x715039, 0xe6e4d4);

	public static final RegistryObject<EntityType<DiamondArmouredMountaineerEntity>> DIAMOND_ARMOURED_MOUNTAINEER = registerEntity(
			"diamond_armored_mountaineer",
			() -> EntityType.Builder
					.<DiamondArmouredMountaineerEntity>of(DiamondArmouredMountaineerEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("diamond_armored_mountaineer").toString()),
			0x715039, 0xe6e4d4);

	// CREEPER

	public static final RegistryObject<EntityType<IcyCreeperEntity>> ICY_CREEPER = registerEntity("icy_creeper",
			() -> EntityType.Builder.<IcyCreeperEntity>of(IcyCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F)
					.clientTrackingRange(8)
					.build(modLoc("icy_creeper").toString()),
			0x5ccea5, 0xd9eef2);
	// WRAITH

	public static final RegistryObject<EntityType<WraithEntity>> WRAITH = registerEntity("wraith",
			() -> EntityType.Builder.of(WraithEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(modLoc("wraith").toString()),
			0x0a2c40, 0x82d8f8);

	// REDSTONE
	public static final RegistryObject<EntityType<RedstoneGolemEntity>> REDSTONE_GOLEM = registerEntity(
			"redstone_golem",
			() -> EntityType.Builder.<RedstoneGolemEntity>of(RedstoneGolemEntity::new, MobCategory.MONSTER)
					.sized(2.66F, 3.83F)
					.clientTrackingRange(10)
					.fireImmune()
					.build(modLoc("redstone_golem").toString()),
			0xaeaaa6, 0xe3260c);
	public static final RegistryObject<EntityType<RedstoneMonstrosityEntity>> REDSTONE_MONSTROSITY = registerEntity(
			"redstone_monstrosity",
			() -> EntityType.Builder
					.<RedstoneMonstrosityEntity>of(RedstoneMonstrosityEntity::new,
							MobCategory.MONSTER)
					.sized(2.66F, 3.83F)
					.clientTrackingRange(10)
					.fireImmune()
					.build(modLoc("redstone_monstrosity").toString()),
			0xaeaaa6, 0xe3260c);
	public static final RegistryObject<EntityType<RedstoneCubeEntity>> REDSTONE_CUBE = registerEntityWithoutEgg(
			"redstone_cube",
			() -> EntityType.Builder.<RedstoneCubeEntity>of(RedstoneCubeEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.fireImmune()
					.build(modLoc("redstone_cube").toString()));
	// GOLEM
	public static final RegistryObject<EntityType<SquallGolemEntity>> SQUALL_GOLEM = registerEntity("squall_golem",
			() -> EntityType.Builder.<SquallGolemEntity>of(SquallGolemEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 2.75F) // 42 px wide, 29px tall + 16px of height
					.clientTrackingRange(10)
					.build(modLoc("squall_golem").toString()),
			0x828f8f, 0xffd426);

	// PIGLIN
	public static final RegistryObject<EntityType<FungusThrowerEntity>> FUNGUS_THROWER = registerEntity(
			"fungus_thrower", () -> EntityType.Builder.of(FungusThrowerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("fungus_thrower").toString()),
			10051392, 0x336baf);

	public static final RegistryObject<EntityType<ZombifiedFungusThrowerEntity>> ZOMBIFIED_FUNGUS_THROWER = registerEntity(
			"zombified_fungus_thrower",
			() -> EntityType.Builder.of(ZombifiedFungusThrowerEntity::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8)
					.build(modLoc("zombified_fungus_thrower").toString()),
			15373203, 0x336baf);
	// JUNGLE
	public static final RegistryObject<EntityType<WhispererEntity>> WHISPERER = registerEntity("whisperer",
			() -> EntityType.Builder.of(WhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10)
					.build(modLoc("whisperer").toString()),
			0x80a242, 0xe20703);

	public static final RegistryObject<EntityType<LeapleafEntity>> LEAPLEAF = registerEntity("leapleaf",
			() -> EntityType.Builder.<LeapleafEntity>of(LeapleafEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 1.9F)
					.clientTrackingRange(10)
					.build(modLoc("leapleaf").toString()),
			0x818a1a, 0x8a54ef);

	public static final RegistryObject<EntityType<QuickGrowingVineEntity>> QUICK_GROWING_VINE = registerEntity(
			"quick_growing_vine",
			() -> EntityType.Builder.of(QuickGrowingVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(modLoc("quick_growing_vine").toString()),
			0x90ad49, 0xfbc883);

	public static final RegistryObject<EntityType<PoisonQuillVineEntity>> POISON_QUILL_VINE = registerEntity(
			"poison_quill_vine",
			() -> EntityType.Builder.of(PoisonQuillVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(modLoc("poison_quill_vine").toString()),
			0x90ad49, 0x632cbb);

	public static final RegistryObject<EntityType<QuickGrowingKelpEntity>> QUICK_GROWING_KELP = registerEntity(
			"quick_growing_kelp",
			() -> EntityType.Builder.of(QuickGrowingKelpEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(modLoc("quick_growing_kelp").toString()),
			0x2b9477, 0x0d8f99);

	public static final RegistryObject<EntityType<PoisonAnemoneEntity>> POISON_ANEMONE = registerEntity(
			"poison_anemone", () -> EntityType.Builder.of(PoisonAnemoneEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10)
					.build(modLoc("poison_anemone").toString()),
			0x2b9477, 0xc436cd);

	// WATER
	public static final RegistryObject<EntityType<WaveWhispererEntity>> WAVEWHISPERER = registerEntity(
			"wavewhisperer",
			() -> EntityType.Builder.of(WaveWhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10)
					.build(modLoc("wavewhisperer").toString()),
			0x48a867, 0x69ebff);

	public static final RegistryObject<EntityType<DrownedNecromancerEntity>> DROWNED_NECROMANCER = registerEntity(
			"drowned_necromancer",
			() -> EntityType.Builder.of(DrownedNecromancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.5F, 1.95F * 1.5F)
					.clientTrackingRange(8)
					.build(modLoc("drowned_necromancer").toString()),
			9433559, 0x274d72);
	public static final RegistryObject<EntityType<SunkenSkeletonEntity>> SUNKEN_SKELETON = registerEntity(
			"sunken_skeleton", () -> EntityType.Builder.of(SunkenSkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8)
					.build(modLoc("sunken_skeleton").toString()),
			0x87a964, 0xc06fe5);

	// ENDER
	public static final RegistryObject<EntityType<EndersentEntity>> ENDERSENT = registerEntity("endersent",
			() -> EntityType.Builder.of(EndersentEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 5.6F)
					.clientTrackingRange(8)
					.build(modLoc("endersent").toString()),
			1447446, 0);

	public static final RegistryObject<EntityType<EyelessEndersentEntity>> EYELESS_ENDERSENT = registerEntity(
			"eyeless_endersent",
			() -> EntityType.Builder.of(EyelessEndersentEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 5.6F)
					.clientTrackingRange(8)
					.build(modLoc("eyeless_endersent").toString()),
			1447446, 0);

	public static final RegistryObject<EntityType<BlastlingEntity>> BLASTLING = registerEntity("blastling",
			() -> EntityType.Builder.of(BlastlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(modLoc("blastling").toString()),
			0x03030a, 0x8900b0);

	public static final RegistryObject<EntityType<WatchlingEntity>> WATCHLING = registerEntity("watchling",
			() -> EntityType.Builder.of(WatchlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(modLoc("watchling").toString()),
			0x110e13, 0xff84f7);

	public static final RegistryObject<EntityType<SnarelingEntity>> SNARELING = registerEntity("snareling",
			() -> EntityType.Builder.of(SnarelingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8)
					.build(modLoc("snareling").toString()),
			0x161616, 0xdbe64e);

	// BLAZES

	public static final RegistryObject<EntityType<WildfireEntity>> WILDFIRE = registerEntity("wildfire",
			() -> EntityType.Builder.of(WildfireEntity::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(0.9F, 2.25F)
					.clientTrackingRange(10)
					.build(modLoc("wildfire").toString()),
			0x8b3401, 0xffd528);

	// PROJECTILES
	public static final RegistryObject<EntityType<BlueNethershroomEntity>> BLUE_NETHERSHROOM = registerEntityWithoutEgg(
			"blue_nethershroom",
			() -> EntityType.Builder
					.<BlueNethershroomEntity>of(BlueNethershroomEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build(modLoc("blue_nethershroom").toString()));

	public static final RegistryObject<EntityType<GeomancerWallEntity>> GEOMANCER_WALL = registerEntityWithoutEgg(
			"geomancer_wall",
			() -> EntityType.Builder.<GeomancerWallEntity>of(GeomancerWallEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(modLoc("geomancer_wall").toString()));

	public static final RegistryObject<EntityType<GeomancerBombEntity>> GEOMANCER_BOMB = registerEntityWithoutEgg(
			"geomancer_bomb",
			() -> EntityType.Builder.<GeomancerBombEntity>of(GeomancerBombEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(modLoc("geomancer_bomb").toString()));

	public static final RegistryObject<EntityType<RedstoneMineEntity>> REDSTONE_MINE = registerEntityWithoutEgg(
			"redstone_mine",
			() -> EntityType.Builder.<RedstoneMineEntity>of(RedstoneMineEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 0.5F)
					.clientTrackingRange(6)
					.updateInterval(2)
					.build(modLoc("redstone_mine").toString()));

	public static final RegistryObject<EntityType<WindcallerTornadoEntity>> TORNADO = registerEntityWithoutEgg(
			"tornado",
			() -> EntityType.Builder
					.<WindcallerTornadoEntity>of(WindcallerTornadoEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 6F)
					.clientTrackingRange(10)
					.build(modLoc("tornado").toString()));

	public static final RegistryObject<EntityType<WindcallerBlastProjectileEntity>> WINDCALLER_BLAST_PROJECTILE = ENTITY_TYPES
			.register("windcaller_blast_projectile", () -> EntityType.Builder
					.<WindcallerBlastProjectileEntity>of(WindcallerBlastProjectileEntity::new,
							MobCategory.MISC)
					.fireImmune()
					.sized(2F, 2F)
					.build(modLoc("windcaller_blast_projectile").toString()));

	public static final RegistryObject<EntityType<TridentStormEntity>> TRIDENT_STORM = registerEntityWithoutEgg(
			"trident_storm", () -> EntityType.Builder.of(TridentStormEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2F, 32F)
					.clientTrackingRange(10)
					.build(modLoc("trident_storm").toString()));

	public static final RegistryObject<EntityType<NecromancerOrbEntity>> NECROMANCER_ORB = ENTITY_TYPES.register(
			"necromancer_orb",
			() -> EntityType.Builder.<NecromancerOrbEntity>of(NecromancerOrbEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1)
					.build(modLoc("necromancer_orb")
							.toString()));

	public static final RegistryObject<EntityType<DrownedNecromancerOrbEntity>> DROWNED_NECROMANCER_ORB = ENTITY_TYPES
			.register("drowned_necromancer_orb", () -> EntityType.Builder
					.<DrownedNecromancerOrbEntity>of(DrownedNecromancerOrbEntity::new,
							MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1)
					.build(modLoc("drowned_necromancer_orb").toString()));

	public static final RegistryObject<EntityType<PoisonQuillEntity>> POISON_QUILL = ENTITY_TYPES.register(
			"poison_quill",
			() -> EntityType.Builder.<PoisonQuillEntity>of(PoisonQuillEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1)
					.build(modLoc("poison_quill").toString()));

	public static final RegistryObject<EntityType<MageMissileEntity>> MAGE_MISSILE = ENTITY_TYPES.register(
			"mage_missile",
			() -> EntityType.Builder.<MageMissileEntity>of(MageMissileEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1)
					.build(modLoc("mage_missile").toString()));

	public static final RegistryObject<EntityType<SummonSpotEntity>> SUMMON_SPOT = registerEntityWithoutEgg(
			"summon_spot",
			() -> EntityType.Builder.<SummonSpotEntity>of(SummonSpotEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.0F)
					.clientTrackingRange(10)
					.build(modLoc("summon_spot").toString()));

	public static final RegistryObject<EntityType<CobwebProjectileEntity>> COBWEB_PROJECTILE = registerEntityWithoutEgg(
			"cobweb_projectile",
			() -> EntityType.Builder
					.<CobwebProjectileEntity>of(CobwebProjectileEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build(modLoc("cobweb_projectile").toString()));

	public static final RegistryObject<EntityType<SimpleTrapEntity>> SIMPLE_TRAP = registerEntityWithoutEgg(
			"simple_trap", () -> EntityType.Builder.of(SimpleTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10)
					.build(modLoc("simple_trap").toString()));

	public static final RegistryObject<EntityType<KelpTrapEntity>> KELP_TRAP = registerEntityWithoutEgg("kelp_trap",
			() -> EntityType.Builder.of(KelpTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10)
					.build(modLoc("kelp_trap").toString()));

	public static final RegistryObject<EntityType<WraithFireEntity>> WRAITH_FIRE = registerEntityWithoutEgg(
			"wraith_fire", () -> EntityType.Builder.of(WraithFireEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 1.25F)
					.clientTrackingRange(10)
					.build(modLoc("wraith_fire").toString()));

	public static final RegistryObject<EntityType<AreaDamageEntity>> AREA_DAMAGE = registerEntityWithoutEgg(
			"area_damage",
			() -> EntityType.Builder.<AreaDamageEntity>of(AreaDamageEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.updateInterval(1)
					.build(modLoc("area_damage").toString()));

	public static final RegistryObject<EntityType<BlastlingBulletEntity>> BLASTLING_BULLET = registerEntityWithoutEgg(
			"blastling_bullet",
			() -> EntityType.Builder.<BlastlingBulletEntity>of(BlastlingBulletEntity::new, MobCategory.MISC)
					.sized(0.3F, 0.3F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(modLoc("blastling_bullet").toString()));

	public static final RegistryObject<EntityType<SnarelingGlobEntity>> SNARELING_GLOB = registerEntityWithoutEgg(
			"snareling_glob",
			() -> EntityType.Builder.<SnarelingGlobEntity>of(SnarelingGlobEntity::new, MobCategory.MISC)
					.sized(0.6F, 0.6F)
					.clientTrackingRange(4)
					.updateInterval(2)
					.build(modLoc("snareling_glob").toString()));

	public static final RegistryObject<EntityType<IceCloudEntity>> ICE_CLOUD = registerEntityWithoutEgg("ice_cloud",
			() -> EntityType.Builder.<IceCloudEntity>of(IceCloudEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 1.0F)
					.clientTrackingRange(6)
					.updateInterval(1)
					.build(modLoc("ice_cloud").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
		SPAWN_EGGS.register(eventBus);
	}

	private static <T extends Mob> RegistryObject<EntityType<T>> registerEntity(String key,
			Supplier<EntityType<T>> sup, int primaryColor, int secondaryColor) {
		ENTITY_IDS.add(key);
		RegistryObject<EntityType<T>> entityType = ENTITY_TYPES.register(key, sup);
		String eggName = key + "_spawn_egg";
		SPAWN_EGGS.register(eggName, () -> new ForgeSpawnEggItem(entityType, primaryColor,
				secondaryColor, new Item.Properties()));
		EGGS.add(modLoc("models/item/" + eggName));
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
