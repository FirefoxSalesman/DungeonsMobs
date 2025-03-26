package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class PoisonQuillVineEntity extends AbstractVineEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int delayedBehaviourTime;

	public int shootAnimationTick;
	public int shootAnimationLength = 18;
	public int shootAnimationActionPoint = 8;

	public boolean open;

	public PoisonQuillVineEntity(EntityType<? extends PoisonQuillVineEntity> p_i50147_1_, Level p_i50147_2_) {
		super(p_i50147_1_, p_i50147_2_);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new PoisonQuillVineEntity.OpenGoal());
		goalSelector.addGoal(0, new PoisonQuillVineEntity.CloseGoal());
		goalSelector.addGoal(1, new PoisonQuillVineEntity.ShootAttackGoal(this));
		goalSelector.addGoal(6, new LookAtTargetGoal(this));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
		return isOut() ? getBbHeight() - 0.75F : super.getStandingEyeHeight(p_213348_1_, p_213348_2_);
	}

	protected BodyRotationControl createBodyControl() {
		return new PoisonQuillVineEntity.BodyHelperController(this);
	}

	class BodyHelperController extends BodyRotationControl {
		public BodyHelperController(Mob p_i50612_2_) {
			super(p_i50612_2_);
		}

		public void clientTick() {
		}
	}

	public int getMaxHeadXRot() {
		return 180;
	}

	public int getMaxHeadYRot() {
		return 180;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 25.0D)
				.add(Attributes.FOLLOW_RANGE, 25.0D);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller",
				getAnimationTransitionTime(), this::predicate));
	}

	@Override
	public int getAnimationTransitionTime() {
		return 5;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (deathTime > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("poison_quill_vine_retract", LoopType.LOOP));
		} else if (burstAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("poison_quill_vine_burst", LoopType.LOOP));
		} else if (retractAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("poison_quill_vine_retract", LoopType.LOOP));
		} else if (shootAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("poison_quill_vine_shoot", LoopType.LOOP));
		} else {
			if (isOut() || burstAnimationTick > 0) {
				if (open) {
					event.getController().setAnimation(RawAnimation.begin()
							.then("poison_quill_vine_idle_open", LoopType.LOOP));
				} else {
					event.getController().setAnimation(RawAnimation.begin()
							.then("poison_quill_vine_idle", LoopType.LOOP));
				}
			} else {
				event.getController().setAnimation(RawAnimation.begin()
						.then("poison_quill_vine_idle_underground", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public int getBurstAnimationLength() {
		return 20;
	}

	@Override
	public int getRetractAnimationLength() {
		return 15;
	}

	@Override
	protected SoundEvent getAmbientSoundFoley() {
		return null;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.POISON_QUILL_VINE_IDLE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.POISON_QUILL_VINE_HURT_VOCAL.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.POISON_QUILL_VINE_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSoundFoley(DamageSource p_184601_1_) {
		return ModSoundEvents.POISON_QUILL_VINE_HURT_FOLEY.get();
	}

	@Override
	public SoundEvent getBurstSound() {
		return ModSoundEvents.POISON_QUILL_VINE_BURST.get();
	}

	@Override
	public SoundEvent getRetractSound() {
		return ModSoundEvents.POISON_QUILL_VINE_BURST.get();
	}

	public SoundEvent getShootSound() {
		return ModSoundEvents.POISON_QUILL_VINE_SHOOT.get();
	}

	public SoundEvent getOpenSound() {
		return ModSoundEvents.POISON_QUILL_VINE_OPEN.get();
	}

	public SoundEvent getCloseSound() {
		return ModSoundEvents.POISON_QUILL_VINE_CLOSE.get();
	}

	@Override
	public SoundEvent getBurstSoundFoley() {
		return null;
	}

	@Override
	public SoundEvent getRetractSoundFoley() {
		return null;
	}

	@Override
	public boolean isKelp() {
		return false;
	}

	@Override
	public boolean shouldDieInWrongHabitat() {
		return true;
	}

	@Override
	public int wrongHabitatDieChance() {
		return 100;
	}

	public int getLengthInSegments() {
		return Mth.clamp(entityData.get(LENGTH), 2, 26);
	}

	@Override
	public void spawnAreaDamage() {
		AreaDamageEntity areaDamage = AreaDamageEntity.spawnAreaDamage(level(), position(), this,
				5.0F,
				damageSources().mobAttack(this), 0.0F, 1.5F, 0.25F, 0.25F, 10, false, false, 0.75, 0.25,
				false, 0, 1);
		level().addFreshEntity(areaDamage);
	}

	@Override
	public void setDefaultFeatures() {
		setLengthInSegments(2 + random.nextInt(3));
		setVanishes(false);
		setAlwaysOut(false);
		setDetectionDistance(7.5F);
	}

	@Override
	public float getExtraHitboxY() {
		return 1.625F;
	}

	@Override
	public void burst() {
		super.burst();
		delayedBehaviourTime = 10;
	}

	@Override
	public void retract() {
		super.retract();
		delayedBehaviourTime = 20;
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 7) {
			open = true;
		} else if (p_70103_1_ == 8) {
			open = false;
		} else if (p_70103_1_ == 9) {
			shootAnimationTick = shootAnimationLength;
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (delayedBehaviourTime > 0) {
			delayedBehaviourTime--;
		}
	}

	@Override
	public void tickDownAnimTimers() {
		super.tickDownAnimTimers();

		if (shootAnimationTick > 0) {
			shootAnimationTick--;
		}
	}

	@Override
	public float distanceTo(Entity p_70032_1_) {
		float f = (float) (getX() - p_70032_1_.getX());
		float f1 = (float) (getEyeY() - p_70032_1_.getY());
		float f2 = (float) (getZ() - p_70032_1_.getZ());
		return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
	}

	class OpenGoal extends Goal {

		public OpenGoal() {

		}

		@Override
		public boolean canUse() {
			return isOut()
					&& delayedBehaviourTime <= 0
					&& getTarget() != null
					&& !open && distanceTo(getTarget()) <= 15;
		}

		@Override
		public void start() {
			super.start();
			open = true;
			level().broadcastEntityEvent(PoisonQuillVineEntity.this, (byte) 7);
			delayedBehaviourTime = 10;
			playSound(getOpenSound(), 1.0F, 1.0F);
		}
	}

	class CloseGoal extends Goal {

		public CloseGoal() {

		}

		@Override
		public boolean canUse() {
			return isOut()
					&& delayedBehaviourTime <= 0
					&& open
					&& (getTarget() == null || distanceTo(getTarget()) > 17.5);
		}

		@Override
		public void start() {
			super.start();
			open = false;
			level().broadcastEntityEvent(PoisonQuillVineEntity.this, (byte) 8);
			delayedBehaviourTime = 40;
			playSound(getCloseSound(), 1.0F, 1.0F);
		}
	}

	class ShootAttackGoal extends Goal {
		public PoisonQuillVineEntity mob;
		@Nullable
		public LivingEntity target;

		public ShootAttackGoal(PoisonQuillVineEntity mob) {
			this.mob = mob;
			target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();
			return mob.open && mob.delayedBehaviourTime <= 0 && target != null
					&& mob.distanceTo(target) <= 12.5 && mob.hasLineOfSight(target)
					&& animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return mob.open && mob.delayedBehaviourTime <= 0 && target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.shootAnimationTick = mob.shootAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 9);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (target != null && mob.shootAnimationTick == mob.shootAnimationActionPoint) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0, mob.getStandingEyeHeight(mob.getPose(),
						mob.getDimensions(mob.getPose())), 1.0, yHeadRot);
				double d1 = target.getX() - pos.x;
				double d2 = target.getY(0.6D) - pos.y;
				double d3 = target.getZ() - pos.z;
				PoisonQuillEntity poisonQuill = new PoisonQuillEntity(mob.level(), mob, d1, d2, d3);
				poisonQuill.setKelp(mob.isKelp());
				poisonQuill.rotateToMatchMovement();
				poisonQuill.moveTo(pos.x, pos.y, pos.z);
				mob.level().addFreshEntity(poisonQuill);
				mob.playSound(mob.getShootSound(), 1.25F, 1.0F);
			}
		}

		public boolean animationsUseable() {
			return mob.shootAnimationTick <= 0;
		}

	}
}
