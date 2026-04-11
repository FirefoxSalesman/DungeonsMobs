package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MageMissileEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.AnimatedMage;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class MageCloneEntity extends AbstractIllager implements AnimatedMage {

	private static final EntityDataAccessor<Boolean> DELAYED_APPEAR = SynchedEntityData.defineId(
			MageCloneEntity.class,
			EntityDataSerializers.BOOLEAN);

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState celebrateAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState vanishAnimationState = new AnimationState();
	public final AnimationState appearAnimationState = new AnimationState();
	public int shootAnimationTick;
	public int shootAnimationLength = 40;
	public int shootAnimationActionPoint = 20;

	public int appearAnimationTick;
	public int appearAnimationLength = 25;

	public int lifeTime;

	private Mob owner;

	public MageCloneEntity(EntityType<? extends MageCloneEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
	}

	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new MageCloneEntity.RemainStationaryGoal());
		goalSelector.addGoal(1, new MageCloneEntity.ShootAttackGoal(this));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, AbstractVillager.class, 5.0F, 1.2D, 1.15D));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 5.0F, 1.2D, 1.2D));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 5.0F, 1.3D, 1.15D));
		goalSelector.addGoal(3, new ApproachTargetGoal(this, 14, 1.0D, true));
		goalSelector.addGoal(4, new LookAtTargetGoal(this));
		goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		targetSelector.addGoal(1, new MageCloneEntity.CopyOwnerTargetGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		entityData.define(DELAYED_APPEAR, false);
	}

	public boolean hasDelayedAppear() {
		return entityData.get(DELAYED_APPEAR);
	}

	public void setDelayedAppear(boolean attached) {
		entityData.set(DELAYED_APPEAR, attached);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float p_70097_2_) {
		if (damageSource.getEntity() != null && isAlliedTo(damageSource.getEntity())
				&& damageSource != damageSources().fellOutOfWorld()) {
			return false;
		} else {
			return super.hurt(damageSource, p_70097_2_);
		}
	}

	public Mob getOwner() {
		return owner;
	}

	public void setOwner(Mob p_190658_1_) {
		owner = p_190658_1_;
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 1) {
			remove(RemovalReason.DISCARDED);
			for (int i = 0; i < 20; ++i) {
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				level().addParticle(ParticleTypes.POOF, getRandomX(1.0D), getRandomY(),
						getRandomZ(1.0D), d0, d1, d2);
			}
		}

	}

	public boolean shouldBeStationary() {
		return appearAnimationTick > 0;
	}

	@Override
	public boolean isLeftHanded() {
		return false;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.MAX_HEALTH, 40.0D);
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			shootAnimationTick = shootAnimationLength;
		} else if (p_28844_ == 8) {
			appearAnimationTick = appearAnimationLength;
		} else if (p_28844_ == 11) {
			for (int i = 0; i < 20; ++i) {
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				level().addParticle(ParticleTypes.POOF, getRandomX(1.0D), getRandomY(),
						getRandomZ(1.0D), d0, d1, d2);
			}
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		attackAnimationState.animateWhen(isAttacking() && !isCelebrating(), tickCount);
		appearAnimationState.animateWhen(isAppearing() && !isAttacking() && !isCelebrating(), tickCount);
		idleAnimationState.animateWhen(!isAppearing() && !isAttacking() && !isMoving() && isAlive(), tickCount);
	}

	private boolean isAppearing() {
		return appearAnimationTick > 0;
	}

	private boolean isAttacking() {
		return shootAnimationTick > 0;
	}

	private boolean isMoving() {
		return walkAnimation.speed() > 1.0E-1F;
	}

	public void baseTick() {
		super.baseTick();
		tickDownAnimTimers();

		lifeTime++;

		if (!level().isClientSide && hasDelayedAppear()) {
			appearAnimationTick = appearAnimationLength;
			level().broadcastEntityEvent(this, (byte) 8);
			setDelayedAppear(false);
		}

		int lifeTimeByDifficulty = level().getCurrentDifficultyAt(blockPosition()).getDifficulty()
				.getId();

		if (!level().isClientSide && (hurtTime > 0
				|| ((lifeTime >= lifeTimeByDifficulty * 100) || getOwner() != null
						&& (getOwner().isDeadOrDying() || getOwner().hurtTime > 0
								|| getOwner().getTarget() == null)))) {
			if (hurtTime > 0) {
				playSound(getDeathSound(), getSoundVolume(), getVoicePitch());
			} else {
				playSound(SoundEvents.ILLUSIONER_MIRROR_MOVE, getSoundVolume(), 1.0F);
			}
			remove(RemovalReason.DISCARDED);
			level().broadcastEntityEvent(this, (byte) 11);
		}

		if (!level().isClientSide() && getOwner() != null) {
			setHealth(getOwner().getHealth());
		}
	}

	public void tickDownAnimTimers() {
		if (shootAnimationTick > 0) {
			shootAnimationTick--;
		}

		if (appearAnimationTick > 0) {
			appearAnimationTick--;
		}
	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity
				&& ((LivingEntity) entityIn).getMobType() == MobType.ILLAGER) {
			return getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENCHANTER_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ENCHANTER_HURT.get();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	class ShootAttackGoal extends Goal {
		public MageCloneEntity mob;
		@Nullable
		public LivingEntity target;

		public ShootAttackGoal(MageCloneEntity mob) {
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
			return target != null && !mob.shouldBeStationary() && mob.distanceTo(target) <= 16
					&& mob.distanceTo(target) > 5 && mob.hasLineOfSight(target)
					&& animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !mob.shouldBeStationary() && !animationsUseable();
		}

		@Override
		public void start() {
			mob.shootAnimationTick = mob.shootAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (target != null && mob.shootAnimationTick == mob.shootAnimationActionPoint) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0.3, 1.5, 0.5, mob.yBodyRot);
				double d1 = target.getX() - pos.x;
				double d3 = target.getZ() - pos.z;
				MageMissileEntity mageMissile = new MageMissileEntity(mob.level(), mob, d1 / 5, 5,
						d3 / 5);
				mageMissile.rotateToMatchMovement();
				mageMissile.moveTo(pos.x, pos.y, pos.z);
				mageMissile.setOwner(mob);
				mob.level().addFreshEntity(mageMissile);
				mob.playSound(ModSoundEvents.NECROMANCER_SHOOT.get(), 1.0F, 2.0F);
			}
		}

		public boolean animationsUseable() {
			return mob.shootAnimationTick <= 0;
		}

	}

	class CopyOwnerTargetGoal extends TargetGoal {
		private final TargetingConditions copyOwnerTargeting = TargetingConditions.forCombat()
				.ignoreInvisibilityTesting();

		public CopyOwnerTargetGoal(PathfinderMob p_i47231_2_) {
			super(p_i47231_2_, false);
		}

		public boolean canUse() {
			return owner != null && owner.getTarget() != null
					&& canAttack(owner.getTarget(),
							copyOwnerTargeting);
		}

		public void start() {
			setTarget(owner.getTarget());
			super.start();
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

	@Override
	public AnimationState getIdleState() {
		return idleAnimationState;
	}

	@Override
	public AnimationState getCelebrateState() {
		return celebrateAnimationState;
	}

	@Override
	public AnimationState getAttackState() {
		return attackAnimationState;
	}

	@Override
	public AnimationState getVanishState() {
		return vanishAnimationState;
	}

	@Override
	public AnimationState getAppearState() {
		return appearAnimationState;
	}

}
