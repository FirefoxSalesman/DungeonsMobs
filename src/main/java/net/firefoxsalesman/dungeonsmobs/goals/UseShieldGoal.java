package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IShieldUser;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;

public class UseShieldGoal extends Goal {

    public int blockingFor;
    public int blockDuration;
    public int maxBlockDuration;
    public int blockChance;
    public int stopChanceAfterDurationEnds;
    public double blockDistance;
    public boolean guaranteedBlockIfTargetNotVisible;

    public PathfinderMob mob;
    @Nullable
    public LivingEntity target;

    public UseShieldGoal(PathfinderMob attackingMob, double blockDistance, int blockDuration, int maxBlockDuration, int stopChanceAfterDurationEnds, int blockChance, boolean guaranteedBlockIfTargetNotVisible) {
        blockDuration = maxBlockDuration;
        mob = attackingMob;
        target = attackingMob.getTarget();
        this.blockChance = blockChance;
        this.maxBlockDuration = maxBlockDuration;
        this.stopChanceAfterDurationEnds = stopChanceAfterDurationEnds;
        this.blockDistance = blockDistance;
        this.guaranteedBlockIfTargetNotVisible = guaranteedBlockIfTargetNotVisible;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean shouldBlockForTarget(LivingEntity target) {
        return !(target instanceof Mob) || ((Mob) target).getTarget() == null || ((Mob) target).getTarget() == mob;
    }

    public boolean isShieldDisabled(PathfinderMob shieldUser) {
        return shieldUser instanceof IShieldUser && ((IShieldUser) shieldUser).isShieldDisabled();
    }

    @Override
    public boolean canUse() {
        target = mob.getTarget();
        return target != null && !isShieldDisabled(mob) && shouldBlockForTarget(target) && (((mob.getRandom().nextInt(blockChance) == 0 && mob.distanceTo(target) <= blockDistance && mob.hasLineOfSight(target) && mob.getOffhandItem().canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) || mob.isBlocking()) || (guaranteedBlockIfTargetNotVisible && !mob.hasLineOfSight(target)));
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && mob.invulnerableTime <= 0 && !isShieldDisabled(mob) && mob.getOffhandItem().canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK);
    }

    @Override
    public void start() {
        mob.startUsingItem(InteractionHand.OFF_HAND);
    }

    @Override
    public void tick() {
        target = mob.getTarget();
        blockingFor++;

        if ((blockingFor >= blockDuration && mob.getRandom().nextInt(stopChanceAfterDurationEnds) == 0) || blockingFor >= maxBlockDuration) {
            stop();
        }
    }

    @Override
    public void stop() {
        mob.stopUsingItem();
        blockingFor = 0;
    }

}
