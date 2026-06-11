package net.firefoxsalesman.dungeonsmobs.entity.ender;

import java.util.Optional;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.object.PlayState;

public class WatchlingEntity extends AbstractEnderlingEntity {
	private Mob owner;

	public WatchlingEntity(EntityType<? extends WatchlingEntity> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(2, new AttackGoal(1.5D));
		goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new AbstractEnderlingEntity.FindPlayerGoal(this, null));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(1,
				new EnderlingTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));
	}

	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.225F).add(Attributes.ATTACK_DAMAGE, 7.0D)
				.add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WATCHLING_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.WATCHLING_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WATCHLING_DEATH.get();
	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		playSound(ModSoundEvents.WATCHLING_ATTACK.get(), 1.0F, 1.0F);
		return super.doHurtTarget(p_70652_1_);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(getStepSound(), 0.75F, 1.0F);
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.WATCHLING_STEP.get();
	}

	public void setTarget(LivingEntity p_70624_1_) {
		if (p_70624_1_ != null && p_70624_1_ != getTarget()) {
			teleport(p_70624_1_.getX() - 3 + random.nextInt(6), p_70624_1_.getY(),
					p_70624_1_.getZ() - 3 + random.nextInt(6));
		}
		super.setTarget(p_70624_1_);
	}

	protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (isAttacking() > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("watchling_attack", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			if (isRunning() > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("watchling_run", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("watchling_walk", LoopType.LOOP));
			}
		} else {
			event.getController().setAnimation(RawAnimation.begin().then("watchling_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	class AttackGoal extends MeleeAttackGoal {

		public final TargetingConditions slimePredicate = TargetingConditions.forCombat().range(20.0D)
				.ignoreInvisibilityTesting();

		public AttackGoal(double speed) {
			super(WatchlingEntity.this, speed, true);
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse();
		}

		protected double getAttackReachSqr(LivingEntity p_179512_1_) {
			return mob.getBbWidth() * 3.0F * mob.getBbWidth() * 3.0F + p_179512_1_.getBbWidth();
		}

		public void tick() {
			super.tick();

			setRunning(10);
		}

		protected void checkAndPerformAttack(LivingEntity entity, double pDistToEnemySqr) {
			double d0 = getAttackReachSqr(entity);
			if (pDistToEnemySqr <= d0 && isAttacking() == 4) {
				resetAttackCooldown();
				mob.doHurtTarget(entity);
			} else if (pDistToEnemySqr <= d0 * 1.5D) {
				if (isTimeToAttack()) {
					resetAttackCooldown();
				}

				if (isAttacking() <= 0) {
					setAttacking(30);
				}
			} else {
				resetAttackCooldown();
			}
		}
	}

	public Optional<Mob> getOwner() {
		return Optional.ofNullable(owner);
	}

	public void setOwner(Mob mob) {
		owner = mob;
	}
}
