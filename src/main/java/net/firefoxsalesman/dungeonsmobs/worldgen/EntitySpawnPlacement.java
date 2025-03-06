package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.entities.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.MossySkeletonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
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
	}

	public static boolean canSeeSkyLight(ServerLevelAccessor world, BlockPos blockPos) {
		return world.getBrightness(LightLayer.SKY, blockPos) > 4;
	}
}
