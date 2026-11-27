package net.firefoxsalesman.dungeonsmobs.entity;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.obscuria.fragmentum.api.registry.Deferred;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EyeHolderEndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmoredPillagerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmoredVindicatorEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
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
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MooshroomMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.RedstoneMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SlimeballEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.WindcallerBlastProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.MooshroomMonstrosityEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneCubeEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneMonstrosityEntity;
import net.firefoxsalesman.dungeonsmobs.entity.slime.ConjuredSlimeEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerBombEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerWallEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
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
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
	public static final List<ResourceLocation> EGGS = new ArrayList<>();
	public static final List<Deferred<Item>> SPAWN_EGGS = new ArrayList<>();
	public static final List<String> ENTITY_IDS = new ObjectArrayList<>();

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

	// ZOMBIES
	public static final Deferred<EntityType<JungleZombieEntity>> JUNGLE_ZOMBIE = registerEntity(
			"jungle_zombie",
			EntityType.Builder.<JungleZombieEntity>of(JungleZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x4f7d33, 0x00afa8);

	public static final Deferred<EntityType<FrozenZombieEntity>> FROZEN_ZOMBIE = registerEntity(
			"frozen_zombie",
			EntityType.Builder.<FrozenZombieEntity>of(FrozenZombieEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x639694, 0xbae1ec);

	// SKELETONS
	public static final Deferred<EntityType<MossySkeletonEntity>> MOSSY_SKELETON = registerEntity(
			"mossy_skeleton",
			EntityType.Builder.<MossySkeletonEntity>of(MossySkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8),
			0xd6d7c6, 0x4a5d18);

	public static final Deferred<EntityType<SkeletonVanguardEntity>> SKELETON_VANGUARD = registerEntity(
			"skeleton_vanguard",
			EntityType.Builder
					.<SkeletonVanguardEntity>of(SkeletonVanguardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.1F, 1.99F * 1.1F)
					.clientTrackingRange(8),
			0x493615, 0xe8b42f);

	public static final Deferred<EntityType<NecromancerEntity>> NECROMANCER = registerEntity("necromancer",
			EntityType.Builder.<NecromancerEntity>of(NecromancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.3F, 1.99F * 1.3F)
					.clientTrackingRange(8),
			0x3f243d, 0x0b9cbb);

	// ILLAGER
	public static final Deferred<EntityType<RoyalGuardEntity>> ROYAL_GUARD = registerEntity("royal_guard",
			EntityType.Builder.<RoyalGuardEntity>of(RoyalGuardEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.2F, 1.95F * 1.2F)
					.clientTrackingRange(8),
			0x676767, 0x014675);

	public static final Deferred<EntityType<VindicatorChefEntity>> VINDICATOR_CHEF = registerEntity(
			"vindicator_chef",
			EntityType.Builder
					.<VindicatorChefEntity>of(VindicatorChefEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8),
			0x676767, 0x014475);

	public static final Deferred<EntityType<ArmoredPillagerEntity>> ARMORED_PILLAGER = registerEntity(
			"armored_pillager",
			EntityType.Builder
					.<ArmoredPillagerEntity>of(ArmoredPillagerEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8),
			0x676767, 0x014575);

	public static final Deferred<EntityType<ArmoredVindicatorEntity>> ARMORED_VINDICATOR = registerEntity(
			"armored_vindicator",
			EntityType.Builder
					.<ArmoredVindicatorEntity>of(ArmoredVindicatorEntity::new,
							MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8),
			0x676767, 0x014575);

	public static final Deferred<EntityType<IceologerEntity>> ICEOLOGER = registerEntity("iceologer",
			EntityType.Builder.<IceologerEntity>of(IceologerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x173873, 0xb6c6ca);

	public static final Deferred<EntityType<MageEntity>> MAGE = registerEntity("mage",
			EntityType.Builder.of(MageEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x951f75, 0xe3ab58);

	public static final Deferred<EntityType<MageCloneEntity>> MAGE_CLONE = registerEntityWithoutEgg(
			"mage_clone", EntityType.Builder.of(MageCloneEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8));

	public static final Deferred<EntityType<GeomancerEntity>> GEOMANCER = registerEntity("geomancer",
			EntityType.Builder.of(GeomancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x373b3b, 0x8b5ea3);

	public static final Deferred<EntityType<WindcallerEntity>> WINDCALLER = registerEntity("windcaller",
			EntityType.Builder.<WindcallerEntity>of(WindcallerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x348179, 0xdc6c46);

	public static final Deferred<EntityType<MountaineerEntity>> MOUNTAINEER = registerEntity("mountaineer",
			EntityType.Builder.<MountaineerEntity>of(MountaineerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			0x715039, 0xe6e4d4);

	// CREEPER

	public static final Deferred<EntityType<IcyCreeperEntity>> ICY_CREEPER = registerEntity("icy_creeper",
			EntityType.Builder.<IcyCreeperEntity>of(IcyCreeperEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.7F)
					.clientTrackingRange(8),
			0x5ccea5, 0xd9eef2);
	// WRAITH

	public static final Deferred<EntityType<WraithEntity>> WRAITH = registerEntity("wraith",
			EntityType.Builder.of(WraithEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8),
			0x0a2c40, 0x82d8f8);

	// SLIME
	public static final Deferred<EntityType<ConjuredSlimeEntity>> CONJURED_SLIME = registerEntityWithoutEgg(
			"conjured_slime",
			EntityType.Builder.<ConjuredSlimeEntity>of(ConjuredSlimeEntity::new, MobCategory.MONSTER)
					.sized(2.04F, 2.04F)
					.clientTrackingRange(10));

	// REDSTONE
	public static final Deferred<EntityType<RedstoneGolemEntity>> REDSTONE_GOLEM = registerEntity(
			"redstone_golem",
			EntityType.Builder.<RedstoneGolemEntity>of(RedstoneGolemEntity::new, MobCategory.MONSTER)
					.sized(2.66F, 3.83F)
					.clientTrackingRange(10)
					.fireImmune(),
			0xaeaaa6, 0xe3260c);
	public static final Deferred<EntityType<RedstoneMonstrosityEntity>> REDSTONE_MONSTROSITY = registerEntity(
			"redstone_monstrosity",
			EntityType.Builder
					.<RedstoneMonstrosityEntity>of(RedstoneMonstrosityEntity::new,
							MobCategory.MONSTER)
					.sized(5.33F, 7F)
					.clientTrackingRange(10)
					.fireImmune(),
			0xaeaaa6, 0xe3260c);
	public static final Deferred<EntityType<MooshroomMonstrosityEntity>> MOOSHROOM_MONSTROSITY = registerEntity(
			"mooshroom_monstrosity",
			EntityType.Builder
					.<MooshroomMonstrosityEntity>of(MooshroomMonstrosityEntity::new,
							MobCategory.MONSTER)
					.sized(5.33F, 7F)
					.clientTrackingRange(10)
					.fireImmune(),
			0xaeaaa6, 0xe3260c);
	public static final Deferred<EntityType<RedstoneCubeEntity>> REDSTONE_CUBE = registerEntityWithoutEgg(
			"redstone_cube",
			EntityType.Builder.<RedstoneCubeEntity>of(RedstoneCubeEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.fireImmune());
	// GOLEM
	public static final Deferred<EntityType<SquallGolemEntity>> SQUALL_GOLEM = registerEntity("squall_golem",
			EntityType.Builder.<SquallGolemEntity>of(SquallGolemEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 2.75F) // 42 px wide, 29px tall + 16px of height
					.clientTrackingRange(10),
			0x828f8f, 0xffd426);

	// PIGLIN
	public static final Deferred<EntityType<FungusThrowerEntity>> FUNGUS_THROWER = registerEntity(
			"fungus_thrower", EntityType.Builder.of(FungusThrowerEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			10051392, 0x336baf);

	public static final Deferred<EntityType<ZombifiedFungusThrowerEntity>> ZOMBIFIED_FUNGUS_THROWER = registerEntity(
			"zombified_fungus_thrower",
			EntityType.Builder.of(ZombifiedFungusThrowerEntity::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(0.6F, 1.95F)
					.clientTrackingRange(8),
			15373203, 0x336baf);
	// JUNGLE
	public static final Deferred<EntityType<WhispererEntity>> WHISPERER = registerEntity("whisperer",
			EntityType.Builder.of(WhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10),
			0x80a242, 0xe20703);

	public static final Deferred<EntityType<LeapleafEntity>> LEAPLEAF = registerEntity("leapleaf",
			EntityType.Builder.<LeapleafEntity>of(LeapleafEntity::new, MobCategory.MONSTER)
					.sized(1.9F, 1.9F)
					.clientTrackingRange(10),
			0x818a1a, 0x8a54ef);

	public static final Deferred<EntityType<QuickGrowingVineEntity>> QUICK_GROWING_VINE = registerEntity(
			"quick_growing_vine",
			EntityType.Builder.of(QuickGrowingVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10),
			0x90ad49, 0xfbc883);

	public static final Deferred<EntityType<PoisonQuillVineEntity>> POISON_QUILL_VINE = registerEntity(
			"poison_quill_vine",
			EntityType.Builder.of(PoisonQuillVineEntity::new, MobCategory.MONSTER)
					// .fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10),
			0x90ad49, 0x632cbb);

	public static final Deferred<EntityType<QuickGrowingKelpEntity>> QUICK_GROWING_KELP = registerEntity(
			"quick_growing_kelp",
			EntityType.Builder.of(QuickGrowingKelpEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10),
			0x2b9477, 0x0d8f99);

	public static final Deferred<EntityType<PoisonAnemoneEntity>> POISON_ANEMONE = registerEntity(
			"poison_anemone", EntityType.Builder.of(PoisonAnemoneEntity::new, MobCategory.MONSTER)
					.sized(1.0F, 2.5F)
					.clientTrackingRange(10),
			0x2b9477, 0xc436cd);

	// WATER
	public static final Deferred<EntityType<WaveWhispererEntity>> WAVEWHISPERER = registerEntity(
			"wavewhisperer",
			EntityType.Builder.of(WaveWhispererEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 2.25F)
					.clientTrackingRange(10),
			0x48a867, 0x69ebff);

	public static final Deferred<EntityType<DrownedNecromancerEntity>> DROWNED_NECROMANCER = registerEntity(
			"drowned_necromancer",
			EntityType.Builder.of(DrownedNecromancerEntity::new, MobCategory.MONSTER)
					.sized(0.6F * 1.5F, 1.95F * 1.5F)
					.clientTrackingRange(8),
			9433559, 0x274d72);
	public static final Deferred<EntityType<SunkenSkeletonEntity>> SUNKEN_SKELETON = registerEntity(
			"sunken_skeleton", EntityType.Builder.of(SunkenSkeletonEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 1.99F)
					.clientTrackingRange(8),
			0x87a964, 0xc06fe5);

	// ENDER
	public static final Deferred<EntityType<EyeHolderEndersentEntity>> ENDERSENT_EYE_HOLDER = registerEntity(
			"eye_holder_endersent",
			EntityType.Builder.of(EyeHolderEndersentEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 5.6F)
					.clientTrackingRange(8),
			1447446, 0);

	public static final Deferred<EntityType<EndersentEntity>> ENDERSENT = registerEntity(
			"endersent",
			EntityType.Builder.of(EndersentEntity::new, MobCategory.MONSTER)
					.sized(0.8F, 5.6F)
					.clientTrackingRange(8),
			1447446, 0);

	public static final Deferred<EntityType<BlastlingEntity>> BLASTLING = registerEntity("blastling",
			EntityType.Builder.of(BlastlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8),
			0x03030a, 0x8900b0);

	public static final Deferred<EntityType<WatchlingEntity>> WATCHLING = registerEntity("watchling",
			EntityType.Builder.of(WatchlingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8),
			0x110e13, 0xff84f7);

	public static final Deferred<EntityType<SnarelingEntity>> SNARELING = registerEntity("snareling",
			EntityType.Builder.of(SnarelingEntity::new, MobCategory.MONSTER)
					.sized(0.6F, 2.4F)
					.clientTrackingRange(8),
			0x161616, 0xdbe64e);

	// BLAZES

	public static final Deferred<EntityType<WildfireEntity>> WILDFIRE = registerEntity("wildfire",
			EntityType.Builder.of(WildfireEntity::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(0.9F, 2.25F)
					.clientTrackingRange(10),
			0x8b3401, 0xffd528);

	// PROJECTILES
	public static final Deferred<EntityType<SlimeballEntity>> SLIMEBALL = registerEntityWithoutEgg(
			"slimeball",
			EntityType.Builder.<SlimeballEntity>of(SlimeballEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F)
					.clientTrackingRange(4)
					.updateInterval(10));
	public static final Deferred<EntityType<BlueNethershroomEntity>> BLUE_NETHERSHROOM = registerEntityWithoutEgg(
			"blue_nethershroom",
			EntityType.Builder
					.<BlueNethershroomEntity>of(BlueNethershroomEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F)
					.clientTrackingRange(4)
					.updateInterval(10));

	public static final Deferred<EntityType<GeomancerWallEntity>> GEOMANCER_WALL = registerEntityWithoutEgg(
			"geomancer_wall",
			EntityType.Builder.<GeomancerWallEntity>of(GeomancerWallEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2));

	public static final Deferred<EntityType<GeomancerBombEntity>> GEOMANCER_BOMB = registerEntityWithoutEgg(
			"geomancer_bomb",
			EntityType.Builder.<GeomancerBombEntity>of(GeomancerBombEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2));

	public static final Deferred<EntityType<RedstoneMineEntity>> REDSTONE_MINE = registerEntityWithoutEgg(
			"redstone_mine",
			EntityType.Builder.<RedstoneMineEntity>of(RedstoneMineEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 0.5F)
					.clientTrackingRange(6)
					.updateInterval(2));
	public static final Deferred<EntityType<RedstoneMonstrosityProjectileEntity>> REDSTONE_MONSTROSITY_PROJECTILE = registerEntityWithoutEgg(
			"redstone_monstrosity_projectile",
			EntityType.Builder
					.<RedstoneMonstrosityProjectileEntity>of(
							RedstoneMonstrosityProjectileEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.25F, 0.25F)
					.clientTrackingRange(6)
					.updateInterval(2));
	public static final Deferred<EntityType<MooshroomMonstrosityProjectileEntity>> MOOSHROOM_MONSTROSITY_PROJECTILE = registerEntityWithoutEgg(
			"mooshroom_monstrosity_projectile",
			EntityType.Builder
					.<MooshroomMonstrosityProjectileEntity>of(
							MooshroomMonstrosityProjectileEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.25F, 0.25F)
					.clientTrackingRange(6)
					.updateInterval(2));

	public static final Deferred<EntityType<WindcallerTornadoEntity>> TORNADO = registerEntityWithoutEgg(
			"tornado",
			EntityType.Builder
					.<WindcallerTornadoEntity>of(WindcallerTornadoEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 6F)
					.clientTrackingRange(10));

	public static final Deferred<EntityType<WindcallerBlastProjectileEntity>> WINDCALLER_BLAST_PROJECTILE = registerEntityWithoutEgg(
			"windcaller_blast_projectile", EntityType.Builder
					.<WindcallerBlastProjectileEntity>of(WindcallerBlastProjectileEntity::new,
							MobCategory.MISC)
					.fireImmune()
					.sized(2F, 2F));

	public static final Deferred<EntityType<TridentStormEntity>> TRIDENT_STORM = registerEntityWithoutEgg(
			"trident_storm", EntityType.Builder.of(TridentStormEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2F, 32F)
					.clientTrackingRange(10));

	public static final Deferred<EntityType<NecromancerOrbEntity>> NECROMANCER_ORB = registerEntityWithoutEgg(
			"necromancer_orb",
			EntityType.Builder.<NecromancerOrbEntity>of(NecromancerOrbEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1));

	public static final Deferred<EntityType<DrownedNecromancerOrbEntity>> DROWNED_NECROMANCER_ORB = registerEntityWithoutEgg(
			"drowned_necromancer_orb", EntityType.Builder
					.<DrownedNecromancerOrbEntity>of(DrownedNecromancerOrbEntity::new,
							MobCategory.MISC)
					.fireImmune()
					.sized(0.5F, 0.5F)
					.updateInterval(1));

	public static final Deferred<EntityType<PoisonQuillEntity>> POISON_QUILL = registerEntityWithoutEgg(
			"poison_quill",
			EntityType.Builder.<PoisonQuillEntity>of(PoisonQuillEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1));

	public static final Deferred<EntityType<MageMissileEntity>> MAGE_MISSILE = registerEntityWithoutEgg(
			"mage_missile",
			EntityType.Builder.<MageMissileEntity>of(MageMissileEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.35F, 0.35F)
					.updateInterval(1));

	public static final Deferred<EntityType<CobwebProjectileEntity>> COBWEB_PROJECTILE = registerEntityWithoutEgg(
			"cobweb_projectile",
			EntityType.Builder
					.<CobwebProjectileEntity>of(CobwebProjectileEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F)
					.clientTrackingRange(4)
					.updateInterval(10));

	public static final Deferred<EntityType<SimpleTrapEntity>> SIMPLE_TRAP = registerEntityWithoutEgg(
			"simple_trap", EntityType.Builder.of(SimpleTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10));

	public static final Deferred<EntityType<KelpTrapEntity>> KELP_TRAP = registerEntityWithoutEgg("kelp_trap",
			EntityType.Builder.of(KelpTrapEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 0.5F)
					.clientTrackingRange(10));

	public static final Deferred<EntityType<WraithFireEntity>> WRAITH_FIRE = registerEntityWithoutEgg(
			"wraith_fire", EntityType.Builder.of(WraithFireEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(3.25F, 1.25F)
					.clientTrackingRange(10));

	public static final Deferred<EntityType<AreaDamageEntity>> AREA_DAMAGE = registerEntityWithoutEgg(
			"area_damage",
			EntityType.Builder.<AreaDamageEntity>of(AreaDamageEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 1.0F)
					.clientTrackingRange(10)
					.updateInterval(1));

	public static final Deferred<EntityType<BlastlingBulletEntity>> BLASTLING_BULLET = registerEntityWithoutEgg(
			"blastling_bullet",
			EntityType.Builder.<BlastlingBulletEntity>of(BlastlingBulletEntity::new, MobCategory.MISC)
					.sized(0.3F, 0.3F)
					.clientTrackingRange(4)
					.updateInterval(2));

	public static final Deferred<EntityType<SnarelingGlobEntity>> SNARELING_GLOB = registerEntityWithoutEgg(
			"snareling_glob",
			EntityType.Builder.<SnarelingGlobEntity>of(SnarelingGlobEntity::new, MobCategory.MISC)
					.sized(0.6F, 0.6F)
					.clientTrackingRange(4)
					.updateInterval(2));

	public static final Deferred<EntityType<IceCloudEntity>> ICE_CLOUD = registerEntityWithoutEgg("ice_cloud",
			EntityType.Builder.<IceCloudEntity>of(IceCloudEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(2.0F, 1.0F)
					.clientTrackingRange(6)
					.updateInterval(1));

	public static void init() {
	}

	private static <T extends Mob> Deferred<EntityType<T>> registerEntity(String key,
			EntityType.Builder<T> builder, int primaryColor, int secondaryColor) {
		Deferred<EntityType<T>> entityType = registerEntityWithoutEgg(key, builder);
		String eggName = key + "_spawn_egg";
		Deferred<Item> item = DungeonsMobs.REGISTRAR.register(Registries.ITEM, GeneralHelper.modLoc(eggName),
				() -> new ForgeSpawnEggItem(entityType, primaryColor,
						secondaryColor, new Item.Properties()));
		SPAWN_EGGS.add(item);
		EGGS.add(modLoc("models/item/" + eggName));
		return entityType;
	}

	private static <T extends Entity> Deferred<EntityType<T>> registerEntityWithoutEgg(String key,
			EntityType.Builder<T> builder) {
		ENTITY_IDS.add(key);
		Deferred<EntityType<T>> entityType = DungeonsMobs.REGISTRAR.register(Registries.ENTITY_TYPE,
				GeneralHelper.modLoc(key), () -> builder.build(modLoc(key).toString())).as();

		return entityType;
	}

	public static Collection<Deferred<Item>> getEntries() {
		return SPAWN_EGGS;
	}
}
