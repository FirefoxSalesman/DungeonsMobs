package net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper.getLeader;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.GoalUtils.shouldAttackEntity;

public class LeaderHurtTargetGoal extends TargetGoal {
    TargetingConditions PREDICATE = TargetingConditions.forNonCombat();
    private final Mob mobEntity;
    private LivingEntity attacker;
    private int timestamp;

    public LeaderHurtTargetGoal(Mob mobEntity) {
        super(mobEntity, false);
        this.mobEntity = mobEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity owner = getLeader(this.mobEntity);
        if (owner == null) {
            return false;
        } else {
            this.attacker = owner.getLastHurtMob();
            int lastAttackedEntityTime = owner.getLastHurtMobTimestamp();
            return lastAttackedEntityTime != this.timestamp && this.canAttack(this.attacker, PREDICATE) && shouldAttackEntity(this.attacker, owner);
        }
//        } else {
//            return false;
//        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = getLeader(this.mobEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
