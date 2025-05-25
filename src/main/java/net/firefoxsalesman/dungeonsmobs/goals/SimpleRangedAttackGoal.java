package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IWebShooter;
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
    
    protected int rangedTimer = 0;
    public LivingEntity movePos;

    // If set to 'true' the attacker moves close enough for ranged attacks
    // If set to 'false' the attacker always moves as close as it can (default: true)
    public boolean keepDistance = true;
    
    // If set to 'false' this will allow the attacker to shoot while moving (default: true)
    public boolean useRangedTimer = true;

    // Only useful if 'useRangedTimer' is true
    // This controls the time before the attack becomes stationary before it shoots
    // If set to '0' the attacker will always be stationary (default: 30)
    public int rangedTimeSet = 30;

    // Only useful if 'useRangedTimer' is true
    // This controls how long it takes from stationary to actually shoot
    // Increase this to make the attacker stay still for longer (default: 0)
    public int rangedTimeFin = rangedTimeSet + 0; 

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
        attackRadiusSqr = attackRadius * attackRadius;
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

    // Modified tick to enable entity moving while shooting
    public void tick() {
        double d0 = mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        double maxDistance = 180; // 180 is roughly 13 blocks away
        double minDistance = 90;
        boolean hasLineOfSight = mob.getSensing().hasLineOfSight(target);

        if (useRangedTimer){
            rangedTimer++;
        }

        if (hasLineOfSight) {
            ++seeTime;
        } else {
            seeTime = 0;
        }
        
        if (keepDistance){
            if (d0 >= maxDistance) {
                // Target is out of range: move toward them
                movePos = target;
                rangedTimer = 0;  // Reset timer while chasing
            } else if (d0 <= minDistance) {
                // Target is in range: stop moving
                movePos = mob;
            }
            if (movePos != null){
                mob.getNavigation().moveTo(movePos, speedModifier);
            }
        }
        else{
            if (rangedTimer <= rangedTimeSet){
                // Pursue the target while rangedTimer is less than the set time
                mob.getNavigation().moveTo(target, speedModifier);
            }
        }

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        // ### RANGED ATTACK TIMING ###
        boolean isTargetTrapped = (mob instanceof IWebShooter shooter) && shooter.isTargetTrapped();

        if (!isTargetTrapped) {
            if (rangedTimer >= rangedTimeFin || !useRangedTimer || keepDistance){
                if (--attackTime == 0) {
                    if (hasLineOfSight) {
                        float distanceFactor = Mth.sqrt((float) d0) / attackRadius;
                        performRangedAttack.accept(mob, target);

                        rangedTimer = 0;

                        attackTime = Mth.floor(distanceFactor * (attackIntervalMax - attackIntervalMin) + attackIntervalMin);
                    }
                } else if (attackTime < 0) {
                    float distanceFactor = Mth.sqrt((float) d0) / attackRadius;
                    attackTime = Mth.floor(Mth.lerp(distanceFactor, attackIntervalMin, attackIntervalMax));
                }
            }
        }
    }

}
