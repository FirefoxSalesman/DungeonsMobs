package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class GoToBeachGoal<T extends PathfinderMob & IAquaticMob> extends MoveToBlockGoal {
	private final T aquaticMob;

	public GoToBeachGoal(T aquaticMob, double speedModifier) {
		super(aquaticMob, speedModifier, 8, 2);
		this.aquaticMob = aquaticMob;
	}

	public boolean canUse() {
		return super.canUse() && !aquaticMob.level().isDay() && aquaticMob.isInWater()
				&& aquaticMob.getY() >= (double) (aquaticMob.level().getSeaLevel() - 3);
	}

	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}

	protected boolean isValidTarget(LevelReader worldReader, BlockPos blockPos) {
		BlockPos blockpos = blockPos.above();
		return worldReader.isEmptyBlock(blockpos) && worldReader.isEmptyBlock(blockpos.above()) && worldReader
				.getBlockState(blockPos).entityCanStandOn(worldReader, blockPos, aquaticMob);
	}

	public void start() {
		aquaticMob.setSearchingForLand(false);
		aquaticMob.setNavigation(aquaticMob.getGroundNavigation());
		super.start();
	}

	public void stop() {
		super.stop();
	}
}
