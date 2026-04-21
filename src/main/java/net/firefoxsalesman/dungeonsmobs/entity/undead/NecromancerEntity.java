package net.firefoxsalesman.dungeonsmobs.entity.undead;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.goals.AbstractSummonGoal;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class NecromancerEntity extends Skeleton {

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState summonAnimationState = new AnimationState();
	public final AnimationState shootAnimationState = new AnimationState();

	private int shootAnimationTick;
	private int shootAnimationLength = 20;
	private int shootAnimationActionPoint = 7;
	private int summonAnimationTick;
	private int summonAnimationLength = 45;
	private int summonAnimationActionPoint1 = summonAnimationLength - 20;
	private int summonAnimationActionPoint2 = summonAnimationLength - 23;
	private int summonAnimationActionPoint3 = summonAnimationLength - 26;
	private int summonAnimationActionPoint4 = summonAnimationLength - 32;
	private int summonAnimationActionPoint5 = summonAnimationLength - 38;
	private int specialAnimationTick;
	private int specialAnimationLength = 48;

	public NecromancerEntity(Level worldIn) {
		super(ModEntities.NECROMANCER.get(), worldIn);
	}

	public NecromancerEntity(EntityType<? extends NecromancerEntity> pEntityType, Level worldIn) {
		super(pEntityType, worldIn);
		this.xpReward = 20;
		setMaxUpStep(1.0F);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Skeleton.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MAX_HEALTH, 40.0D)
				.add(Attributes.ARMOR, 5.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
				.add(AttributeRegistry.SUMMON_CAP.get(), 4);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new NecromancerEntity.SummonGoal(this));
		this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 10, 1.2D, true));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 5F, 1.2D, 1.6D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, IronGolem.class, 5F, 1.2D, 1.6D));
		this.goalSelector.addGoal(4, new NecromancerEntity.ShootAttackGoal(this));
		this.goalSelector.addGoal(5, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public boolean isSpellcasting() {
		return isShooting() || isSummoning();
	}

	private boolean isShooting() {
		return shootAnimationTick > 0;
	}

	@Override
	public void reassessWeaponGoal() {

	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity
				&& ((LivingEntity) entityIn).getMobType() == MobType.UNDEAD) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
		return 2.25F;
	}

	@Override
	protected boolean isSunBurnTick() {
		return false;
	}

	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		SpawnEquipmentHelper.equipMainhand(ModItems.NECROMANCER_STAFF.get().getDefaultInstance(), this);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficultyInstance,
			MobSpawnType spawnReason, @Nullable SpawnGroupData livingEntityDataIn,
			@Nullable CompoundTag compoundNBT) {
		livingEntityDataIn = super.finalizeSpawn(world, difficultyInstance, spawnReason, livingEntityDataIn,
				compoundNBT);

		return livingEntityDataIn;
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.NECROMANCER_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.NECROMANCER_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.NECROMANCER_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.NECROMANCER_STEP.get();
	}

	@Override
	public boolean isLeftHanded() {
		return false;
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.specialAnimationTick = specialAnimationLength;
		} else if (p_28844_ == 11) {
			this.shootAnimationTick = shootAnimationLength;
		} else if (p_28844_ == 9) {
			this.summonAnimationTick = summonAnimationLength;
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

	private boolean isMoving() {
		return walkAnimation.speed() > 1.0E-1F;
	}

	private void setupAnimationStates() {
		summonAnimationState.animateWhen(isSummoning(), tickCount);
		shootAnimationState.animateWhen(isShooting() && !isSummoning(), tickCount);
		idleAnimationState.animateWhen(
				!isSpellcasting() && !isMoving() && isAlive() && summonAnimationTick <= 0,
				tickCount);
	}

	private boolean isSummoning() {
		return summonAnimationTick > 0;
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();

		if (!this.level().isClientSide && this.getTarget() == null && this.random.nextInt(300) == 0) {
			this.specialAnimationTick = this.specialAnimationLength;
			this.level().broadcastEntityEvent(this, (byte) 4);
			this.playSound(ModSoundEvents.NECROMANCER_LAUGH.get(), this.getSoundVolume(),
					this.getVoicePitch());
		}

		if (!this.level().isClientSide && this.getTarget() != null
				&& (this.random.nextInt(100) == 0 || this.getTarget().deathTime > 0)) {
			this.playSound(ModSoundEvents.NECROMANCER_LAUGH.get(), this.getSoundVolume(),
					this.getVoicePitch());
			this.ambientSoundTime = -this.getAmbientSoundInterval() / 2;
		}
	}

	public void tickDownAnimTimers() {
		if (this.specialAnimationTick > 0) {
			this.specialAnimationTick--;
		}

		if (this.shootAnimationTick > 0) {
			this.shootAnimationTick--;
		}

		if (this.summonAnimationTick > 0) {
			this.summonAnimationTick--;
		}
	}

	class SummonGoal extends AbstractSummonGoal<NecromancerEntity> {
		public SummonGoal(NecromancerEntity mob) {
			super(mob);
		}

		protected Optional<SoundEvent> getSummonSound() {
			return Optional.of(ModSoundEvents.NECROMANCER_PREPARE_SUMMON.get());
		}

		protected void setSummonTick(int tick) {
			mob.summonAnimationTick = tick;
		}

		protected int getSummonTick() {
			return mob.summonAnimationTick;
		}

		protected boolean tickCondition() {
			return mob.summonAnimationTick == mob.summonAnimationActionPoint1
					|| mob.summonAnimationTick == mob.summonAnimationActionPoint2
					|| mob.summonAnimationTick == mob.summonAnimationActionPoint3
					|| (mob.summonAnimationTick == mob.summonAnimationActionPoint4
							&& mob.random.nextBoolean())
					|| (mob.summonAnimationTick == mob.summonAnimationActionPoint5
							&& mob.random.nextBoolean());
		}

		protected List<String> getSummonList() {
			return (List<String>) DungeonsMobsConfig.Common.NECROMANCER_MOB_SUMMONS.get();
		}

		@Override
		public boolean canUse() {
			return super.canUse() && mob.distanceTo(target) > 5;
		}

		@Override
		protected EntityType<?> getBackupEntityType() {
			return EntityType.ZOMBIE;
		}

		@Override
		protected int getSummonLength() {
			return mob.summonAnimationLength;
		}

		@Override
		protected int mobSummonRange() {
			return 3;
		}

		@Override
		protected int closeMobSummonRange() {
			return 1;
		}
	}

	class ShootAttackGoal extends Goal {
		public NecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public ShootAttackGoal(NecromancerEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
			return target != null && mob.distanceTo(target) <= 12.5 && mob.distanceTo(target) > 5
					&& mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.shootAnimationTick = mob.shootAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 11);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (target != null && mob.shootAnimationTick == mob.shootAnimationActionPoint) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0.3, 1.5, 0.5, mob.yBodyRot);
				double d1 = target.getX() - pos.x;
				double d2 = target.getY(0.6D) - pos.y;
				double d3 = target.getZ() - pos.z;
				NecromancerOrbEntity necromancerOrb = new NecromancerOrbEntity(mob.level(), mob, d1, d2,
						d3);
				necromancerOrb.setDelayedForm(true);
				necromancerOrb.rotateToMatchMovement();
				necromancerOrb.moveTo(pos.x, pos.y, pos.z);
				mob.level().addFreshEntity(necromancerOrb);
				mob.playSound(ModSoundEvents.NECROMANCER_SHOOT.get(), 1.0F, 1.0F);
			}
		}

		public boolean animationsUseable() {
			return mob.shootAnimationTick <= 0;
		}

	}

}
