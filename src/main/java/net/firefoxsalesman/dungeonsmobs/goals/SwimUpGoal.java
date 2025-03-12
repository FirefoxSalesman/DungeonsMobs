package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class SwimUpGoal<T extends PathfinderMob & IAquaticMob> extends Goal {
    private final T aquaticMob;
    private final double speedModifier;
    private final int seaLevel;
    private boolean stuck;

    public SwimUpGoal(T aquaticMob, double speedModifier, int seaLevel) {
        this.aquaticMob = aquaticMob;
        this.speedModifier = speedModifier;
        this.seaLevel = seaLevel;
    }

    public boolean canUse() {
        return !aquaticMob.level().isDay() && aquaticMob.isInWater() && aquaticMob.getY() < (double) (seaLevel - 2);
    }

    public boolean canContinueToUse() {
        return canUse() && !stuck;
    }

    public void tick() {
        if (aquaticMob.getY() < (double) (seaLevel - 1) && (aquaticMob.getNavigation().isDone() || aquaticMob.closeToNextPos(aquaticMob))) {
            Vec3 vector3d = DefaultRandomPos.getPosTowards(aquaticMob, 4, 8, new Vec3(aquaticMob.getX(), seaLevel - 1, aquaticMob.getZ()), (float) Math.PI / 2F);
            if (vector3d == null) {
                stuck = true;
                return;
            }

            aquaticMob.getNavigation().moveTo(vector3d.x, vector3d.y, vector3d.z, speedModifier);
        }

    }

    public void start() {
        aquaticMob.setSearchingForLand(true);
        stuck = false;
    }

    public void stop() {
        aquaticMob.setSearchingForLand(false);
    }
}
