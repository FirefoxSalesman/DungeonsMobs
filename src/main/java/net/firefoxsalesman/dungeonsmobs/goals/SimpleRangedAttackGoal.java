package net.firefoxsalesman.dungeonsmobs.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SimpleRangedAttackGoal<T extends Mob> extends Goal {
    protected final T mob;
    protected final BiConsumer<T, LivingEntity> performRangedAttack;
    protected final Predicate<ItemStack> weaponPredicate;
    protected LivingEntity target;
    protected int attackTime = -1;
    protected final double speedModifier;
    protected int seeTime;
    protected final int attackIntervalMin;
    protected final int attackIntervalMax;
    protected final float attackRadius;
    protected final float attackRadiusSqr;

    public SimpleRangedAttackGoal(T mob, Predicate<ItemStack> weaponPredicate, BiConsumer<T, LivingEntity> performRangedAttack, double speedModifier, int attackInterval, float attackRadius) {
        this(mob, weaponPredicate, performRangedAttack, speedModifier, attackInterval, attackInterval, attackRadius);
    }

    public SimpleRangedAttackGoal(T mob, Predicate<ItemStack> weaponPredicate, BiConsumer<T, LivingEntity> performRangedAttack, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
        this.mob = mob;
        this.weaponPredicate = weaponPredicate;
        this.performRangedAttack = performRangedAttack;
        this.speedModifier = speedModifier;
        this.attackIntervalMin = attackIntervalMin;
        this.attackIntervalMax = attackIntervalMax;
        this.attackRadius = attackRadius;
        this.attackRadiusSqr = attackRadius * attackRadius;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if (!mob.isHolding(weaponPredicate)) {
            return false;
        }
        LivingEntity livingentity = mob.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            target = livingentity;
            return true;
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (!mob.isHolding(weaponPredicate)) {
            return false;
        }
        return canUse() || !mob.getNavigation().isDone();
    }

    public void stop() {
        target = null;
        seeTime = 0;
        attackTime = -1;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        double d0 = mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean flag = mob.getSensing().hasLineOfSight(target);
        if (flag) {
            ++seeTime;
        } else {
            seeTime = 0;
        }

        if (!(d0 > (double) attackRadiusSqr) && seeTime >= 5) {
            mob.getNavigation().stop();
        } else {
            mob.getNavigation().moveTo(target, speedModifier);
        }

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (--attackTime == 0) {
            if (!flag) {
                return;
            }

            float f = Mth.sqrt((float) d0) / attackRadius;
            performRangedAttack.accept(mob, target);
            attackTime = Mth.floor(f * (float) (attackIntervalMax - attackIntervalMin) + (float) attackIntervalMin);
        } else if (attackTime < 0) {
            attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) attackRadius, (double) attackIntervalMin, (double) attackIntervalMax));
        }

    }
}
