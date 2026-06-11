package net.firefoxsalesman.dungeonsmobs.entity.water;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.DrownedNecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.NecromancerOrbEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.firefoxsalesman.dungeonsmobs.goals.AbstractSummonGoal;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;
import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;

public class DrownedNecromancerEntity extends Drowned implements KeyframeEntity {

	private Map<String, AnimationState> states;
	private final AnimationTimer landShootTimer = new AnimationTimer(20);
	private static final int landShootAnimationActionPoint = 7;
	private final AnimationTimer landSummonTimer = new AnimationTimer(45);
	private static final int landSummonAnimationActionPoint1 = 25;
	private static final int landSummonAnimationActionPoint2 = 22;
	private static final int landSummonAnimationActionPoint3 = 19;
	private static final int landSummonAnimationActionPoint4 = 13;
	private static final int landSummonAnimationActionPoint5 = 7;
	private final AnimationTimer rainTridentStormTimer = new AnimationTimer(45);
	private final AnimationTimer rainShootTimer = new AnimationTimer(40);
	private static final int rainShootAnimationActionPoint = 23;
	private final AnimationTimer summonTimer = new AnimationTimer(45);
	private static final int summonAnimationActionPoint = 23;
	private final AnimationTimer shootTimer = new AnimationTimer(40);
	private static final int shootAnimationActionPoint = 23;
	private final AnimationTimer tridentStormTimer = new AnimationTimer(45);

	public DrownedNecromancerEntity(EntityType<? extends DrownedNecromancerEntity> type, Level worldIn) {
		super(type, worldIn);
		states = genStates("swim", "walk", "waterIdle", "landIdle", "waterSummon", "landSummon", "shoot",
				"landShoot", "waterShoot", "waterTridentStorm", "landTridentStorm");
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Drowned.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(ForgeMod.SWIM_SPEED.get(), 2.5D).add(Attributes.FOLLOW_RANGE, 30.0D)
				.add(Attributes.MAX_HEALTH, 75.0D).add(Attributes.ARMOR, 12.5D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6D).add(AttributeRegistry.SUMMON_CAP.get(), 4);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new DrownedNecromancerEntity.GoToWaterGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new DrownedNecromancerEntity.LandSummonGoal(this));
		this.goalSelector.addGoal(2, new DrownedNecromancerEntity.SummonGoal(this));
		this.goalSelector.addGoal(3, new DrownedNecromancerEntity.TridentStormGoal(this));
		this.goalSelector.addGoal(3, new DrownedNecromancerEntity.RainTridentStormGoal(this));
		this.goalSelector.addGoal(4, new DrownedNecromancerEntity.ShootAttackGoal(this));
		this.goalSelector.addGoal(4, new DrownedNecromancerEntity.RainShootAttackGoal(this));
		this.goalSelector.addGoal(4, new DrownedNecromancerEntity.LandShootAttackGoal(this));
		this.goalSelector.addGoal(5, new ApproachTargetGoal(this, 7.5, 1.1D, true));
		this.goalSelector.addGoal(6, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(7, new DrownedNecromancerEntity.GoToBeachGoal(this, 1.0D));
		this.goalSelector.addGoal(8,
				new DrownedNecromancerEntity.SwimUpGoal(this, 1.0D, level().getSeaLevel()));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	private boolean isSummoning() {
		return summonTimer.isRunning() || landSummonTimer.isRunning();
	}

	public boolean isSpellcasting() {
		return isShootingGeneric() || tridentStormTimer.isRunning() || rainTridentStormTimer.isRunning()
				|| isSummoning();
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
		return 2.4F;
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide)
			setupAnimationStates();
	}

	private void setupAnimationStates() {
		animate(getState("walk"), isMoving() && !isInWater());
		animate(getState("swim"), isMoving() && isInWater());
		animate(getState("waterSummon"), summonTimer.isRunning() && !isMoving());
		animate(getState("landSummon"), landSummonTimer.isRunning() && !summonTimer.isRunning() && !isMoving());
		animate(getState("shoot"), shootTimer.isRunning() && !isSummoning() && !isMoving());
		animate(getState("landShoot"),
				landShootTimer.isRunning() && !shootTimer.isRunning() && !isSummoning() && !isMoving());
		animate(getState("waterShoot"),
				rainShootTimer.isRunning() && !landShootTimer.isRunning() && !shootTimer.isRunning()
						&& !isSummoning() && !isMoving());
		animate(getState("waterTridentStorm"),
				rainTridentStormTimer.isRunning() && !isShootingGeneric() && !isSummoning()
						&& !isMoving());
		animate(getState("landTridentStorm"),
				tridentStormTimer.isRunning() && !isShootingGeneric() && !isSummoning() && !isMoving());
		animate(getState("waterIdle"), !isSpellcasting() && !isMoving() && isAlive() && isInWater());
		animate(getState("landIdle"), !isSpellcasting() && !isMoving() && isAlive() && !isInWater());
	}

	private void animate(AnimationState state, boolean condition) {
		state.animateWhen(condition, tickCount);
	}

	private boolean isShootingGeneric() {
		return rainShootTimer.isRunning() || landShootTimer.isRunning() || shootTimer.isRunning();
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();
	}

	public void tickDownAnimTimers() {
		landShootTimer.dec();
		landSummonTimer.dec();
		rainTridentStormTimer.dec();
		rainShootTimer.dec();
		summonTimer.dec();
		shootTimer.dec();
		tridentStormTimer.dec();
	}

	@Override
	public boolean isLeftHanded() {
		return false;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	public void handleEntityEvent(byte event) {
		if (event == 11)
			landShootTimer.reset();
		else if (event == 9)
			landSummonTimer.reset();
		else if (event == 4)
			rainTridentStormTimer.reset();
		else if (event == 8)
			rainShootTimer.reset();
		else if (event == 7)
			summonTimer.reset();
		else if (event == 6)
			shootTimer.reset();
		else if (event == 5)
			tridentStormTimer.reset();
		else
			super.handleEntityEvent(event);
	}

	protected boolean isSunSensitive() {
		return false;
	}

	@Override
	public float getVoicePitch() {
		return !this.isInWater() ? super.getVoicePitch() / 1.5F : super.getVoicePitch();
	}

	protected SoundEvent getAmbientSound() {
		return this.isInWater() ? ModSoundEvents.DROWNED_NECROMANCER_IDLE.get() : SoundEvents.DROWNED_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return this.isInWater() ? ModSoundEvents.DROWNED_NECROMANCER_HURT.get() : SoundEvents.DROWNED_HURT;
	}

	protected SoundEvent getDeathSound() {
		return this.isInWater() ? ModSoundEvents.DROWNED_NECROMANCER_DEATH.get() : SoundEvents.DROWNED_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.DROWNED_STEP;
	}

	protected SoundEvent getSwimSound() {
		return ModSoundEvents.DROWNED_NECROMANCER_SWIM.get();
	}

	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		SpawnEquipmentHelper.equipMainhand(ModItems.NECROMANCER_TRIDENT.get().getDefaultInstance(), this);
	}

	private boolean isInRain() {
		BlockPos blockpos = this.blockPosition();
		return level().isRainingAt(blockpos) || level().isRainingAt(
				new BlockPos(blockpos.getX(), (int) this.getBoundingBox().maxY, blockpos.getZ()));
	}

	static class SwimUpGoal extends Goal {
		private final DrownedNecromancerEntity drowned;
		private final double speedModifier;
		private final int seaLevel;
		private boolean stuck;

		public SwimUpGoal(DrownedNecromancerEntity p_i48908_1_, double p_i48908_2_, int p_i48908_4_) {
			this.drowned = p_i48908_1_;
			this.speedModifier = p_i48908_2_;
			this.seaLevel = p_i48908_4_;
		}

		public boolean canUse() {
			return !this.drowned.level().isDay() && this.drowned.isInWater()
					&& this.drowned.getY() < (double) (this.seaLevel - 2);
		}

		public boolean canContinueToUse() {
			return this.canUse() && !this.stuck;
		}

		public void tick() {
			if (this.drowned.getY() < (double) (this.seaLevel - 1)
					&& (this.drowned.getNavigation().isDone() || this.drowned.closeToNextPos())) {
				Vec3 vector3d = DefaultRandomPos.getPosTowards(this.drowned, 4, 8,
						new Vec3(this.drowned.getX(), this.seaLevel - 1, this.drowned.getZ()),
						(float) Math.PI / 2F);
				if (vector3d == null) {
					this.stuck = true;
					return;
				}

				this.drowned.getNavigation().moveTo(vector3d.x, vector3d.y, vector3d.z,
						this.speedModifier);
			}

		}

		public void start() {
			this.drowned.setSearchingForLand(true);
			this.stuck = false;
		}

		public void stop() {
			this.drowned.setSearchingForLand(false);
		}
	}

	static class GoToWaterGoal extends Goal {
		private final PathfinderMob mob;
		private final double speedModifier;
		private final Level level;
		private double wantedX;
		private double wantedY;
		private double wantedZ;

		public GoToWaterGoal(PathfinderMob p_i48910_1_, double p_i48910_2_) {
			this.mob = p_i48910_1_;
			this.speedModifier = p_i48910_2_;
			this.level = p_i48910_1_.level();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (!this.level.isDay()) {
				return false;
			} else if (this.mob.isInWaterOrRain()) {
				return false;
			} else {
				Vec3 vector3d = this.getWaterPos();
				if (vector3d == null) {
					return false;
				} else {
					this.wantedX = vector3d.x;
					this.wantedY = vector3d.y;
					this.wantedZ = vector3d.z;
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return !this.mob.getNavigation().isDone();
		}

		public void start() {
			this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}

		@Nullable
		private Vec3 getWaterPos() {
			RandomSource random = this.mob.getRandom();
			BlockPos blockpos = this.mob.blockPosition();

			for (int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8),
						random.nextInt(20) - 10);
				if (this.level.getBlockState(blockpos1).is(Blocks.WATER)) {
					return Vec3.atBottomCenterOf(blockpos1);
				}
			}

			return null;
		}
	}

	static class GoToBeachGoal extends MoveToBlockGoal {
		private final DrownedNecromancerEntity drowned;

		public GoToBeachGoal(DrownedNecromancerEntity p_i48911_1_, double p_i48911_2_) {
			super(p_i48911_1_, p_i48911_2_, 8, 2);
			this.drowned = p_i48911_1_;
		}

		public boolean canUse() {
			return super.canUse() && !this.drowned.level().isDay() && this.drowned.level().isRaining()
					&& this.drowned.isInWater()
					&& this.drowned.getY() >= (double) (this.drowned.level().getSeaLevel() - 7);
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse();
		}

		protected boolean isValidTarget(LevelReader p_179488_1_, BlockPos p_179488_2_) {
			BlockPos blockpos = p_179488_2_.above();
			return p_179488_1_.isEmptyBlock(blockpos) && p_179488_1_.isEmptyBlock(blockpos.above())
					&& p_179488_1_.getBlockState(p_179488_2_).entityCanStandOn(p_179488_1_,
							p_179488_2_, this.drowned);
		}

		public void start() {
			this.drowned.setSearchingForLand(false);
			this.drowned.navigation = this.drowned.groundNavigation;
			super.start();
		}

		public void stop() {
			super.stop();
		}
	}

	class LandSummonGoal extends AbstractSummonGoal<DrownedNecromancerEntity> {
		public LandSummonGoal(DrownedNecromancerEntity mob) {
			super(mob, 5, 2, 9, DungeonsMobsConfig.Common.DROWNED_NECROMANCER_MOB_SUMMONS.get(),
					EntityType.DROWNED, ModSoundEvents.NECROMANCER_PREPARE_SUMMON.get(),
					ModSoundEvents.NECROMANCER_SUMMON.get(), 2);
		}

		@Override
		protected void resetSummonTick() {
			landSummonTimer.reset();
		}

		@Override
		protected int getSummonTick() {
			return landSummonTimer.getTick();
		}

		@Override
		protected boolean tickCondition() {
			return landSummonTimer.tickEquals(landSummonAnimationActionPoint1)
					|| landSummonTimer.tickEquals(landSummonAnimationActionPoint2)
					|| landSummonTimer.tickEquals(landSummonAnimationActionPoint3)
					|| (landSummonTimer.tickEquals(landSummonAnimationActionPoint4)
							&& mob.random.nextBoolean())
					|| (landSummonTimer.tickEquals(landSummonAnimationActionPoint5)
							&& mob.random.nextBoolean());
		}

		@Override
		public boolean canUse() {
			return super.canUse() && !mob.isInWaterOrBubble();
		}
	}

	class LandShootAttackGoal extends Goal {
		public DrownedNecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public LandShootAttackGoal(DrownedNecromancerEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 12.5 && mob.hasLineOfSight(target)
					&& animationsUseable() && !mob.isInWaterRainOrBubble();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			landShootTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 11);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (target != null && landShootTimer.tickEquals(landShootAnimationActionPoint)) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0.3, 1.5, 0.5, mob.getYRot());
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
			return landShootTimer.animationsUseable();
		}

	}

	class RainTridentStormGoal extends Goal {

		public DrownedNecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public int tridentSummonRange = 15;

		public RainTridentStormGoal(DrownedNecromancerEntity mob) {
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

			return target != null && mob.tickCount >= this.nextUseTime && mob.distanceTo(target) <= 13
					&& animationsUseable()
					&& mob.hasLineOfSight(target) && !mob.isInWaterOrBubble() && mob.isInRain();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			rainTridentStormTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (target != null && rainTridentStormTimer.tickEquals(30)) {
				mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_PREPARE_TRIDENT_STORM.get(), 3.0F,
						1.0F);
			}

			if (target != null && rainTridentStormTimer.getTick() <= 30
					&& rainTridentStormTimer.getTick() >= 10 && mob.random.nextBoolean()) {
				TridentStormEntity tridentStorm = ModEntities.TRIDENT_STORM.get().create(mob.level());
				tridentStorm.owner = mob;
				tridentStorm.moveTo(new BlockPos(
						(int) mob.getX() - tridentSummonRange
								+ mob.random.nextInt(tridentSummonRange * 2),
						(int) mob.getY(), (int) mob.getZ() - tridentSummonRange
								+ mob.random.nextInt(tridentSummonRange * 2)),
						0, 0);
				tridentStorm.setYRot(mob.random.nextInt(360));
				mob.level().addFreshEntity(tridentStorm);
				PositionUtils.moveToCorrectHeight(tridentStorm);
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + (100 + mob.random.nextInt(300));
		}

		public boolean animationsUseable() {
			return rainTridentStormTimer.animationsUseable();
		}

	}

	class RainShootAttackGoal extends Goal {
		public DrownedNecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public RainShootAttackGoal(DrownedNecromancerEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 12.5 && mob.hasLineOfSight(target)
					&& animationsUseable() && !mob.isInWaterOrBubble() && mob.isInRain();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			rainShootTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 8);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (rainShootTimer.tickEquals(rainShootAnimationActionPoint)) {
				mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_SHOOT.get(), 1.25F, 1.0F);
			}

			if (target != null && (rainShootTimer.tickEquals(rainShootAnimationActionPoint)
					|| rainShootTimer.tickEquals(rainShootAnimationActionPoint - 3)
					|| rainShootTimer.tickEquals(rainShootAnimationActionPoint - 6)
					|| rainShootTimer.tickEquals(rainShootAnimationActionPoint - 9))) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0.3, 1.5, 0.5, getYRot());
				double d1 = target.getX() - pos.x;
				double d2 = target.getY(0.6D) - pos.y;
				double d3 = target.getZ() - pos.z;
				DrownedNecromancerOrbEntity necromancerOrb = new DrownedNecromancerOrbEntity(
						mob.level(),
						mob, d1 + (mob.random.nextGaussian() * 1.0),
						d2 + (mob.random.nextGaussian() * 0.25),
						d3 + (mob.random.nextGaussian() * 1.0));
				necromancerOrb.rotateToMatchMovement();
				necromancerOrb.moveTo(pos.x, pos.y, pos.z);
				mob.level().addFreshEntity(necromancerOrb);
			}
		}

		public boolean animationsUseable() {
			return rainShootTimer.animationsUseable();
		}

	}

	class SummonGoal extends AbstractSummonGoal<DrownedNecromancerEntity> {
		public SummonGoal(DrownedNecromancerEntity mob) {
			super(mob, 6, 3, 7, DungeonsMobsConfig.Common.DROWNED_NECROMANCER_MOB_SUMMONS.get(),
					EntityType.DROWNED, ModSoundEvents.DROWNED_NECROMANCER_STRONG_ATTACK.get(),
					ModSoundEvents.DROWNED_NECROMANCER_SUMMON.get(), 2);
		}

		@Override
		protected void resetSummonTick() {
			summonTimer.reset();
		}

		@Override
		protected int getSummonTick() {
			return summonTimer.getTick();
		}

		@Override
		protected boolean tickCondition() {
			return summonTimer.tickEquals(summonAnimationActionPoint);
		}

		@Override
		protected void tickBody() {
			for (int i = 0; i < 6; i++) {
				super.tickBody();
			}
		}

		@Override
		public boolean canUse() {
			return super.canUse() && mob.isInWaterOrBubble();
		}
	}

	class ShootAttackGoal extends Goal {
		public DrownedNecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public ShootAttackGoal(DrownedNecromancerEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.JUMP));
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
			return target != null && mob.tickCount >= this.nextUseTime && mob.distanceTo(target) <= 14
					&& mob.hasLineOfSight(target) && animationsUseable() && mob.isInWaterOrBubble();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			shootTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 6);
			mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_STEAM_MISSILE.get(), 1.5F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (shootTimer.tickEquals(shootAnimationActionPoint)) {
				mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_SHOOT.get(), 1.0F, 1.0F);
				mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_ATTACK.get(), 1.5F, 1.0F);
			}

			if (target != null && (shootTimer.tickEquals(shootAnimationActionPoint)
					|| shootTimer.tickEquals(shootAnimationActionPoint - 2)
					|| shootTimer.tickEquals(shootAnimationActionPoint - 4)
					|| shootTimer.tickEquals(shootAnimationActionPoint - 6)
					|| shootTimer.tickEquals(shootAnimationActionPoint - 8)
					|| shootTimer.tickEquals(shootAnimationActionPoint - 10))) {
				Vec3 pos = PositionUtils.getOffsetPos(mob, 0.3, 1.5, 0.5, getYRot());
				double d1 = target.getX() - pos.x;
				double d2 = target.getY(0.6D) - pos.y;
				double d3 = target.getZ() - pos.z;
				DrownedNecromancerOrbEntity necromancerOrb = new DrownedNecromancerOrbEntity(
						mob.level(), mob, d1 + (mob.random.nextGaussian() * 1.25),
						d2 + (mob.random.nextGaussian() * 0.5),
						d3 + (mob.random.nextGaussian() * 1.25));
				necromancerOrb.rotateToMatchMovement();
				necromancerOrb.moveTo(pos.x, pos.y, pos.z);
				mob.level().addFreshEntity(necromancerOrb);
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + 20;
		}

		public boolean animationsUseable() {
			return shootTimer.animationsUseable();
		}

	}

	class TridentStormGoal extends Goal {

		public DrownedNecromancerEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public int tridentSummonRange = 20;

		public TridentStormGoal(DrownedNecromancerEntity mob) {
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

			return target != null && mob.tickCount >= this.nextUseTime && mob.distanceTo(target) <= 15
					&& animationsUseable()
					&& mob.hasLineOfSight(target) && mob.isInWaterOrBubble();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			tridentStormTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 5);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			this.mob.getNavigation().stop();

			if (target != null && tridentStormTimer.tickEquals(30)) {
				mob.playSound(ModSoundEvents.DROWNED_NECROMANCER_PREPARE_TRIDENT_STORM.get(), 3.0F,
						1.0F);
			}

			if (target != null && tridentStormTimer.getTick() <= 30
					&& tridentStormTimer.getTick() >= 10) {
				TridentStormEntity tridentStorm = ModEntities.TRIDENT_STORM.get().create(mob.level());
				tridentStorm.owner = mob;
				tridentStorm.moveTo(new BlockPos(
						(int) mob.getX() - tridentSummonRange
								+ mob.random.nextInt(tridentSummonRange * 2),
						(int) mob.getY(), (int) mob.getZ() - tridentSummonRange
								+ mob.random.nextInt(tridentSummonRange * 2)),
						0, 0);
				tridentStorm.setYRot(mob.random.nextInt(360));
				mob.level().addFreshEntity(tridentStorm);
				PositionUtils.moveToCorrectHeight(tridentStorm);
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + (100 + mob.random.nextInt(300));
		}

		public boolean animationsUseable() {
			return tridentStormTimer.animationsUseable();
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
