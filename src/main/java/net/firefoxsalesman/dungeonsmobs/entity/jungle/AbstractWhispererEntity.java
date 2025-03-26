package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AbstractTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.firefoxsalesman.dungeonsmobs.utils.GeomancyHelper;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class AbstractWhispererEntity extends Monster implements GeoEntity {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(
			AbstractWhispererEntity.class,
			EntityDataSerializers.BYTE);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int summonQGVAnimationTick;
	public int summonQGVAnimationLength = 40;
	public int summonQGVAnimationActionPoint = 20;

	public int summonPQVAnimationTick;
	public int summonPQVAnimationLength = 70;
	public int summonPQVAnimationActionPoint1 = 53;
	public int summonPQVAnimationActionPoint2 = 30;

	public int grappleAnimationTick;
	public int grappleAnimationLength = 65;
	public int grappleAnimationActionPoint = 23;

	public int underwaterGrappleAnimationActionPoint = 20;

	public int attackAnimationTick;
	public int attackAnimationLength = 30;
	public int attackAnimationActionPoint = 18;

	public int underwaterAttackAnimationActionPoint = 16;

	protected final GroundPathNavigation climberNavigation;

	public AbstractWhispererEntity(EntityType<? extends AbstractWhispererEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.0F);
		climberNavigation = new WallClimberNavigation(this, world);
	}

	public boolean isSpellcasting() {
		return summonPQVAnimationTick > 0 || summonQGVAnimationTick > 0
				|| grappleAnimationTick > 0;
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	@Override
	public void tick() {
		super.tick();
		if (isClimbing()) {
			yBodyRot = yHeadRot;
		}
	}

	protected abstract AbstractTrapEntity getTrap();

	protected abstract int getBasicAttackActionPoint();

	protected abstract int getGrappleActionPoint();

	protected abstract SoundEvent getGrappleSound();

	protected abstract SoundEvent getSummonQGSound();

	protected abstract SoundEvent getSummonPoisonFoleySound();

	protected abstract SoundEvent getSummonPoisonVocalSound();

	protected abstract SoundEvent getAttackSound();

	protected abstract EntityType<? extends AbstractVineEntity> getOffensiveVine();

	protected abstract EntityType<? extends AbstractVineEntity> getAreaDenialVine();

	public boolean isClimbing() {
		return (entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean p_70839_1_) {
		byte b0 = entityData.get(DATA_FLAGS_ID);
		if (p_70839_1_) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		entityData.set(DATA_FLAGS_ID, b0);
	}

	public boolean onClimbable() {
		return isClimbing();
	}

	public abstract boolean isWavewhisperer();

	public abstract boolean isInWrongHabitat();

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.FOLLOW_RANGE, 17.5D).add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.ARMOR, 5.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
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

	abstract protected SoundEvent getAmbientSound();

	abstract protected SoundEvent getAmbientSoundFoley();

	@Override
	public int getAmbientSoundInterval() {
		return 150;
	}

	abstract protected SoundEvent getHurtSound(DamageSource pDamageSource);

	abstract protected SoundEvent getHurtSoundFoley(DamageSource pDamageSource);

	@Override
	abstract protected SoundEvent getDeathSound();

	protected SoundEvent getStepSound() {
		return ModSoundEvents.WHISPERER_STEP_VOCAL.get();
	}

	protected SoundEvent getStepSoundFoley() {
		return ModSoundEvents.WHISPERER_STEP_FOLEY.get();
	}

	@Override
	protected void playSwimSound(float p_203006_1_) {
		playSound(getSwimSound(), 1.0F,
				1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
	}

	@Override
	public void playSound(SoundEvent soundEvent, float p_184185_2_, float p_184185_3_) {
		if (soundEvent == getDeathSound()) {
			super.playSound(soundEvent, p_184185_2_, p_184185_3_ / 2);
			super.playSound(ModSoundEvents.WHISPERER_STEP_FOLEY.get(), p_184185_2_, p_184185_3_);
			super.playSound(ModSoundEvents.WHISPERER_HURT_FOLEY.get(), p_184185_2_, p_184185_3_);
		} else {
			super.playSound(soundEvent, p_184185_2_, p_184185_3_);
		}
	}

	@Override
	abstract protected SoundEvent getSwimSound();

	public void baseTick() {
		super.baseTick();
		tickDownAnimTimers();

		if (!level().isClientSide) {
			if (isInWrongHabitat() && random.nextInt(200) == 0) {
				kill();
			}
		}
	}

	public void tickDownAnimTimers() {
		if (summonQGVAnimationTick > 0) {
			summonQGVAnimationTick--;
		}

		if (summonPQVAnimationTick > 0) {
			summonPQVAnimationTick--;
		}

		if (grappleAnimationTick > 0) {
			grappleAnimationTick--;
		}

		if (attackAnimationTick > 0) {
			attackAnimationTick--;
		}
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 4) {
			summonQGVAnimationTick = summonQGVAnimationLength;
		} else if (p_70103_1_ == 5) {
			summonPQVAnimationTick = summonPQVAnimationLength;
		} else if (p_70103_1_ == 6) {
			grappleAnimationTick = grappleAnimationLength;
		} else if (p_70103_1_ == 7) {
			attackAnimationTick = attackAnimationLength;
		} else {
			super.handleEntityEvent(p_70103_1_);
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

	protected abstract <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event);

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public boolean isAlliedTo(Entity entity) {
		if (super.isAlliedTo(entity)) {
			return true;
		} else if (entity instanceof LivingEntity && entity.getType().is(EntityTags.PLANT_MOBS)) {
			return getTeam() == null && entity.getTeam() == null;
		} else {
			return false;
		}
	}

	public boolean canUseGoals() {
		return true;
	}

	class BasicAttackGoal extends Goal {

		public AbstractWhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public BasicAttackGoal(AbstractWhispererEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
			return target != null && mob.distanceTo(target) <= 3 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.random.nextInt(20) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.attackAnimationTick = mob.attackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 7);
			mob.playSound(ModSoundEvents.WHISPERER_ATTACK_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			int actionPoint = mob.getBasicAttackActionPoint();

			if (mob.attackAnimationTick == mob.attackAnimationActionPoint + 5) {
				mob.playSound(mob.getAttackSound(), 1.25F, 1.0F);
			}

			if (target != null && mob.distanceTo(target) < 3.5
					&& mob.attackAnimationTick == actionPoint) {
				mob.doHurtTarget(target);
			}
		}

		public boolean animationsUseable() {
			return mob.attackAnimationTick <= 0;
		}

	}

	class SummonQGVGoal extends Goal {

		public AbstractWhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public SummonQGVGoal(AbstractWhispererEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
			return target != null && mob.distanceTo(target) <= 15 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.summonQGVAnimationTick = mob.summonQGVAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
			mob.playSound(ModSoundEvents.WHISPERER_SUMMON_QGV_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.summonQGVAnimationTick == mob.summonQGVAnimationLength - 5) {
				mob.playSound(mob.getSummonQGSound(), 1.25F, 1.0F);
			}

			if (target != null && mob.summonQGVAnimationTick == mob.summonQGVAnimationActionPoint) {
				int[] rowToRemove = Util.getRandom(GeomancyHelper.CONFIG_1_ROWS, getRandom());
				GeomancyHelper.summonAreaDenialVineTrap(mob, target, getAreaDenialVine(), rowToRemove);
			}
		}

		@Override
		public void stop() {
			nextUseTime = mob.tickCount + (150 + mob.random.nextInt(50));
		}

		public boolean animationsUseable() {
			return mob.summonQGVAnimationTick <= 0;
		}

	}

	class SummonPQVAttackGoal extends Goal {

		public AbstractWhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public SummonPQVAttackGoal(AbstractWhispererEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
			return target != null && mob.distanceTo(target) <= 12.5 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.summonPQVAnimationTick = mob.summonPQVAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 5);
			mob.playSound(mob.getSummonPoisonFoleySound(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.summonPQVAnimationTick == mob.summonPQVAnimationLength - 12) {
				mob.playSound(getSummonPoisonVocalSound(), 1.25F, 1.0F);
			}

			if (target != null
					&& (mob.summonPQVAnimationTick == mob.summonPQVAnimationActionPoint1
							|| mob.summonPQVAnimationTick == mob.summonPQVAnimationActionPoint2)) {
				boolean movingOnX = mob.random.nextBoolean();
				GeomancyHelper.summonOffensiveVine(mob, mob, mob.getOffensiveVine(),
						movingOnX ? (mob.random.nextBoolean() ? 3 : -3) : 0,
						!movingOnX ? (mob.random.nextBoolean() ? 3 : -3) : 0);
			}
		}

		@Override
		public void stop() {
			nextUseTime = mob.tickCount + (350 + mob.random.nextInt(150));
		}

		public boolean animationsUseable() {
			return mob.summonPQVAnimationTick <= 0;
		}

	}

	class GrappleGoal extends Goal {

		public AbstractWhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public GrappleGoal(AbstractWhispererEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
			return target != null && mob.distanceTo(target) <= 15 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.grappleAnimationTick = mob.grappleAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 6);
			mob.playSound(ModSoundEvents.WHISPERER_GRAPPLE_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.grappleAnimationTick == mob.grappleAnimationLength - 30) {
				mob.playSound(mob.getGrappleSound(), 1.25F, 1.0F);
			}

			int actionPoint = mob.getGrappleActionPoint();

			if (target != null && mob.grappleAnimationTick == actionPoint) {
				AbstractTrapEntity trap = getTrap();
				trap.moveTo(target.getX(), target.getY(), target.getZ());
				if (!mob.isWavewhisperer())
					((SimpleTrapEntity) trap).setTrapType(1);
				PositionUtils.moveToCorrectHeight(trap);
				trap.owner = mob;
				mob.level().addFreshEntity(trap);
			}
		}

		@Override
		public void stop() {
			nextUseTime = mob.tickCount + (200 + mob.random.nextInt(400));
		}

		public boolean animationsUseable() {
			return mob.grappleAnimationTick <= 0;
		}

	}
}
