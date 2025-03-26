package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
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
import java.util.EnumSet;
import java.util.UUID;

public class LeapleafEntity extends Monster implements GeoEntity {

	private static final UUID SPEED_MODIFIER_CHARGING_UUID = UUID
			.fromString("b380d5fd-85cb-4ac3-9450-d9092a09e0c9");
	private static final AttributeModifier SPEED_MODIFIER_CHARGING = new AttributeModifier(
			SPEED_MODIFIER_CHARGING_UUID,
			"Charging speed increase", 0.1D, AttributeModifier.Operation.ADDITION);

	private static final EntityDataAccessor<Integer> TIMES_LEAPT = SynchedEntityData.defineId(LeapleafEntity.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Boolean> CAN_LEAP = SynchedEntityData.defineId(LeapleafEntity.class,
			EntityDataSerializers.BOOLEAN);

	private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(LeapleafEntity.class,
			EntityDataSerializers.BOOLEAN);

	public int strafeTick;
	public int strafeLength = 40;

	public int restTick;
	public int restLength = 100;

	public int attackAnimationTick;
	public int attackAnimationLength = 30;
	public int attackAnimationActionPoint = 15;

	public int prepareLeapAnimationTick;
	public int prepareLeapAnimationLength = 30;

	public int leapAnimationTick;
	public int leapAnimationLength = 9;

	public int smashAnimationTick;
	public int smashAnimationLength = 25;
	public int smashAnimationActionPoint = 18;

	public int leapCooldown;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public LeapleafEntity(Level world) {
		super(ModEntities.LEAPLEAF.get(), world);
	}

	public LeapleafEntity(EntityType<? extends LeapleafEntity> type, Level world) {
		super(type, world);
		xpReward = 20;
		setMaxUpStep(1.0F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new LeapleafEntity.RemainStationaryGoal());
		goalSelector.addGoal(1, new LeapleafEntity.StrafeGoal(this));
		goalSelector.addGoal(2, new LeapleafEntity.LeapGoal(this));
		goalSelector.addGoal(3, new LeapleafEntity.PrepareLeapGoal(this));
		goalSelector.addGoal(4, new LeapleafEntity.BasicAttackGoal(this));
		goalSelector.addGoal(5, new ApproachTargetGoal(this, 0, 1.2D, true));
		goalSelector.addGoal(6, new LookAtTargetGoal(this));
		goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.275D)
				.add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, 75.0D)
				.add(Attributes.ARMOR, 15D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	public boolean shouldBeStationary() {
		return restTick > 0;
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundeventVocal = getAmbientSound();
		SoundEvent soundeventFoley = getAmbientSoundFoley();
		playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(),
				getSoundVolume(), getVoicePitch());
	}

	@Override
	protected void playHurtSound(DamageSource pDamageSource) {
		ambientSoundTime = -getAmbientSoundInterval();
		SoundEvent soundeventVocal = getHurtSound(pDamageSource);
		SoundEvent soundeventFoley = getHurtSoundFoley(pDamageSource);
		playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(),
				getSoundVolume(), getVoicePitch());
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		playSound(getStepSound(), 0.5F, 1.0F);
		playSound(getStepSoundFoley(), 0.5F, 1.0F);
	}

	public void playSound(SoundEvent vocalSound, SoundEvent foleySound, float vocalVolume, float vocalPitch,
			float foleyVolume, float foleyPitch) {
		if (!isSilent()) {
			if (vocalSound != null) {
				level().playSound(null, getX(), getY(), getZ(), vocalSound,
						getSoundSource(), vocalVolume, vocalPitch);
			}
			if (foleySound != null) {
				level().playSound(null, getX(), getY(), getZ(), foleySound,
						getSoundSource(), foleyVolume, foleyPitch);
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.LEAPLEAF_IDLE_VOCAL.get();
	}

	protected SoundEvent getAmbientSoundFoley() {
		return ModSoundEvents.LEAPLEAF_IDLE_FOLEY.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return 250;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.LEAPLEAF_HURT_VOCAL.get();
	}

	protected SoundEvent getHurtSoundFoley(DamageSource p_184601_1_) {
		return ModSoundEvents.LEAPLEAF_HURT_FOLEY.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.LEAPLEAF_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.LEAPLEAF_STEP_VOCAL.get();
	}

	protected SoundEvent getStepSoundFoley() {
		return ModSoundEvents.LEAPLEAF_STEP_FOLEY.get();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(TIMES_LEAPT, 0);
		entityData.define(CAN_LEAP, false);
		entityData.define(LEAPING, false);
	}

	public int getTimesLeapt() {
		return entityData.get(TIMES_LEAPT);
	}

	public void setTimesLeapt(int attached) {
		entityData.set(TIMES_LEAPT, attached);
	}

	public boolean canLeap() {
		return entityData.get(CAN_LEAP);
	}

	public void setCanLeap(boolean attached) {
		entityData.set(CAN_LEAP, attached);
	}

	public boolean isLeaping() {
		return entityData.get(LEAPING);
	}

	public void setLeaping(boolean attached) {
		entityData.set(LEAPING, attached);
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			attackAnimationTick = attackAnimationLength;
		} else if (p_28844_ == 8) {
			prepareLeapAnimationTick = prepareLeapAnimationLength;
		} else if (p_28844_ == 9) {
			leapAnimationTick = leapAnimationLength;
		} else if (p_28844_ == 11) {
			smashAnimationTick = smashAnimationLength;
		} else if (p_28844_ == 7) {
			restTick = restLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	public void baseTick() {
		super.baseTick();
		tickDownAnimTimers();

		AttributeInstance modifiableattributeinstance = getAttribute(Attributes.MOVEMENT_SPEED);

		if (!level().isClientSide && canLeap()
				&& (getTarget() == null || getTarget().isDeadOrDying())) {
			restTick = restLength;
			level().broadcastEntityEvent(this, (byte) 7);
			setCanLeap(false);
			setTimesLeapt(0);
		}

		if (canLeap()) {
			if (!modifiableattributeinstance.hasModifier(SPEED_MODIFIER_CHARGING)) {
				modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_CHARGING);
			}
		} else {
			modifiableattributeinstance.removeModifier(SPEED_MODIFIER_CHARGING);
		}

		if (getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F
				&& random.nextInt(3) == 0) {
			int i = Mth.floor(getX());
			int j = Mth.floor(getY() - (double) 0.2F);
			int k = Mth.floor(getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = level().getBlockState(pos);
			if (!blockstate.isAir()) {
				level().addParticle(ModParticleTypes.DUST.get(),
						getX() + ((double) random.nextFloat() - 0.5D)
								* (double) getBbWidth(),
						getY() + 0.1D,
						getZ() + ((double) random.nextFloat() - 0.5D)
								* (double) getBbWidth(),
						random.nextFloat() * 0.5, random.nextGaussian() * 1,
						random.nextGaussian() * 1);
			}
		}

		if (restTick > 0) {
			restTick--;
		}

		if (strafeTick > 0) {
			strafeTick--;
		}

		if (leapCooldown > 0) {
			leapCooldown--;
		}
	}

	public void tickDownAnimTimers() {
		if (attackAnimationTick > 0) {
			attackAnimationTick--;
		}

		if (prepareLeapAnimationTick > 0) {
			prepareLeapAnimationTick--;
		}

		if (leapAnimationTick > 0) {
			leapAnimationTick--;
		}

		if (smashAnimationTick > 0) {
			smashAnimationTick--;
		}
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_, DamageSource p_147189_) {
		return false;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (smashAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_smash", LoopType.LOOP));
		} else if (leapAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_leap", LoopType.LOOP));
		} else if (prepareLeapAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("leapleaf_prepare_leap", LoopType.LOOP));
		} else if (attackAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_attack", LoopType.LOOP));
		} else if (restTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_rest", LoopType.LOOP));
		} else if (isLeaping()) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("leapleaf_leaping", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_walk", LoopType.LOOP));
		} else {
			event.getController().setAnimation(RawAnimation.begin().then("leapleaf_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public boolean isAlliedTo(Entity pEntity) {
		if (super.isAlliedTo(pEntity)) {
			return true;
		} else if (pEntity instanceof LivingEntity && pEntity.getType().is(EntityTags.PLANT_MOBS)) {
			return getTeam() == null && pEntity.getTeam() == null;
		} else {
			return false;
		}
	}

	class BasicAttackGoal extends Goal {

		public LeapleafEntity mob;
		@Nullable
		public LivingEntity target;

		public BasicAttackGoal(LeapleafEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.mob = mob;
			target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return mob.shouldBeStationary();
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();

			return target != null && mob.distanceTo(target) <= 3.25 && animationsUseable()
					&& mob.hasLineOfSight(target);
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.LEAPLEAF_ATTACK_VOCAL.get(),
					ModSoundEvents.LEAPLEAF_ATTACK_FOLEY.get(), 1.25F, 1.0F, 1.25F, 1.0F);
			mob.attackAnimationTick = mob.attackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (mob.attackAnimationTick == mob.attackAnimationActionPoint) {
				Vec3 areaDamagePos = PositionUtils.getOffsetPos(mob, -1, 0, 1.5, mob.yBodyRot);
				AreaDamageEntity areaDamage = AreaDamageEntity.spawnAreaDamage(mob.level(),
						areaDamagePos,
						mob, 15F, damageSources().mobAttack(mob), 0.0F, 4.5F, 1.0F, 0.5F, 0,
						false,
						false, 1.0D, 0.2D, false, 0, 1);
				mob.level().addFreshEntity(areaDamage);
			}
		}

		public boolean animationsUseable() {
			return mob.attackAnimationTick <= 0;
		}

	}

	class PrepareLeapGoal extends Goal {

		public LeapleafEntity mob;
		@Nullable
		public LivingEntity target;

		public PrepareLeapGoal(LeapleafEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.mob = mob;
			target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return mob.shouldBeStationary();
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();

			return target != null && mob.distanceTo(target) <= 15 && !mob.canLeap()
					&& mob.random.nextInt(30) == 0 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.leapCooldown <= 0;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.LEAPLEAF_PREPARE_LEAP_VOCAL.get(),
					ModSoundEvents.LEAPLEAF_PREPARE_LEAP_FOLEY.get(), 1.25F, 1.0F, 1.25F, 1.0F);
			mob.prepareLeapAnimationTick = mob.prepareLeapAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 8);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (mob.prepareLeapAnimationTick == 1) {
				mob.setCanLeap(true);
			}
		}

		public boolean animationsUseable() {
			return mob.prepareLeapAnimationTick <= 0;
		}

	}

	class LeapGoal extends Goal {

		public LeapleafEntity mob;
		@Nullable
		public LivingEntity target;

		public int leapTime;

		public LeapGoal(LeapleafEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.mob = mob;
			target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return mob.shouldBeStationary();
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();

			return target != null && mob.distanceTo(target) <= 10 && mob.canLeap()
					&& mob.random.nextInt(5) == 0 && animationsUseable()
					&& mob.hasLineOfSight(target);
		}

		@Override
		public boolean canContinueToUse() {
			return target != null
					&& (!animationsUseable() || mob.isLeaping() || mob.smashAnimationTick > 0);
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.LEAPLEAF_LEAP_VOCAL.get(),
					ModSoundEvents.LEAPLEAF_LEAP_FOLEY.get(), 1.5F, 1.0F, 1.5F, 1.0F);
			mob.leapAnimationTick = mob.leapAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 9);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null && mob.leapAnimationTick == 1) {
				double d0 = target.getX() - mob.getX();
				double d1 = target.getY() - mob.getY();
				double d2 = target.getZ() - mob.getZ();
				mob.setDeltaMovement(d0 * 0.15D, 0.75 + Mth.clamp(d1 * 0.05D, 0, 10), d2 * 0.15D);
				mob.setLeaping(true);
				mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
				leapTime = 0;
			}

			if (mob.isLeaping()) {
				leapTime++;
			}

			if (mob.isLeaping() && mob.onGround() && leapTime > 10) {
				mob.playSound(ModSoundEvents.LEAPLEAF_LAND.get(), 2.0F, 1.0F);
				mob.setLeaping(false);
				mob.smashAnimationTick = mob.smashAnimationLength;
				mob.level().broadcastEntityEvent(mob, (byte) 11);
			}

			if (mob.smashAnimationTick == mob.smashAnimationActionPoint) {
				Vec3 areaDamagePos = PositionUtils.getOffsetPos(mob, -1.5, 0, 2, mob.yBodyRot);
				AreaDamageEntity areaDamage = AreaDamageEntity.spawnAreaDamage(mob.level(),
						areaDamagePos, mob, 25F, damageSources().mobAttack(mob), 0.0F, 6.0F,
						1.0F, 0.75F, 10, false, false, 2.0D, 0.4D, true, 120, 1);

				Vec3 areaDamagePos2 = PositionUtils.getOffsetPos(mob, 1.5, 0, 2, mob.yBodyRot);
				AreaDamageEntity areaDamage2 = AreaDamageEntity.spawnAreaDamage(mob.level(),
						areaDamagePos2, mob, 25F, damageSources().mobAttack(mob), 0.0F, 6.0F,
						1.0F,
						0.75F, 10, false, false, 2.0D, 0.4D, true, 120, 1);

				areaDamage.connectedAreaDamages.add(areaDamage2);
				areaDamage2.connectedAreaDamages.add(areaDamage);

				mob.level().addFreshEntity(areaDamage);
				mob.level().addFreshEntity(areaDamage2);
			}
		}

		@Override
		public void stop() {
			super.stop();
			leapTime = 0;
			mob.setCanLeap(false);
			mob.setLeaping(false);
			mob.setTimesLeapt(mob.getTimesLeapt() + 1);
			mob.leapCooldown = 60;

			int leapTimesByDifficulty = mob.level().getCurrentDifficultyAt(mob.blockPosition())
					.getDifficulty().getId();

			if (mob.getTimesLeapt() >= leapTimesByDifficulty) {
				mob.restTick = mob.restLength;
				mob.level().broadcastEntityEvent(mob, (byte) 7);
				mob.setTimesLeapt(0);
				mob.playSound(ModSoundEvents.LEAPLEAF_REST_VOCAL.get(),
						ModSoundEvents.LEAPLEAF_REST_FOLEY.get(), 1.0F, mob.getVoicePitch(),
						1.0F, mob.getVoicePitch());
			} else {
				mob.strafeTick = mob.strafeLength;
			}
		}

		public boolean animationsUseable() {
			return mob.leapAnimationTick <= 0;
		}

	}

	class StrafeGoal extends Goal {

		public LeapleafEntity mob;
		@Nullable
		public LivingEntity target;

		private boolean strafingLeft;

		public StrafeGoal(LeapleafEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.mob = mob;
			target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return mob.shouldBeStationary();
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();

			return target != null && mob.strafeTick > 0;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && mob.strafeTick > 0;
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());

				if (mob.random.nextInt(30) == 0) {
					strafingLeft = !strafingLeft;
				}

				if (mob.distanceTo(target) < 4) {
					mob.setDeltaMovement(mob.getDeltaMovement().add(
							PositionUtils.getOffsetMotion(mob, 0, 0, -0.1, mob.yBodyRot)));
				}

				if (strafingLeft) {
					mob.setDeltaMovement(mob.getDeltaMovement().add(
							PositionUtils.getOffsetMotion(mob, 0.05, 0, 0, mob.yBodyRot)));
				} else {
					mob.setDeltaMovement(mob.getDeltaMovement().add(
							PositionUtils.getOffsetMotion(mob, -0.05, 0, 0, mob.yBodyRot)));
				}
			}
		}

	}

	class RemainStationaryGoal extends Goal {

		public RemainStationaryGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.TARGET, Goal.Flag.JUMP));
		}

		@Override
		public boolean canUse() {
			return shouldBeStationary();
		}
	}

}
