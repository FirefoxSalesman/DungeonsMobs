package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IWebShooter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class RangedWebAttackGoal<T extends Mob & IWebShooter> extends SimpleRangedAttackGoal<T> {
    private final T webShooter;

    public RangedWebAttackGoal(T attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        super(attacker, (is) -> true, IWebShooter::shootWeb, movespeed, maxAttackTime, maxAttackDistanceIn);
        webShooter = attacker;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = webShooter.getTarget();
        return super.canUse() && target != null && !webShooter.isTargetTrapped();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = webShooter.getTarget();
        return super.canContinueToUse() && target != null && !webShooter.isTargetTrapped();
    }

    @Override
    public void start() {
        super.start();
        webShooter.setWebShooting(true);
    }

    @Override
    public void stop() {
        super.stop();
        webShooter.setWebShooting(false);
    }
}
