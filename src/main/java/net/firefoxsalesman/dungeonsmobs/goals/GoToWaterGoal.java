package net.firefoxsalesman.dungeonsmobs.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GoToWaterGoal extends Goal {
	public final PathfinderMob mob;
	public double wantedX;
	public double wantedY;
	public double wantedZ;
	public final double speedModifier;
	public final Level level;

	public GoToWaterGoal(PathfinderMob mob, double speedModifier) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		level = mob.level();
		setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		if (!level.isDay()) {
			return false;
		} else if (mob.isInWater()) {
			return false;
		} else {
			Vec3 vector3d = getWaterPos();
			if (vector3d == null) {
				return false;
			} else {
				wantedX = vector3d.x;
				wantedY = vector3d.y;
				wantedZ = vector3d.z;
				return true;
			}
		}
	}

	public boolean canContinueToUse() {
		return !mob.getNavigation().isDone();
	}

	public void start() {
		mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
	}

	@Nullable
	public Vec3 getWaterPos() {
		RandomSource random = mob.getRandom();
		BlockPos blockpos = mob.blockPosition();

		for (int i = 0; i < 10; ++i) {
			BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8),
					random.nextInt(20) - 10);
			if (level.getBlockState(blockpos1).is(Blocks.WATER)) {
				return Vec3.atBottomCenterOf(blockpos1);
			}
		}

		return null;
	}
}
