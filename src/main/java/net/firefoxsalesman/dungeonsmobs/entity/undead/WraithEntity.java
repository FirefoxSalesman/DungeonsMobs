package net.firefoxsalesman.dungeonsmobs.entity.undead;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;
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

public class WraithEntity extends Monster implements GeoEntity {

	public int summonFireAttackAnimationTick;
	public int summonFireAttackAnimationLength = 40;
	public int summonFireAttackAnimationActionPoint = 20;

	public int teleportAnimationTick;
	public int teleportAnimationLength = 40;
	public int teleportAnimationActionPoint = 18;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public WraithEntity(EntityType<? extends WraithEntity> type, Level world) {
		super(type, world);
		setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
	}

	protected void registerGoals() {
		goalSelector.addGoal(1, new RestrictSunGoal(this));
		goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));
		goalSelector.addGoal(3, new WraithEntity.TeleportGoal(this));
		goalSelector.addGoal(4, new WraithEntity.SummonFireAttackGoal(this));
		goalSelector.addGoal(5, new ApproachTargetGoal(this, 8, 1.2D, true));
		goalSelector.addGoal(6, new LookAtTargetGoal(this));
		goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true)
				.setUnseenMemoryTicks(300));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false,
				Turtle.BABY_ON_LAND_SELECTOR).setUnseenMemoryTicks(300));
	}

	public boolean isSpellcasting() {
		return summonFireAttackAnimationTick > 0;
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			teleportAnimationTick = teleportAnimationLength;
		} else if (p_28844_ == 11) {
			summonFireAttackAnimationTick = summonFireAttackAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity
				&& ((LivingEntity) entityIn).getMobType() == MobType.UNDEAD) {
			return getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		tickDownAnimTimers();

		if (teleportAnimationTick > 0) {
			level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, getRandomX(1), getY(),
					getRandomZ(1), random.nextGaussian() * 0.01, 0.1,
					random.nextGaussian() * 0.01);
		}
	}

	public void tickDownAnimTimers() {
		if (summonFireAttackAnimationTick > 0) {
			summonFireAttackAnimationTick--;
		}

		if (teleportAnimationTick > 0) {
			teleportAnimationTick--;
		}
	}

	public void aiStep() {

		if (!onGround() && getDeltaMovement().y < 0.0D) {
			setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.75D, 1.0D));
		}

		if (isAlive()) {
			boolean flag = isSunSensitive() && isSunBurnTick();
			if (flag) {
				ItemStack itemstack = getItemBySlot(EquipmentSlot.HEAD);
				if (!itemstack.isEmpty()) {
					if (itemstack.isDamageableItem()) {
						itemstack.setDamageValue(
								itemstack.getDamageValue() + random.nextInt(2));
						if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
							broadcastBreakEvent(EquipmentSlot.HEAD);
							setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
						}
					}

					flag = false;
				}

				if (flag) {
					setSecondsOnFire(8);
				}
			}
		}

		super.aiStep();
	}

	public boolean isSunSensitive() {
		return true;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.4D)
				.add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, 20.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WRAITH_IDLE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.WRAITH_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WRAITH_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.WRAITH_FLY.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(getStepSound(), 0.5F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (summonFireAttackAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("wraith_attack", LoopType.LOOP));
		} else if (teleportAnimationTick > 10) {
			event.getController().setAnimation(RawAnimation.begin().then("wraith_teleport", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin().then("wraith_fly", LoopType.LOOP));
		} else {
			event.getController().setAnimation(RawAnimation.begin().then("wraith_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	private boolean teleport(double pX, double pY, double pZ) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

		while (blockpos$mutableblockpos.getY() > level().getMinBuildHeight()
				&& !level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		BlockState blockstate = level().getBlockState(blockpos$mutableblockpos);
		boolean flag = blockstate.blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			EntityTeleportEvent.EnderEntity event = ForgeEventFactory.onEnderTeleport(this, pX, pY, pZ);
			if (event.isCanceled()) {
				return false;
			} else {
				Vec3 vec3 = position();
				boolean randomTeleport = randomTeleport(event.getTargetX(), event.getTargetY(),
						event.getTargetZ(), true);
				if (randomTeleport) {
					level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
					if (!isSilent()) {
						level().playSound((Player) null, xo, yo, zo,
								ModSoundEvents.WRAITH_TELEPORT.get(),
								getSoundSource(), 1.0F, 1.0F);
						playSound(ModSoundEvents.WRAITH_TELEPORT.get(), 1.0F, 1.0F);
					}
				}

				return randomTeleport;
			}
		} else {
			return false;
		}
	}

	class TeleportGoal extends Goal {
		public static final int TELEPORT_AWAY_RANGE = 20;
		public static final int TELEPORT_TO_RANGE = 10;
		public static final int TARGET_TOO_FAR = 16;
		public static final int TARGET_TOO_CLOSE = 4;
		public WraithEntity mob;
		@Nullable
		public LivingEntity target;

		public TeleportGoal(WraithEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
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

			return target != null
					&& (mob.distanceTo(target) <= TARGET_TOO_CLOSE
							|| mob.distanceTo(target) >= TARGET_TOO_FAR)
					&& mob.hasLineOfSight(target)
					&& animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WRAITH_IDLE.get(), 1.0F, mob.getVoicePitch());
			mob.teleportAnimationTick = mob.teleportAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.teleportAnimationTick == mob.teleportAnimationActionPoint) {
				if (mob.distanceTo(target) >= TARGET_TOO_FAR) {
					tryTeleport(TELEPORT_TO_RANGE, target.getX(), target.getY(),
							target.getZ());
				} else {
					tryTeleport(TELEPORT_AWAY_RANGE, target.getX(), target.getY(),
							target.getZ());
				}
			}
		}

		private void tryTeleport(int teleportToRange, double targetX, double targetY, double targetZ) {
			for (int i = 0; i < 10; i++) {
				if (mob.teleport(
						targetX - teleportToRange + mob.random.nextInt(teleportToRange * 2),
						targetY,
						targetZ - teleportToRange + mob.random.nextInt(teleportToRange * 2)))
					break;
			}
		}

		public boolean animationsUseable() {
			return mob.teleportAnimationTick <= 0;
		}

	}

	class SummonFireAttackGoal extends Goal {
		public WraithEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime = 0;

		public SummonFireAttackGoal(WraithEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
			this.mob = mob;
			this.target = mob.getTarget();
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

			return target != null && mob.tickCount >= nextUseTime && mob.distanceTo(target) <= 14
					&& mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WRAITH_ATTACK.get(), 1.0F, mob.getVoicePitch());
			mob.summonFireAttackAnimationTick = mob.summonFireAttackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 11);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null
					&& mob.summonFireAttackAnimationTick == mob.summonFireAttackAnimationActionPoint) {
				WraithFireEntity wraithFire = ModEntities.WRAITH_FIRE.get().create(mob.level());
				wraithFire.owner = mob;
				wraithFire.moveTo(target.position());
				mob.level().addFreshEntity(wraithFire);
				PositionUtils.moveToCorrectHeight(wraithFire);
			}
		}

		@Override
		public void stop() {
			super.stop();
			nextUseTime = mob.tickCount + 60 + mob.random.nextInt(60);
		}

		public boolean animationsUseable() {
			return mob.summonFireAttackAnimationTick <= 0;
		}

	}
}
