package net.firefoxsalesman.dungeonsmobs.lib.utils;

import net.firefoxsalesman.dungeonsmobs.mixin.BrainAccessor;
import net.firefoxsalesman.dungeonsmobs.mixin.NearestAttackableTargetGoalAccessor;
import net.firefoxsalesman.dungeonsmobs.mixin.TargetingConditionsAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import static net.firefoxsalesman.dungeonsmobs.lib.utils.PetHelper.isPetOrColleagueRelation;
import static net.minecraft.world.entity.EntityType.ARMOR_STAND;

public class AbilityHelper {

    public static boolean isFacingEntity(Entity looker, Entity target, Vec3 look, int angle) {
        if (angle <= 0) return false;
        Vec3 posVec = target.position().add(0, target.getEyeHeight(), 0);
        Vec3 relativePosVec = posVec.vectorTo(looker.position().add(0, looker.getEyeHeight(), 0)).normalize();
        //relativePosVec = new Vector3d(relativePosVec.x, 0.0D, relativePosVec.z);

        double dotsq = ((relativePosVec.dot(look) * Math.abs(relativePosVec.dot(look))) / (relativePosVec.lengthSqr() * look.lengthSqr()));
        double cos = Mth.cos((float) ((angle / 360d) * Math.PI));
        return dotsq < -(cos * cos);
    }

    private static boolean isNotTheTargetOrAttacker(LivingEntity attacker, LivingEntity target, LivingEntity nearbyEntity) {
        return nearbyEntity != target
                && nearbyEntity != attacker;
    }

    private static boolean isBothPlayerAndNoPvP(LivingEntity attacker, LivingEntity nearbyEntity) {
        // return attacker instanceof Player && nearbyEntity instanceof Player && !DungeonsLibrariesConfig.ENABLE_AREA_OF_EFFECT_ON_OTHER_PLAYERS.get();
        return attacker instanceof Player && nearbyEntity instanceof Player;
    }

    public static boolean canHealEntity(LivingEntity healer, LivingEntity nearbyEntity) {
        return nearbyEntity != healer
                && isAlly(healer, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, healer);
    }

    public static boolean isAlly(LivingEntity origin, LivingEntity target) {
        LivingEntity originOwner = PetHelper.getOwner(origin);
        LivingEntity targetOwner = PetHelper.getOwner(target);
        if (originOwner != null || targetOwner != null) {
            return isAlly(originOwner != null ? originOwner : origin, targetOwner != null ? targetOwner : target);
        }
        return isPetOrColleagueRelation(origin, target)
                || origin.isAlliedTo(target)
                || isBothPlayerAndNoPvP(origin, target)
                || isEntityBlacklisted(origin, target)
                || (isDefaultEnemy(origin) && isDefaultEnemy(target) && !matchesAITargets(origin, target));
    }

    public static boolean isDefaultEnemy(LivingEntity origin) {
        return origin.getClassification(false).equals(MobCategory.MONSTER);
    }

    private static boolean matchesAITargets(LivingEntity origin, LivingEntity target) {
        if (!(origin instanceof Mob originMob)) return true;
        return checkTargetGoals(originMob, target);
    }

    @NotNull
    private static Boolean checkTargetGoals(Mob originMob, LivingEntity target) {
        return originMob.targetSelector.getAvailableGoals().stream()
                .map(WrappedGoal::getGoal)
                .filter(goal -> goal instanceof NearestAttackableTargetGoal<?>)
                .map(goal -> matchesPredicate(target, ((NearestAttackableTargetGoalAccessor<?>) goal).getTargetType(), ((NearestAttackableTargetGoalAccessor<?>) goal).getTargetConditions()))
                .reduce(false, (o, o2) -> o || o2);
    }

    private static boolean matchesPredicate(LivingEntity target, Class<?> targetType, TargetingConditions targetConditions) {
        Predicate<LivingEntity> selector = ((TargetingConditionsAccessor) targetConditions).getSelector();
        return (targetType == null || targetType.isInstance(target)) && (selector == null || selector.test(target));
    }

    public static <E extends LivingEntity> BrainAccessor<E> castToAccessor(Brain<E> brain) {
        //noinspection unchecked
        return (BrainAccessor<E>) brain;
    }

    private static boolean isEntityBlacklisted(LivingEntity origin, LivingEntity target) {
        if (target.getType().equals(ARMOR_STAND)) return true;
        return origin instanceof Player
               && !isDefaultEnemy(target);
                // && (!DungeonsLibrariesConfig.ENEMY_WHITELIST.get().contains(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString())
                // || (DungeonsLibrariesConfig.ENEMY_BLACKLIST.get().contains(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString())));
    }

    private static boolean isAliveAndCanBeSeen(LivingEntity nearbyEntity, LivingEntity attacker) {
        return nearbyEntity.isAlive() && attacker.hasLineOfSight(nearbyEntity);
    }

    public static boolean canApplyToSecondEnemy(LivingEntity attacker, LivingEntity target, LivingEntity nearbyEntity) {
        return isNotTheTargetOrAttacker(attacker, target, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && !isAlly(attacker, nearbyEntity);
    }

    public static boolean canApplyToEnemy(LivingEntity attacker, LivingEntity nearbyEntity) {
        return nearbyEntity != attacker
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && !isAlly(attacker, nearbyEntity);
    }

}
