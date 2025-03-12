package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.SnarelingGlobEntity;
import net.firefoxsalesman.dungeonsmobs.mod.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
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

import java.util.EnumSet;
import java.util.function.Predicate;

public class SnarelingEntity extends AbstractEnderlingEntity {

	public static final EntityDataAccessor<Integer> SHOOT_TIME = SynchedEntityData.defineId(SnarelingEntity.class,
			EntityDataSerializers.INT);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public SnarelingEntity(EntityType<? extends SnarelingEntity> p_i50210_1_, Level p_i50210_2_) {
		super(p_i50210_1_, p_i50210_2_);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new SnarelingEntity.AvoidEntityGoal<>(this, 3, 1.0D, 1.0D));
		goalSelector.addGoal(2, new SnarelingEntity.AttackGoal());
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
		return MobType.ARTHROPOD;
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(getStepSound(), 0.75F, 1.0F);
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.SNARELING_STEP.get();
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.SNARELING_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.SNARELING_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.SNARELING_DEATH.get();
	}

	public void baseTick() {
		super.baseTick();

		if (getTarget() != null && getTarget().isAlive() && distanceTo(getTarget()) > 5
				&& getTarget().hasEffect(ModEffects.ENSNARED.get())
				&& random.nextInt(10) == 0) {
			teleport(getTarget().getX() - 3 + random.nextInt(6), getTarget().getY(),
					getTarget().getZ() - 3 + random.nextInt(6));
		}

		if (getTarget() != null && getTarget().isAlive() && !getTarget().hasEffect(ModEffects.ENSNARED.get())
				&& hasLineOfSight(getTarget()) && getShootTime() <= 0 && random.nextInt(10) == 0) {
			setShootTime(80);
			playSound(ModSoundEvents.SNARELING_PREPARE_SHOOT.get(), 2.0F,
					1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		}

		if (getShootTime() > 0) {
			setShootTime(getShootTime() - 1);
		}

		if (!level().isClientSide && getShootTime() == 15 && getTarget() != null && getTarget().isAlive()) {

			performRangedAttack(getTarget(), 2.0F);
		}

		if (isAttacking() == 29) {
			playSound(ModSoundEvents.SNARELING_ATTACK.get(), 1.0F, 1.0F);
		}

		if (getShootTime() > 0) {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3F);
		}
	}

	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
		SnarelingGlobEntity snowballentity = new SnarelingGlobEntity(level(), this);
		double d0 = p_82196_1_.getEyeY() - 1.75F;
		double d1 = p_82196_1_.getX() - getX();
		double d2 = d0 - snowballentity.getY();
		double d3 = p_82196_1_.getZ() - getZ();
		float f = Mth.sqrt((float) (d1 * d1 + d3 * d3)) * 0.2F;
		snowballentity.shoot(d1, d2 + (double) f, d3, 1.6F, 2.0F);
		playSound(ModSoundEvents.SNARELING_SHOOT.get(), 2.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		level().addFreshEntity(snowballentity);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(SHOOT_TIME, 0);
	}

	public int getShootTime() {
		return entityData.get(SHOOT_TIME);
	}

	public void setShootTime(int p_189794_1_) {
		entityData.set(SHOOT_TIME, p_189794_1_);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (getShootTime() > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("snareling_shoot", LoopType.LOOP));
		} else if (isAttacking() > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("snareling_attack", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin().then("snareling_walk", LoopType.LOOP));
		} else {
			event.getController().setAnimation(RawAnimation.begin().then("snareling_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	class AttackGoal extends MeleeAttackGoal {

		public AttackGoal() {
			super(SnarelingEntity.this, 1.0D, true);
		}

		public boolean canContinueToUse() {
			return SnarelingEntity.this.getShootTime() <= 0 && super.canContinueToUse();
		}

		protected double getAttackReachSqr(LivingEntity p_179512_1_) {
			return mob.getBbWidth() * 3.0F * mob.getBbWidth() * 3.0F + p_179512_1_.getBbWidth();
		}

		protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
			double d0 = getAttackReachSqr(p_190102_1_);
			if (p_190102_2_ <= d0 && isTimeToAttack() && SnarelingEntity.this.getShootTime() <= 0) {
				resetAttackCooldown();
				mob.doHurtTarget(p_190102_1_);
			} else if (p_190102_2_ <= d0 * 1.5D) {
				if (isTimeToAttack()) {
					resetAttackCooldown();
				}

				if (getTicksUntilNextAttack() <= 30) {
					SnarelingEntity.this.setAttacking(30);
				}
			} else {
				resetAttackCooldown();
			}
		}
	}

	public class AvoidEntityGoal<T extends LivingEntity> extends Goal {
		protected final PathfinderMob mob;
		private final double walkSpeedModifier;
		private final double sprintSpeedModifier;
		protected LivingEntity toAvoid;
		protected final float maxDist;
		protected Path path;
		protected final PathNavigation pathNav;
		protected final Predicate<LivingEntity> avoidPredicate;
		protected final Predicate<LivingEntity> predicateOnAvoidEntity;
		private final TargetingConditions avoidEntityTargeting;

		public AvoidEntityGoal(PathfinderMob mob, float p_i46404_3_, double p_i46404_4_,
				double p_i46404_6_) {
			this(mob, (p_200828_0_) -> {
				return true;
			}, p_i46404_3_, p_i46404_4_, p_i46404_6_, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
		}

		public AvoidEntityGoal(PathfinderMob mob, Predicate<LivingEntity> avoidPredicate,
				float maxDist, double walkSpeedModifier, double sprintSpeedModifier,
				Predicate<LivingEntity> predicateOnAvoidEntity) {
			this.mob = mob;
			this.avoidPredicate = avoidPredicate;
			this.maxDist = maxDist;
			this.walkSpeedModifier = walkSpeedModifier;
			this.sprintSpeedModifier = sprintSpeedModifier;
			this.predicateOnAvoidEntity = predicateOnAvoidEntity;
			this.pathNav = mob.getNavigation();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist)
					.selector(predicateOnAvoidEntity.and(avoidPredicate));
		}

		public AvoidEntityGoal(PathfinderMob mob, float p_i48860_3_, double p_i48860_4_,
				double p_i48860_6_, Predicate<LivingEntity> p_i48860_8_) {
			this(mob, (p_203782_0_) -> {
				return true;
			}, p_i48860_3_, p_i48860_4_, p_i48860_6_, p_i48860_8_);
		}

		public boolean canUse() {
			toAvoid = SnarelingEntity.this.getTarget();
			if (toAvoid == null || mob.distanceTo(toAvoid) > maxDist) {
				return false;
			} else {
				Vec3 vector3d = DefaultRandomPos.getPosAway(mob, 16, 7, toAvoid.position());
				if (vector3d == null) {
					return false;
				} else if (toAvoid.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < toAvoid
						.distanceToSqr(mob)) {
					return false;
				} else {
					path = pathNav.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
					return SnarelingEntity.this.getTarget() != null
							&& SnarelingEntity.this.getTarget().isAlive()
							&& !SnarelingEntity.this.getTarget()
									.hasEffect(ModEffects.ENSNARED.get())
							&& path != null;
				}
			}
		}

		public boolean canContinueToUse() {
			return SnarelingEntity.this.getTarget() != null && SnarelingEntity.this.getTarget().isAlive()
					&& !SnarelingEntity.this.getTarget().hasEffect(ModEffects.ENSNARED.get())
					&& !pathNav.isDone();
		}

		public void start() {
			pathNav.moveTo(path, walkSpeedModifier);
		}

		public void stop() {
			toAvoid = null;
		}

		public void tick() {
			SnarelingEntity.this.setShootTime(0);
			if (mob.distanceToSqr(toAvoid) < 49.0D) {
				mob.getNavigation().setSpeedModifier(sprintSpeedModifier);
			} else {
				mob.getNavigation().setSpeedModifier(walkSpeedModifier);
			}

		}
	}

}
