package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.goals.RangedWebAttackGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.ITrapsTarget;
import net.firefoxsalesman.dungeonsmobs.interfaces.IWebShooter;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Spider.class)
public abstract class SpiderEntityMixin extends Monster implements IWebShooter {

	private static final EntityDataAccessor<Boolean> WEBSHOOTING = SynchedEntityData.defineId(Spider.class,
			EntityDataSerializers.BOOLEAN);
	private RangedWebAttackGoal<?> rangedWebAttackGoal;
	private LeapAtTargetGoal leapAtTargetGoal;
	private MeleeAttackGoal meleeAttackGoal;

	public int targetTrappedCounter = 0;

	protected SpiderEntityMixin(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(at = @At("TAIL"), method = "registerGoals")
	private void registerGoals(CallbackInfo callbackInfo) {
		((GoalSelectorAccessor) goalSelector)
				.getAvailableGoals()
				.stream()
				.filter(pg -> pg.getPriority() == 4 && pg.getGoal() instanceof MeleeAttackGoal)
				.findFirst()
				.ifPresent(pg -> {
					meleeAttackGoal = (MeleeAttackGoal) pg.getGoal();
				});
		((GoalSelectorAccessor) goalSelector)
				.getAvailableGoals()
				.stream()
				.filter(pg -> pg.getPriority() == 3 && pg.getGoal() instanceof LeapAtTargetGoal)
				.findFirst()
				.ifPresent(pg -> {
					leapAtTargetGoal = (LeapAtTargetGoal) pg.getGoal();
				});

		rangedWebAttackGoal = new RangedWebAttackGoal<>(this, 1.0D, 60, 20.0F);
	}

	@Inject(at = @At("RETURN"), method = "defineSynchedData")
	private void registerData(CallbackInfo callbackInfo) {
		entityData.define(WEBSHOOTING, false);
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		if (DungeonsMobsConfig.COMMON.ENABLE_RANGED_SPIDERS.get() && getType() != EntityType.CAVE_SPIDER) {
			reassessAttackGoals();
		}
	}

	/*
	 * We check for leapAtTargetGoal not being null on a case-by-case basis since
	 * we want compatibility with Spiders 2.0 which changes the LeapAtTargetGoal to
	 * a custom Goal that doesn't extend it
	 */
	private void reassessAttackGoals() {
		LivingEntity target = getTarget();
		if (meleeAttackGoal != null
				&& rangedWebAttackGoal != null
				&& target != null) {

			double distanceSq = this.distanceToSqr(target);
			boolean closeEnoughForMelee = distanceSq <= 3.5D * 3.5D; // square of melee distance

			if (!isTargetTrapped() && !closeEnoughForMelee) {
				// Use ranged
				goalSelector.removeGoal(meleeAttackGoal);
				if (leapAtTargetGoal != null) {
					goalSelector.removeGoal(leapAtTargetGoal);
				}
				if (!goalSelector.getAvailableGoals().contains(rangedWebAttackGoal)) {
					goalSelector.addGoal(4, rangedWebAttackGoal);
				}
			} else {
				// Use melee
				goalSelector.removeGoal(rangedWebAttackGoal);
				if (leapAtTargetGoal != null
						&& !goalSelector.getAvailableGoals().contains(leapAtTargetGoal)) {
					goalSelector.addGoal(3, leapAtTargetGoal);
				}
				if (!goalSelector.getAvailableGoals().contains(meleeAttackGoal)) {
					goalSelector.addGoal(4, meleeAttackGoal);
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (targetTrappedCounter > 0) {
			targetTrappedCounter--;
		}
	}

	@Override
	public void setTargetTrapped(boolean trapped, boolean notifyOthers) {
		TargetingConditions spiderTargeting = TargetingConditions.forCombat().range(10.0D)
				.ignoreInvisibilityTesting();

		if (notifyOthers) {
			List<Spider> spiders = level().getNearbyEntities(Spider.class, spiderTargeting, this,
					getBoundingBox().inflate(10.0D));

			for (Spider spider : spiders) {
				if (spider instanceof ITrapsTarget && getTarget() != null
						&& spider.getTarget() != null
						&& spider.getTarget() == getTarget()) {
					((ITrapsTarget) spider).setTargetTrapped(trapped, false);
				}
			}
		}

		if (trapped) {
			targetTrappedCounter = 20;
		} else {
			targetTrappedCounter = 0;
		}
	}

	@Override
	public boolean isTargetTrapped() {
		return targetTrappedCounter > 0;
	}

	@Override
	public void setWebShooting(boolean webShooting) {
		playSound(ModSoundEvents.SPIDER_PREPARE_SHOOT.get(), getSoundVolume(), getVoicePitch());
		entityData.set(WEBSHOOTING, webShooting);
	}

	@Override
	public boolean isWebShooting() {
		return entityData.get(WEBSHOOTING);
	}

}
