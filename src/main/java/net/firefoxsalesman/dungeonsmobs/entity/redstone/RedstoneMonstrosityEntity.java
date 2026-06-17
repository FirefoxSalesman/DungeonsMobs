package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.RedstoneMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.goals.AbstractSummonGoal;
import net.firefoxsalesman.dungeonsmobs.goals.SimpleRangedAttackGoal;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;
import net.firefoxsalesman.dungeonsmobs.utils.AreaAttackHelper;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
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

public class RedstoneMonstrosityEntity extends Raider implements GeoEntity {
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private AnimationTimer fireTimer = new AnimationTimer(600);
	private AnimationTimer summonTimer = new AnimationTimer(72);
	private static final EntityDataAccessor<Boolean> MELEEATTACKING = SynchedEntityData
			.defineId(RedstoneMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FIRING = SynchedEntityData
			.defineId(RedstoneMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);

	private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(),
			BossEvent.BossBarColor.RED,
			BossEvent.BossBarOverlay.PROGRESS));

	public RedstoneMonstrosityEntity(EntityType<? extends RedstoneMonstrosityEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		setMaxUpStep(1.25F);
		xpReward = 40;
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (hasCustomName()) {
			bossEvent.setName(getDisplayName());
		}

	}

	public void setCustomName(@Nullable Component p_200203_1_) {
		super.setCustomName(p_200203_1_);
		bossEvent.setName(getDisplayName());
	}

	public void baseTick() {
		super.baseTick();
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

	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new SummonRedstoneCubesGoal(this));
		goalSelector.addGoal(6, new AttackGoal(this, 1.3D));
		goalSelector.addGoal(5, new SpewProjectilesGoal(this, 2.0D, 30, 20.0F));
		goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		goalSelector.addGoal(9, new RandomLookAroundGoal(this));

		targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
				.add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.ATTACK_DAMAGE, 20.0D).add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(AttributeRegistry.SUMMON_CAP.get(), 5);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	public boolean isMeleeAttacking() {
		return entityData.get(MELEEATTACKING);
	}

	public void setMeleeAttacking(boolean attacking) {
		entityData.set(MELEEATTACKING, attacking);
	}

	public boolean isFiring() {
		return entityData.get(FIRING);
	}

	public void setFiring(boolean attacking) {
		entityData.set(FIRING, attacking);
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		Vec3 velocity = getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		if (summonTimer.isRunning()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController()
					.setAnimation(RawAnimation.begin().then(
							"animation.redstone_monstrosity.lava_attack",
							LoopType.PLAY_ONCE));

		} else if (isMeleeAttacking()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController()
					.setAnimation(RawAnimation.begin().then(
							"animation.redstone_monstrosity.strong_attack",
							LoopType.PLAY_ONCE));
		} else if (isFiring()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController()
					.setAnimation(RawAnimation.begin().then(
							"animation.redstone_monstrosity.spit",
							LoopType.PLAY_ONCE));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimationSpeed(groundSpeed * 10);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_monstrosity.walk",
							LoopType.LOOP));
		} else {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_monstrosity.idle",
							LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public void applyRaidBuffs(int pWave, boolean pUnusedFalse) {
	}

	@Override
	public void handleEntityEvent(byte pId) {
		super.handleEntityEvent(pId);
		if (pId == 8)
			summonTimer.reset();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.IRON_GOLEM_REPAIR;
	}

	public boolean canBeLeader() {
		return false;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(FIRING, false);
		entityData.define(MELEEATTACKING, false);
	}

	@Override
	public void tick() {
		super.tick();
		summonTimer.dec();
		fireTimer.dec();
	}

	@Override
	public void checkDespawn() {
	}

	class AttackGoal extends MeleeAttackGoal {
		private int maxAttackTimer = 60;
		private final double moveSpeed;
		private int delayCounter;
		private int attackTimer;

		public AttackGoal(RedstoneMonstrosityEntity creatureEntity, double moveSpeed) {
			super(creatureEntity, moveSpeed, true);
			this.moveSpeed = moveSpeed;
		}

		@Override
		public boolean canUse() {
			return getTarget() != null && getTarget().isAlive();
		}

		@Override
		public void start() {
			setAggressive(true);
			delayCounter = 0;
		}

		@Override
		public void tick() {
			LivingEntity livingentity = getTarget();
			if (livingentity == null)
				return;

			lookControl.setLookAt(livingentity, 30.0F, 30.0F);

			if (--delayCounter <= 0) {
				delayCounter = 4 + getRandom().nextInt(7);
				getNavigation().moveTo(livingentity, moveSpeed);
			}

			attackTimer = Math.max(attackTimer - 1, 0);
			checkAndPerformAttack(livingentity, distanceToSqr(livingentity.getX(),
					livingentity.getBoundingBox().minY, livingentity.getZ()));

		}

		@Override
		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			if (attackTimer <= 0 && distToEnemySqr <= getAttackReachSqr(enemy) && !isMeleeAttacking()) {
				setMeleeAttacking(true);
				attackTimer = maxAttackTimer;
			}

			if ((distToEnemySqr <= getAttackReachSqr(enemy)
					|| getBoundingBox().intersects(enemy.getBoundingBox()))
					&& attackTimer == 40) {
				AreaAttackHelper.areaAttack(7, 7, 7, 7, 360, 1.0F, this.mob);
			}

			if (attackTimer <= 0) {
				setMeleeAttacking(false);
			}
		}

		@Override
		public void stop() {
			getNavigation().stop();
			setMeleeAttacking(false);
			if (getTarget() == null)
				setAggressive(false);
		}

		public AttackGoal setMaxAttackTick(int max) {
			maxAttackTimer = max;
			return this;
		}
	}

	class SummonRedstoneCubesGoal extends AbstractSummonGoal<RedstoneMonstrosityEntity> {

		public SummonRedstoneCubesGoal(RedstoneMonstrosityEntity mob) {
			super(mob, 10, 5, 8, DungeonsMobsConfig.Common.REDSTONE_MONSTROSITY_MOB_SUMMONS.get(),
					ModEntities.REDSTONE_CUBE.get(), null, null, 2);
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
		protected boolean tickCondition() {
			return summonTimer.tickEquals(1);
		}

		@Override
		protected AnimationTimer timer() {
			return summonTimer;
		}
	}

	private static void spewProjectiles(RedstoneMonstrosityEntity shooter, LivingEntity target) {
		Vec3 pos = PositionUtils.getOffsetPos(shooter, 0.0, 1.0, -0.75, shooter.yBodyRot);
		for (int i = 0; i < 5; i++) {
			RedstoneMonstrosityProjectileEntity projectile = new RedstoneMonstrosityProjectileEntity(
					shooter.level(), shooter);
			projectile.setPos(pos.x, shooter.getEyeY(), pos.z);
			double aimX = target.getX() - pos.x;
			double aimY = target.getY(0.3333333333333333D) - pos.y;
			double aimZ = target.getZ() - pos.z;
			if (i > 0) {
				aimX += shooter.getRandom().nextInt(-5, 5);
				aimY += shooter.getRandom().nextInt(-5, 5);
				aimZ += shooter.getRandom().nextInt(-5, 5);
			}
			float f = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ)) * 0.2F;

			float horizontalDistance = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ));
			float velocity = Mth.clamp(horizontalDistance * 0.25F, 1.0F, 1.5F); // Clamp velocity for longer
												// shots
			float inaccuracy = 2.0F;
			projectile.shoot(aimX, aimY + (double) f, aimZ, velocity, inaccuracy);

			shooter.level().addFreshEntity(projectile);
		}

		shooter.fireTimer.reset();
	}

	class SpewProjectilesGoal extends SimpleRangedAttackGoal<RedstoneMonstrosityEntity> {

		public SpewProjectilesGoal(RedstoneMonstrosityEntity mob, double speedModifier, int attackInterval,
				float attackRadius) {
			super(mob, (w) -> true, RedstoneMonstrosityEntity::spewProjectiles, speedModifier,
					attackInterval, attackRadius);
		}

		private boolean targetInRange() {
			LivingEntity target = getTarget();
			return target != null && distanceToSqr(target.getX(),
					target.getBoundingBox().minY, target.getZ()) >= 20;
		}

		@Override
		public boolean canUse() {
			return super.canUse() && targetInRange() && fireTimer.animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && targetInRange() && fireTimer.animationsUseable();
		}

		@Override
		public void start() {
			super.start();
			setFiring(true);
		}

		@Override
		public void stop() {
			super.stop();
			setFiring(false);
		}
	}
}
