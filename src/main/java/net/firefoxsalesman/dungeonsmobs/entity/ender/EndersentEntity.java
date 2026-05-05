package net.firefoxsalesman.dungeonsmobs.entity.ender;

import static net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig.COMMON;

import java.util.Map;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.goals.AbstractSummonGoal;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
import net.firefoxsalesman.dungeonsmobs.utils.KnockbackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Our attack goal borrows from Goety, because I am not smart enough to figure
 * how to synchronize it on my own
 */
public class EndersentEntity extends VanillaEnderlingEntity implements KeyframeEntity {
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(EndersentEntity.class,
			EntityDataSerializers.BOOLEAN);

	private static final EntityDataAccessor<Boolean> SMASHING = SynchedEntityData.defineId(EndersentEntity.class,
			EntityDataSerializers.BOOLEAN);

	private int attackAnimationTick = 0;
	private final int attackAnimationLength = 26;

	private int summonAnimationTick = 0;
	private final int summonAnimationLength = 22;

	private final int smashAnimationLength = 37;

	public int appearDelay = 0;

	private Map<String, AnimationState> states;

	public static final EntityDataAccessor<Integer> TELEPORTING = SynchedEntityData.defineId(EndersentEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossEvent = COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()
			? (ServerBossEvent) (new ServerBossEvent(getDisplayName(),
					BossEvent.BossBarColor.PURPLE,
					BossEvent.BossBarOverlay.PROGRESS)).setCreateWorldFog(true)
					.setPlayBossMusic(true)
			: null;

	public EndersentEntity(EntityType<? extends EndersentEntity> type, Level level) {
		super(type, level);
		xpReward = 50;
		states = genStates("idle", "attack", "death", "summon", "teleport", "smash");
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
				.add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.ATTACK_DAMAGE, 14.0D).add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(AttributeRegistry.SUMMON_CAP.get(), 5);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new EndersentEntity.CreateWatchlingGoal(this));
		goalSelector.addGoal(2, new EndersentEntity.AttackGoal(EndersentEntity.this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		targetSelector.addGoal(1, new VanillaEnderlingEntity.FindPlayerGoal(this, null));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, VanillaEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(1,
				new EnderlingTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));
	}

	public void setAttacking(boolean attacking) {
		entityData.set(ATTACKING, attacking);
	}

	public void setSmashing(boolean attacking) {
		entityData.set(SMASHING, attacking);
	}

	public boolean isSmashing() {
		return entityData.get(SMASHING);
	}

	public Boolean isAttackingBool() {
		return entityData.get(ATTACKING);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(TELEPORTING, 0);
		entityData.define(ATTACKING, false);
		entityData.define(SMASHING, false);
	}

	public int isTeleporting() {
		return entityData.get(TELEPORTING);
	}

	public void setTeleporting(int teleporting) {
		if (teleporting == 15 && getTarget() != null) {
			teleport(getTarget().getX() - 5 + random.nextInt(10), getTarget().getY(),
					getTarget().getZ() - 5 + random.nextInt(10));
			entityData.set(TELEPORTING, teleporting);
		}
	}

	@Override
	protected void tickDeath() {
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
		if (hasCustomName() && COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setName(getDisplayName());
		}

	}

	public void setCustomName(@Nullable Component p_200203_1_) {
		super.setCustomName(p_200203_1_);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setName(getDisplayName());
		}
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

	public boolean doHurtTarget(Entity pEntity) {
		playSound(ModSoundEvents.ENDERSENT_ATTACK.get(), 1.0F, 1.0F);
		return super.doHurtTarget(pEntity);
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
		attackAnimationTick--;
	}

	public void baseTick() {
		super.baseTick();

		if (isTeleporting() > 0) {
			setTeleporting(isTeleporting() - 1);
		}

		if (random.nextInt(500) == 0 && getTarget() == null) {
			setTeleporting(50);
		} else if (random.nextInt(20) == 0 && getTarget() != null) {
			setTeleporting(50);
		}

		if (isTeleporting() > 0) {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
		}

		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setProgress(getHealth() / getMaxHealth());
		}
	}

	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.addPlayer(player);
		}
	}

	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.removePlayer(player);
		}
	}

	protected boolean teleport() {
		return false;
	}

	@Override
	protected boolean teleport(double x, double y, double z) {
		boolean result = super.teleport(x, y, z);
		if (result) {
			getState("teleport").start(tickCount);
		}
		return result;
	}

	@Override
	protected SoundEvent getTeleportSound() {
		return ModSoundEvents.ENDERSENT_TELEPORT.get();
	}

	private void setupAnimationStates() {
		getState("death").animateWhen(isDead(), tickCount);
		getState("teleport").animateWhen(teleporting(), tickCount);
		getState("summon").animateWhen(isSummoning() && !teleporting(), tickCount);
		getState("attack").animateWhen(isAttackingBool() && !isSmashing(), tickCount);
		getState("smash").animateWhen(isAttackingBool() && isSmashing(), tickCount);
		getState("idle").animateWhen(!isMoving() && !isSummoning() && !isAttackingBool() && !isDead(),
				tickCount);
	}

	private boolean teleporting() {
		return isTeleporting() > 0;
	}

	private boolean isDead() {
		return deathTime > 0;
	}

	private boolean isSummoning() {
		return summonAnimationTick > 0;
	}

	class AttackGoal extends MeleeAttackGoal {
		private final EndersentEntity entity;

		public AttackGoal(EndersentEntity mob, double speed) {
			super(mob, speed, true);
			entity = mob;
		}

		@Override
		protected double getAttackReachSqr(LivingEntity pEntity) {
			return mob.getBbWidth() * 5.0F * mob.getBbWidth() * 5.0F + pEntity.getBbWidth();
		}

		@Override
		public void tick() {
			super.tick();
			setRunning(10);
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity pEntity, double pDistToEnemySqr) {
			double d0 = getAttackReachSqr(pEntity);
			if (pDistToEnemySqr <= d0 && !entity.isAttackingBool()) {
				entity.setAttacking(true);
				boolean smashing = mob.getRandom().nextInt(5) == 1;
				entity.setSmashing(smashing);
				entity.attackAnimationTick = smashing ? smashAnimationLength : attackAnimationLength;
			}
			int inverseTick = (entity.isSmashing() ? smashAnimationLength : attackAnimationLength)
					- entity.attackAnimationTick;
			if (entity.isAttackingBool()) {
				if (inverseTick == 20 && !entity.isSmashing()) {
					doHurtTarget(getTarget());
				}
				if (inverseTick == 27 && entity.isSmashing()) {
					areaAttack(4, 4, 4, 4, 270, 1.0F);
				}
			}
			if (entity.attackAnimationTick <= 0) {
				entity.setAttacking(false);
				entity.setSmashing(false);
			}
		}

		private void areaAttack(float range, float X, float Y, float Z, float arc, float damage) {
			for (LivingEntity entityHit : level().getEntitiesOfClass(LivingEntity.class,
					getBoundingBox().inflate(X, Y, Z))) {
				float entityHitAngle = (float) ((Math.atan2(
						entityHit.getZ() - getZ(),
						entityHit.getX() - getX()) * (180 / Math.PI)
						- 90) % 360);
				float entityAttackingAngle = yBodyRot % 360;
				if (entityHitAngle < 0) {
					entityHitAngle += 360;
				}
				if (entityAttackingAngle < 0) {
					entityAttackingAngle += 360;
				}
				float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
				float entityHitDistance = (float) Math.sqrt((entityHit.getZ()
						- getZ())
						* (entityHit.getZ() - getZ())
						+ (entityHit.getX() - getX())
								* (entityHit.getX() - getX()));
				if (entityHitDistance <= range
						&& (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2)
						|| (entityRelativeAngle >= 360 - arc / 2
								|| entityRelativeAngle <= -360 + arc / 2)) {
					if (!isAlliedTo(entityHit) && !(entityHit == EndersentEntity.this)
							&& !(entityHit instanceof WatchlingEntity)) {
						entityHit.hurt(damageSources().mobAttack(EndersentEntity.this),
								(float) EndersentEntity.this.getAttributeValue(
										Attributes.ATTACK_DAMAGE) * damage);

						EndersentEntity v = EndersentEntity.this;
						float attackKnockback = (float) getAttributeValue(
								Attributes.ATTACK_KNOCKBACK);
						double ratioX = Mth.sin(v.getYRot() * ((float) Math.PI / 180F));
						double ratioZ = -Mth.cos(v.getYRot() * ((float) Math.PI / 180F));
						double knockbackReduction = 0.35D;
						entityHit.hurt(damageSources().mobAttack(EndersentEntity.this),
								damage);
						KnockbackHelper.forceKnockback(entityHit, attackKnockback * 0.8F,
								ratioX, ratioZ,
								knockbackReduction);
						entityHit.setDeltaMovement(
								entityHit.getDeltaMovement().add(0, 0.3333333, 0));
					}
				}
			}
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAttacking(false);
			entity.setSmashing(false);
		}

	}

	@Override
	public void checkDespawn() {
	}

	public boolean shouldBeStationary() {
		return summonAnimationTick > 0 || appearDelay > 0;
	}

	class CreateWatchlingGoal extends AbstractSummonGoal<EndersentEntity> {

		public CreateWatchlingGoal(EndersentEntity mob) {
			super(mob, 10, 5, 8, DungeonsMobsConfig.Common.ENDERSENT_MOB_SUMMONS.get(),
					ModEntities.WATCHLING.get(), null, null, 2);
		}

		@Override
		protected void tickBody() {
			int clonesByDifficulty = mob.level().getCurrentDifficultyAt(mob.blockPosition())
					.getDifficulty().getId();

			for (int i = 0; i < clonesByDifficulty * 2; i++) {
				super.tickBody();
			}
		}

		@Override
		protected void resetSummonTick() {
			mob.summonAnimationTick = summonAnimationLength;
		}

		@Override
		protected int getSummonTick() {
			return mob.summonAnimationTick;
		}

		@Override
		protected boolean tickCondition() {
			return mob.summonAnimationTick == 1;
		}

		@Override
		public boolean canUse() {
			return super.canUse() && random.nextInt(10) == 0;
		}

		@Override
		public boolean isInterruptable() {
			return mob.shouldBeStationary();
		}

		@Override
		protected void tickStareHook() {
			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}
		}
	}

	@Override
	public Map<String, AnimationState> getStates() {
		return states;
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}

}
