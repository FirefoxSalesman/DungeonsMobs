package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IWebShooter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class RangedWebAttackGoal<T extends Mob & IWebShooter> extends SimpleRangedAttackGoal<T> {
    private final T webShooter;

    public RangedWebAttackGoal(T attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        super(attacker, (is) -> true, IWebShooter::shootWeb, movespeed, maxAttackTime, maxAttackDistanceIn);
        this.webShooter = attacker;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.webShooter.getTarget();
        return super.canUse() && target != null && !this.webShooter.isTargetTrapped();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.webShooter.getTarget();
        return super.canContinueToUse() && target != null && !this.webShooter.isTargetTrapped();
    }

    @Override
    public void start() {
        super.start();
        this.webShooter.setWebShooting(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.webShooter.setWebShooting(false);
    }
}
