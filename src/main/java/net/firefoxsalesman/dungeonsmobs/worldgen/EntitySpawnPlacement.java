package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.levelgen.Heightmap;

public class EntitySpawnPlacement {
	public static SpawnPlacements.Type ON_GROUND_ALLOW_LEAVES;

	public static void createPlacementTypes() {
		ON_GROUND_ALLOW_LEAVES = SpawnPlacements.Type.create("on_ground_allow_leaves",
				((levelReader, blockPos, entityType) -> {
					BlockState blockstate = levelReader.getBlockState(blockPos);
					FluidState fluidstate = levelReader.getFluidState(blockPos);
					BlockPos above = blockPos.above();
					BlockPos below = blockPos.below();
					BlockState stateBelow = levelReader.getBlockState(below);
					if (!stateBelow.isValidSpawn(levelReader, below, SpawnPlacements.Type.ON_GROUND,
							entityType) && !(stateBelow.is(BlockTags.LEAVES))) {
						return false;
					} else {
						return NaturalSpawner.isValidEmptySpawnBlock(levelReader, blockPos,
								blockstate, fluidstate, entityType)
								&& NaturalSpawner.isValidEmptySpawnBlock(levelReader,
										above, levelReader.getBlockState(above),
										levelReader.getFluidState(above),
										entityType);
					}
				}));
	}

	public static void initSpawnPlacements() {
		SpawnPlacements.register(ModEntities.WRAITH.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.JUNGLE_ZOMBIE.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				JungleZombieEntity::canJungleZombieSpawn);
		SpawnPlacements.register(ModEntities.FROZEN_ZOMBIE.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FrozenZombieEntity::canFrozenZombieSpawn);
		SpawnPlacements.register(ModEntities.MOSSY_SKELETON.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				MossySkeletonEntity::canMossySkeletonSpawn);
		SpawnPlacements.register(ModEntities.ICY_CREEPER.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				IcyCreeperEntity::canIcyCreeperSpawn);

		SpawnPlacements.register(ModEntities.SQUALL_GOLEM.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canRaiderSpawn);

		SpawnPlacements.register(ModEntities.REDSTONE_GOLEM.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);

		SpawnPlacements.register(ModEntities.WHISPERER.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				EntitySpawnPlacement::canJungleMobSpawn);
		SpawnPlacements.register(ModEntities.LEAPLEAF.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				EntitySpawnPlacement::canJungleMobSpawn);

		// Ocean
		SpawnPlacements.register(ModEntities.WAVEWHISPERER.get(),
				SpawnPlacements.Type.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::checkAquaticMobSpawnRules);
		SpawnPlacements.register(ModEntities.SUNKEN_SKELETON.get(),
				SpawnPlacements.Type.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::checkAquaticMobSpawnRules);

		// Enderlings
		SpawnPlacements.register(ModEntities.ENDERSENT.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.BLASTLING.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.WATCHLING.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.SNARELING.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
	}

	public static boolean checkAquaticMobSpawnRules(EntityType<? extends Mob> type,
			ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos,
			RandomSource pRandom) {
		if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)) {
			return false;
		} else {
			Holder<Biome> holder = pServerLevel.getBiome(pPos);
			boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL
					&& Monster.isDarkEnoughToSpawn(pServerLevel, pPos, pRandom)
					&& (pMobSpawnType == MobSpawnType.SPAWNER
							|| pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
			if (holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
				return pRandom.nextInt(15) == 0 && flag;
			} else {
				return pRandom.nextInt(40) == 0 && IAquaticMob.isDeepEnoughToSpawn(pServerLevel, pPos)
						&& flag;
			}
		}
	}

	public static boolean canSeeSkyLight(ServerLevelAccessor world, BlockPos blockPos) {
		return world.getBrightness(LightLayer.SKY, blockPos) > 4;
	}

	public static boolean canRaiderSpawn(EntityType<? extends Raider> entityType, ServerLevelAccessor world,
			MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return Monster.checkMonsterSpawnRules(entityType, world, spawnReason, blockPos, rand);
	}

	public static boolean canJungleMobSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor world,
			MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return Monster.checkMonsterSpawnRules(entityType, world, spawnReason, blockPos, rand)
				&& world.getSeaLevel() <= blockPos.getY();
	}
}
