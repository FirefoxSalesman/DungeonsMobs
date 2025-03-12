package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlastlingBulletEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.function.Predicate;

public class BlastlingEntity extends AbstractEnderlingEntity implements RangedAttackMob {

	public static final EntityDataAccessor<Integer> SHOOT_TIME = SynchedEntityData.defineId(BlastlingEntity.class,
			EntityDataSerializers.INT);

	public float flameTicks;

	public BlastlingEntity(EntityType<? extends BlastlingEntity> p_i50210_1_, Level p_i50210_2_) {
		super(p_i50210_1_, p_i50210_2_);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new RangedAttackGoal(this, 1.15D, 20, 10.0F));
		goalSelector.addGoal(0, new BlastlingEntity.AvoidEntityGoal<>(this, 5, 1.0D, 1.0D));
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
		return MobType.UNDEAD;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.225F).add(Attributes.ATTACK_DAMAGE, 7.0D)
				.add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.BLASTLING_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.BLASTLING_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.BLASTLING_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(getStepSound(), 0.75F, 1.0F);
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.BLASTLING_STEP.get();
	}

	public void baseTick() {
		super.baseTick();

		flameTicks = flameTicks + 0.25F;

		if (getTarget() != null && getShootTime() <= 2) {
			setShootTime(15);
		}

		if (getShootTime() > 0) {
			setShootTime(getShootTime() - 1);
		}

		if (!level().isClientSide && (getShootTime() == 2 || getShootTime() == 8)
				&& getTarget() != null && getTarget().isAlive()) {

			shoot(getShootTime() == 2, getTarget());
		}
	}

	private void shoot(boolean leftArm, LivingEntity p_82216_2_) {
		readyShoot(leftArm, p_82216_2_.getX(),
				p_82216_2_.getY() + (double) p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.getZ());
		playSound(ModSoundEvents.BLASTLING_SHOOT.get(), 2.0F, getVoicePitch());
	}

	private void readyShoot(boolean p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_) {
		double d0 = getHeadX(p_82209_1_);
		double d1 = getHeadY(p_82209_1_);
		double d2 = getHeadZ(p_82209_1_);
		double d3 = p_82209_2_ - d0;
		double d4 = p_82209_4_ - d1;
		double d5 = p_82209_6_ - d2;
		BlastlingBulletEntity blastlingBulletEntity = new BlastlingBulletEntity(level(), this, d3, d4, d5);
		blastlingBulletEntity.setOwner(this);

		blastlingBulletEntity.setPosRaw(d0, d1, d2);
		level().addFreshEntity(blastlingBulletEntity);
	}

	private double getHeadX(boolean p_82214_1_) {
		float f = (yBodyRot + (float) (180 * (p_82214_1_ ? 0 : 0.75))) * ((float) Math.PI / 180F);
		float f1 = Mth.cos(f);
		return getX() + (double) f1 * 1.3D;
	}

	private double getHeadY(boolean p_82208_1_) {
		return getY() + 1.75D;
	}

	private double getHeadZ(boolean p_82213_1_) {
		float f = (yBodyRot + (float) (180 * (p_82213_1_ ? 0 : 0.75))) * ((float) Math.PI / 180F);
		float f1 = Mth.sin(f);
		return getZ() + (double) f1 * 1.3D;
	}

	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {

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

    protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
	if (getShootTime() > 0) {
	    event.getController()
		.setAnimation(RawAnimation.begin().then("blastling_shoot", LoopType.LOOP));
	} else {
	    if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
		event.getController().setAnimation(
		    RawAnimation.begin().then("blastling_walk", LoopType.LOOP));
	    } else {
		event.getController().setAnimation(
		    RawAnimation.begin().then("blastling_idle", LoopType.LOOP));
	    }
	}
	return PlayState.CONTINUE;
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

		public AvoidEntityGoal(PathfinderMob p_i46404_1_, float p_i46404_3_, double p_i46404_4_,
				double p_i46404_6_) {
			this(p_i46404_1_, (p_200828_0_) -> {
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
			toAvoid = BlastlingEntity.this.getTarget();
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
					return BlastlingEntity.this.getTarget() != null
							&& BlastlingEntity.this.getTarget().isAlive()
							&& path != null;
				}
			}
		}

		public boolean canContinueToUse() {
			return BlastlingEntity.this.getTarget() != null && BlastlingEntity.this.getTarget().isAlive()
					&& !pathNav.isDone();
		}

		public void start() {
			pathNav.moveTo(path, walkSpeedModifier);
		}

		public void stop() {
			toAvoid = null;
		}

		public void tick() {
			BlastlingEntity.this.setShootTime(0);
			if (mob.distanceToSqr(toAvoid) < 49.0D) {
				mob.getNavigation().setSpeedModifier(sprintSpeedModifier);
			} else {
				mob.getNavigation().setSpeedModifier(walkSpeedModifier);
			}

		}
	}

}
