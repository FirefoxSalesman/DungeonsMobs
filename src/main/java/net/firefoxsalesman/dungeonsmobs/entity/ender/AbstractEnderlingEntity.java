package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public abstract class AbstractEnderlingEntity extends Monster implements GeoEntity {
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public static final EntityDataAccessor<Integer> ATTACKING = SynchedEntityData.defineId(
			AbstractEnderlingEntity.class,
			EntityDataSerializers.INT);

	public static final EntityDataAccessor<Integer> RUNNING = SynchedEntityData.defineId(
			AbstractEnderlingEntity.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData
			.defineId(AbstractEnderlingEntity.class, EntityDataSerializers.BOOLEAN);

	private final TargetingConditions followPredicate = TargetingConditions.forCombat().range(50.0D)
			.ignoreInvisibilityTesting();

	protected AbstractEnderlingEntity(EntityType<? extends AbstractEnderlingEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
		setMaxUpStep(1.0F);
		setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 7.0D)
				.add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	public void setTarget(@Nullable LivingEntity p_70624_1_) {
		if (p_70624_1_ == null) {
			entityData.set(DATA_STARED_AT, false);
		} else {

		}

		super.setTarget(p_70624_1_); // Forge: Moved down to allow event handlers to write data manager values.
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ATTACKING, 0);
		entityData.define(RUNNING, 0);
		entityData.define(DATA_STARED_AT, false);
	}

	public boolean hasBeenStaredAt() {
		return entityData.get(DATA_STARED_AT);
	}

	public void setBeingStaredAt() {
		entityData.set(DATA_STARED_AT, true);
	}

	private boolean isLookingAtMe(Player p_70821_1_) {
		ItemStack itemstack = p_70821_1_.getInventory().armor.get(3);
		if (itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
			return false;
		} else {
			Vec3 vector3d = p_70821_1_.getViewVector(1.0F).normalize();
			Vec3 vector3d1 = new Vec3(getX() - p_70821_1_.getX(),
					getEyeY() - p_70821_1_.getEyeY(),
					getZ() - p_70821_1_.getZ());
			double d0 = vector3d1.length();
			vector3d1 = vector3d1.normalize();
			double d1 = vector3d.dot(vector3d1);
			return d1 > 1.0D - 0.025D / d0 && p_70821_1_.hasLineOfSight(this);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean checkSpawnRules(LevelAccessor p_213380_1_, MobSpawnType p_213380_2_) {
		return (p_213380_2_ != MobSpawnType.NATURAL
				|| !level().getBiome(blockPosition()).is(Biomes.THE_END))
				&& checkMobSpawnRules(((EntityType<? extends Mob>) getType()), p_213380_1_,
						p_213380_2_, blockPosition(), random);
	}

	public void baseTick() {
		super.baseTick();

		if (isAttacking() > 0) {
			setAttacking(isAttacking() - 1);
		}

		if (isRunning() > 0) {
			setRunning(isRunning() - 1);
		}
	}

	public int isAttacking() {
		return entityData.get(ATTACKING);
	}

	public void setAttacking(int p_189794_1_) {
		entityData.set(ATTACKING, p_189794_1_);
	}

	public int isRunning() {
		return entityData.get(RUNNING);
	}

	public void setRunning(int p_189794_1_) {
		entityData.set(RUNNING, p_189794_1_);
	}

	public void aiStep() {
		if (level().isClientSide) {
			for (int i = 0; i < 2; ++i) {
				level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D),
						getRandomY() - 0.25D,
						getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D,
						-random.nextDouble(),
						(random.nextDouble() - 0.5D) * 2.0D);
			}
		}

		jumping = false;

		super.aiStep();
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	protected void customServerAiStep() {
		if (shouldTeleportInDay() && level().isDay() && random.nextInt(600) == 0) {
			float f = getLightLevelDependentMagicValue();
			if (f > 0.5F && level().canSeeSky(blockPosition())
					&& random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				setTarget(null);
				teleport();
			}
		}

		super.customServerAiStep();
	}

	public boolean shouldTeleportInDay() {
		return false;
	}

	protected boolean teleport() {
		if (!level().isClientSide() && isAlive()) {
			double d0 = getX() + (random.nextDouble() - 0.5D) * 64.0D;
			double d1 = getY() + (double) (random.nextInt(64) - 32);
			double d2 = getZ() + (random.nextDouble() - 0.5D) * 64.0D;
			return teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleportTowards(Entity p_70816_1_) {
		Vec3 vector3d = new Vec3(getX() - p_70816_1_.getX(), getY(0.5D) - p_70816_1_.getEyeY(),
				getZ() - p_70816_1_.getZ());
		vector3d = vector3d.normalize();
		double d0 = 16.0D;
		double d1 = getX() + (random.nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
		double d2 = getY() + (double) (random.nextInt(16) - 8) - vector3d.y * 16.0D;
		double d3 = getZ() + (random.nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
		return teleport(d1, d2, d3);
	}

	protected boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(p_70825_1_, p_70825_3_,
				p_70825_5_);

		while (blockpos$mutable.getY() > 0
				&& !level().getBlockState(blockpos$mutable).blocksMotion()) {
			blockpos$mutable.move(Direction.DOWN);
		}

		BlockState blockstate = level().getBlockState(blockpos$mutable);
		boolean flag = blockstate.blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory
					.onEnderTeleport(this, p_70825_1_, p_70825_3_, p_70825_5_);
			if (event.isCanceled())
				return false;
			boolean flag2 = randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(),
					true);
			if (!isSilent()) {
				level().playSound(null, xo, yo, zo, SoundEvents.ENDERMAN_TELEPORT,
						getSoundSource(), 1.0F, 1.0F);
				playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}
			return flag2;
		} else {
			return false;
		}
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENDERMAN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENDERMAN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENDERMAN_DEATH;
	}

	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (isInvulnerableTo(p_70097_1_)) {
			return false;
		} else {
			boolean flag = super.hurt(p_70097_1_, p_70097_2_);
			if (!level().isClientSide() && !(p_70097_1_.getEntity() instanceof LivingEntity)
					&& random.nextInt(2) == 0) {
				teleport();
			}

			return flag;
		}
	}

	class AttackGoal extends MeleeAttackGoal {

		public final TargetingConditions slimePredicate = TargetingConditions.forCombat().range(20.0D)
				.ignoreInvisibilityTesting();

		public AttackGoal(double speed) {
			super(AbstractEnderlingEntity.this, speed, true);
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

		protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
			double d0 = getAttackReachSqr(p_190102_1_);
			if (p_190102_2_ <= d0 && isTimeToAttack()) {
				resetAttackCooldown();
				mob.doHurtTarget(p_190102_1_);
			} else if (p_190102_2_ <= d0 * 1.5D) {
				if (isTimeToAttack()) {
					resetAttackCooldown();
				}

				if (getTicksUntilNextAttack() <= 30) {
					setAttacking(30);
				}
			} else {
				resetAttackCooldown();
			}
		}
	}

	public class EnderlingTargetGoal<T extends LivingEntity> extends TargetGoal {
		protected final Class<T> targetType;
		protected final int randomInterval;
		protected LivingEntity target;
		protected TargetingConditions targetConditions;

		public EnderlingTargetGoal(Mob pMob, Class<T> p_i50313_2_, boolean p_i50313_3_) {
			this(pMob, p_i50313_2_, p_i50313_3_, false);
		}

		public EnderlingTargetGoal(Mob mob, Class<T> p_i50314_2_, boolean p_i50314_3_,
				boolean p_i50314_4_) {
			this(mob, p_i50314_2_, 10, p_i50314_3_, p_i50314_4_, null);
		}

		public EnderlingTargetGoal(Mob p_i50315_1_, Class<T> p_i50315_2_, int p_i50315_3_, boolean p_i50315_4_,
				boolean p_i50315_5_, @Nullable Predicate<LivingEntity> p_i50315_6_) {
			super(p_i50315_1_, p_i50315_4_, p_i50315_5_);
			targetType = p_i50315_2_;
			randomInterval = p_i50315_3_;
			setFlags(EnumSet.of(Goal.Flag.TARGET));
			targetConditions = TargetingConditions.forCombat().range(getFollowDistance())
					.selector(p_i50315_6_);
		}

		public boolean canUse() {
			if (randomInterval > 0 && mob.getRandom().nextInt(randomInterval) != 0) {
				return false;
			} else {
				findTarget();
				return target != null;
			}
		}

		protected AABB getTargetSearchArea(double p_188511_1_) {
			return mob.getBoundingBox().inflate(p_188511_1_, 4.0D, p_188511_1_);
		}

		protected void findTarget() {
			if (targetType != Player.class && targetType != ServerPlayer.class) {
				target = mob.level().getNearestEntity(targetType, targetConditions,
						mob,
						mob.getX(), mob.getEyeY(), mob.getZ(),
						getTargetSearchArea(getFollowDistance()));
			} else {
				target = mob.level().getNearestPlayer(targetConditions, mob,
						mob.getX(),
						mob.getEyeY(), mob.getZ());
			}

		}

		public void start() {
			mob.setTarget(target);
			super.start();
		}

		public void setTarget(@Nullable LivingEntity target) {
			this.target = target;
		}
	}

	static class FindPlayerGoal extends NearestAttackableTargetGoal<Player> {
		private final AbstractEnderlingEntity enderman;
		private Player pendingTarget;
		private int aggroTime;
		private int teleportTime;
		private final TargetingConditions startAggroTargetConditions;
		private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat();

		public FindPlayerGoal(AbstractEnderlingEntity enderman,
				@Nullable Predicate<LivingEntity> p_i241912_2_) {
			super(enderman, Player.class, 10, false, false, p_i241912_2_);
			this.enderman = enderman;
			startAggroTargetConditions = TargetingConditions.forCombat()
					.range(getFollowDistance())
					.selector((p_220790_1_) -> {
						return enderman.isLookingAtMe((Player) p_220790_1_);
					});
		}

		public boolean canUse() {
			pendingTarget = enderman.level().getNearestPlayer(startAggroTargetConditions,
					enderman);
			return pendingTarget != null;
		}

		public void start() {
			aggroTime = 5;
			teleportTime = 0;
			enderman.setBeingStaredAt();
		}

		public void stop() {
			pendingTarget = null;
			super.stop();
		}

		public boolean canContinueToUse() {
			if (pendingTarget != null) {
				if (!enderman.isLookingAtMe(pendingTarget)) {
					return false;
				} else {
					enderman.lookAt(pendingTarget, 10.0F, 10.0F);
					return true;
				}
			} else {
				return target != null
						&& continueAggroTargetConditions.test(enderman, target)
						|| super.canContinueToUse();
			}
		}

		public void tick() {
			if (enderman.getTarget() == null) {
				super.setTarget(null);
			}

			if (pendingTarget != null) {
				if (--aggroTime <= 0) {
					target = pendingTarget;
					pendingTarget = null;
					super.start();
				}
			} else {
				if (target != null && !enderman.isPassenger()) {
					if (enderman.isLookingAtMe((Player) target)) {
						if (target.distanceToSqr(enderman) < 16.0D) {
							enderman.teleport();
						}

						teleportTime = 0;
					} else if (target.distanceToSqr(enderman) > 256.0D
							&& teleportTime++ >= 30
							&& enderman.teleportTowards(target)) {
						teleportTime = 0;
					}
				}

				super.tick();
			}

		}
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	abstract protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event);

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
	}
}
