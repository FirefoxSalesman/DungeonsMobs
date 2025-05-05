package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

public class EndersentEntity extends VanillaEnderlingEntity {
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(EndersentEntity.class,
			EntityDataSerializers.BOOLEAN);

	public final AnimationState attackAnimationState = new AnimationState();
	private int attackAnimationTimeout = 0;

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	public final AnimationState deathAnimationState = new AnimationState();

	public final AnimationState summonAnimationState = new AnimationState();
	public int summonAnimationTick;
	public final int summonAnimationLength = 22;

	public int appearDelay = 0;

	public static final EntityDataAccessor<Integer> TELEPORTING = SynchedEntityData.defineId(EndersentEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(),
			BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setCreateWorldFog(true)
			.setPlayBossMusic(true);

	public EndersentEntity(EntityType<? extends EndersentEntity> p_i50210_1_, Level p_i50210_2_) {
		super(p_i50210_1_, p_i50210_2_);
		xpReward = 50;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
				.add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.ATTACK_DAMAGE, 14.0D).add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new EndersentEntity.CreateWatchlingGoal(this));
		goalSelector.addGoal(2, new EndersentEntity.AttackGoal(EndersentEntity.this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, VanillaEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));
		targetSelector.addGoal(1, new VanillaEnderlingEntity.FindPlayerGoal(this, null));
	}

	public void setAttacking(boolean attacking) {
		entityData.set(ATTACKING, attacking);
	}

	public Boolean isAttackingBool() {
		return entityData.get(ATTACKING);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(TELEPORTING, 0);
		entityData.define(ATTACKING, false);
	}

	public int isTeleporting() {
		return entityData.get(TELEPORTING);
	}

	public void setTeleporting(int p_189794_1_) {
		if (p_189794_1_ == 15 && getTarget() != null) {
			setPos(getTarget().getX() - 5 + random.nextInt(10),
					getTarget().getY(),
					getTarget().getZ() - 5 + random.nextInt(10));
			level().playSound(null, xo, yo, zo,
					ModSoundEvents.ENDERSENT_TELEPORT.get(), getSoundSource(), 1.0F,
					1.0F);
			playSound(ModSoundEvents.ENDERSENT_TELEPORT.get(), 1.0F, 1.0F);
			entityData.set(TELEPORTING, p_189794_1_);
		}
	}

	@Override
	protected void tickDeath() {
		// TODO: stop endersent from falling & turning red on death
		if (deathTime == 0) {
			deathAnimationState.start(deathTime);
			cancelIdleAnimation();
		}
		++deathTime;
		if (deathTime == 100) {
			remove(RemovalReason.KILLED);

			for (int i = 0; i < 20; ++i) {
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				level().addParticle(ParticleTypes.POOF, getRandomX(1.0D), getRandomY(),
						getRandomZ(1.0D), d0, d1, d2);
			}
		}

	}

	public void readAdditionalSaveData(CompoundTag p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (hasCustomName()) {
			bossEvent.setName(getDisplayName());
		}

	}

	public void setCustomName(@Nullable Component p_200203_1_) {
		super.setCustomName(p_200203_1_);
		bossEvent.setName(getDisplayName());
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENDERSENT_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.ENDERSENT_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENDERSENT_DEATH.get();
	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		playSound(ModSoundEvents.ENDERSENT_ATTACK.get(), 1.0F, 1.0F);
		return super.doHurtTarget(p_70652_1_);
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(getStepSound(), 1.25F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 2.0F;
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.ENDERSENT_STEP.get();
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			setupAnimationStates();
		}
		summonAnimationTick--;
	}

	public void baseTick() {
		super.baseTick();

		if (isTeleporting() > 0) {
			setTeleporting(isTeleporting() - 1);
		}

		if (random.nextInt(500) == 0 && getTarget() == null) {
			setTeleporting(50);
		} else if (random.nextInt(200) == 0 && getTarget() != null) {
			setTeleporting(50);
		}

		if (isTeleporting() > 0) {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
		}

		bossEvent.setProgress(getHealth() / getMaxHealth());
	}

	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		bossEvent.addPlayer(player);
	}

	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		bossEvent.removePlayer(player);
	}

	protected boolean teleport() {
		return false;
	}

	protected boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
		return false;
	}

	private void cancelIdleAnimation() {
		idleAnimationState.stop();
		idleAnimationTimeout = 0;
	}

	private void setupAnimationStates() {
		if (isAttackingBool() && attackAnimationTimeout <= 0) {
			attackAnimationTimeout = 95;
			attackAnimationState.start(tickCount);
			cancelIdleAnimation();
		} else {
			attackAnimationTimeout--;
			if (idleAnimationTimeout <= 0) {
				idleAnimationTimeout = random.nextInt(40) + 95;
				idleAnimationState.start(tickCount);
			} else {
				idleAnimationTimeout--;
			}
		}
	}

	class AttackGoal extends MeleeAttackGoal {
		private final EndersentEntity entity;
		private boolean waiting = false;

		public AttackGoal(EndersentEntity mob, double speed) {
			super(mob, speed, true);
			entity = mob;
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse();
		}

		protected double getAttackReachSqr(LivingEntity pEntity) {
			return mob.getBbWidth() * 5.0F * mob.getBbWidth() * 5.0F + pEntity.getBbWidth();
		}

		public void tick() {
			super.tick();

			setRunning(10);
		}

		protected void checkAndPerformAttack(LivingEntity pEntity, double pDistToEnemySqr) {
			double d0 = getAttackReachSqr(pEntity);
			if (pDistToEnemySqr <= d0 && isTimeToAttack()) {
				resetAttackCooldown();
				entity.setAttacking(true);
				if (waiting) {
					mob.doHurtTarget(pEntity);
					waiting = false;
				} else {
					waiting = true;
				}
			} else if (pDistToEnemySqr <= d0 * 1.5D) {
				if (isTimeToAttack()) {
					resetAttackCooldown();
				}

				if (getTicksUntilNextAttack() <= 30) {
					setAttacking(30);
				}
			} else {
				entity.setAttacking(false);
				resetAttackCooldown();
			}
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAttacking(false);
		}

	}

	@Override
	public void checkDespawn() {
	}

	public boolean shouldBeStationary() {
		return summonAnimationTick > 0 || appearDelay > 0;
	}

	class CreateWatchlingGoal extends Goal {
		public EndersentEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		private final Predicate<Entity> WATCHLING = (p_33346_) -> {
			return p_33346_ instanceof WatchlingEntity
					&& ((WatchlingEntity) p_33346_).getOwner().isPresent()
					&& ((WatchlingEntity) p_33346_).getOwner().get() == mob;
		};

		public CreateWatchlingGoal(EndersentEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
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

			int nearbyClones = mob.level().getEntities(mob, mob.getBoundingBox().inflate(30.0D), WATCHLING)
					.size();

			return target != null && mob.tickCount >= nextUseTime && mob.random.nextInt(10) == 0
					&& mob.hasLineOfSight(target) && nearbyClones <= 0 && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.summonAnimationTick = mob.summonAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 8);
			summonAnimationState.start(tickCount);
		}

		@Override
		public void tick() {
			cancelIdleAnimation();
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.summonAnimationTick == 1) {
				SummonSpotEntity summonSpot = ModEntities.SUMMON_SPOT.get().create(mob.level());
				summonSpot.moveTo(target.blockPosition().offset((int) -12.5 + mob.random.nextInt(25), 0,
						(int) -12.5 + mob.random.nextInt(25)), 0.0F, 0.0F);
				summonSpot.setSummonType(3);
				((ServerLevel) mob.level()).addFreshEntityWithPassengers(summonSpot);
				PositionUtils.moveToCorrectHeight(summonSpot);

				mob.level().broadcastEntityEvent(mob, (byte) 7);
				mob.moveTo(summonSpot.blockPosition(), 0.0F, 0.0F);
				mob.setYBodyRot(mob.random.nextInt(360));
				mob.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(mob.getX(), mob.getEyeY(), mob.getZ()));
				mob.appearDelay = 11;
				mob.level().broadcastEntityEvent(mob, (byte) 6);
				mob.playSound(SoundEvents.ILLUSIONER_MIRROR_MOVE, 1.0F, 1.0F);
				PositionUtils.moveToCorrectHeight(mob);

				if (target instanceof Mob) {
					((Mob) target).setTarget(null);
					target.setLastHurtByMob(null);
					if (target instanceof NeutralMob) {
						((NeutralMob) target).stopBeingAngry();
						((NeutralMob) target).setLastHurtByMob(null);
						((NeutralMob) target).setTarget(null);
						((NeutralMob) target).setPersistentAngerTarget(null);
					}
				}

				int clonesByDifficulty = mob.level().getCurrentDifficultyAt(mob.blockPosition())
						.getDifficulty().getId();

				for (int i = 0; i < clonesByDifficulty * 2; i++) {
					SummonSpotEntity cloneSummonSpot = ModEntities.SUMMON_SPOT.get()
							.create(mob.level());
					cloneSummonSpot.moveTo(
							target.blockPosition().offset(
									(int) -12.5 + mob.random.nextInt(25), 0,
									(int) -12.5 + mob.random.nextInt(25)),
							0.0F, 0.0F);
					cloneSummonSpot.setSummonType(3);
					cloneSummonSpot.mobSpawnRotation = mob.random.nextInt(360);
					((ServerLevel) mob.level()).addFreshEntityWithPassengers(cloneSummonSpot);
					PositionUtils.moveToCorrectHeight(cloneSummonSpot);

					WatchlingEntity clone = ModEntities.WATCHLING.get().create(mob.level());
					clone.finalizeSpawn(((ServerLevel) mob.level()),
							mob.level().getCurrentDifficultyAt(
									cloneSummonSpot.blockPosition()),
							MobSpawnType.MOB_SUMMONED, null, null);
					clone.setOwner(mob);
					cloneSummonSpot.summonedEntity = clone;
				}
			}
		}

		public boolean animationsUseable() {
			return mob.summonAnimationTick <= 0;
		}

		@Override
		public void stop() {
			super.stop();
			nextUseTime = mob.tickCount + 60;
		}

	}
}
