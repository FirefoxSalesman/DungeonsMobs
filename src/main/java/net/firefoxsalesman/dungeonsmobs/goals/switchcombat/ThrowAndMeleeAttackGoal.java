package net.firefoxsalesman.dungeonsmobs.goals.switchcombat;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.SnowballItem;

import java.util.EnumSet;

public class ThrowAndMeleeAttackGoal<T extends PathfinderMob & RangedAttackMob> extends MeleeAttackGoal {
    public final T hostCreature;
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public ThrowAndMeleeAttackGoal(T rangedAttackMob, double speedAmplifier, int attackInterval, float maxDistance, boolean useLongMemory) {
        super(rangedAttackMob, speedAmplifier, useLongMemory);
        rangedAttackTime = -1;
        hostCreature = rangedAttackMob;
        entityMoveSpeed = speedAmplifier;
        attackIntervalMin = attackInterval;
        maxRangedAttackTime = attackInterval;
        attackRadius = maxDistance;
        maxAttackDistance = maxDistance * maxDistance;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if (hasThrowableItemInMainhand()) {
            LivingEntity attackTarget = hostCreature.getTarget();
            return attackTarget != null && attackTarget.isAlive();
        } else {
            return super.canUse();
        }
    }

    public boolean hasThrowableItemInMainhand() {
        return hostCreature.getMainHandItem().getItem() instanceof SnowballItem
                | hostCreature.getMainHandItem().getItem() instanceof EggItem;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (hasThrowableItemInMainhand()) {
            return canUse() || !hostCreature.getNavigation().isDone();
        } else {
            return super.canContinueToUse();
        }
    }

    /**
     * Resets the task
     */
    public void stop() {
        if (hasThrowableItemInMainhand()) {
            seeTime = 0;
            rangedAttackTime = -1;
        } else {
            super.stop();
        }
    }

    /**
     * Updates the task
     */
    public void tick() {
        LivingEntity attackTarget = hostCreature.getTarget();
        if (hasThrowableItemInMainhand() && attackTarget != null) {
            float hostDistanceSq = (float) hostCreature.distanceToSqr(attackTarget.getX(), attackTarget.getY(), attackTarget.getZ());
            boolean canSee = hostCreature.getSensing().hasLineOfSight(attackTarget);
            if (canSee) {
                ++seeTime;
            } else {
                seeTime = 0;
            }

            if (hostDistanceSq <= (double) maxAttackDistance && seeTime >= 5) {
                hostCreature.getNavigation().stop();
            } else {
                hostCreature.getNavigation().moveTo(attackTarget, entityMoveSpeed);
            }

            hostCreature.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);
            float distanceOverAttackRadius;
            if (--rangedAttackTime == 0) {
                if (!canSee) {
                    return;
                }

                distanceOverAttackRadius = Mth.sqrt(hostDistanceSq) / attackRadius;
                float clampedDistanceOverAttackRadius = Mth.clamp(distanceOverAttackRadius, 0.1F, 1.0F);

                // Used to animate snowball or egg throwing
                if (hasThrowableItemInMainhand()) hostCreature.swing(InteractionHand.MAIN_HAND);

                hostCreature.performRangedAttack(attackTarget, clampedDistanceOverAttackRadius);
                rangedAttackTime = Mth.floor(distanceOverAttackRadius * (float) (maxRangedAttackTime - attackIntervalMin) + (float) attackIntervalMin);
            } else if (rangedAttackTime < 0) {
                distanceOverAttackRadius = Mth.sqrt(hostDistanceSq) / attackRadius;
                rangedAttackTime = Mth.floor(distanceOverAttackRadius * (float) (maxRangedAttackTime - attackIntervalMin) + (float) attackIntervalMin);
            }
        } else if (attackTarget != null) {
            super.tick();
        }
    }
}
