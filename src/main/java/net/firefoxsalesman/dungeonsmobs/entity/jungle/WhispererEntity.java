package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.AquaticMoveHelperController;
import net.firefoxsalesman.dungeonsmobs.goals.GoToWaterGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.SwimUpGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.firefoxsalesman.dungeonsmobs.utils.GeomancyHelper;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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

import javax.annotation.Nullable;
import java.util.EnumSet;

public class WhispererEntity extends Monster implements GeoEntity, IAquaticMob {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(WhispererEntity.class,
			EntityDataSerializers.BYTE);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int summonQGVAnimationTick;
	public int summonQGVAnimationLength = 40;
	public int summonQGVAnimationActionPoint = 20;

	public int summonPQVAnimationTick;
	public int summonPQVAnimationLength = 70;
	public int summonPQVAnimationActionPoint1 = 53;
	public int summonPQVAnimationActionPoint2 = 30;

	public int grappleAnimationTick;
	public int grappleAnimationLength = 65;
	public int grappleAnimationActionPoint = 23;

	public int underwaterGrappleAnimationActionPoint = 20;

	public int attackAnimationTick;
	public int attackAnimationLength = 30;
	public int attackAnimationActionPoint = 18;

	public int underwaterAttackAnimationActionPoint = 16;

	protected final WaterBoundPathNavigation waterNavigation;
	protected final GroundPathNavigation groundNavigation;
	protected final GroundPathNavigation climberNavigation;

	public WhispererEntity(EntityType<? extends WhispererEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.0F);
		if (this.isWavewhisperer()) {
			this.moveControl = new AquaticMoveHelperController<>(this);
			this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		}
		this.waterNavigation = new WaterBoundPathNavigation(this, world);
		this.groundNavigation = new GroundPathNavigation(this, world);
		this.climberNavigation = new WallClimberNavigation(this, world);
		if (!this.isWavewhisperer()) {
			// this.setNavigation(this.climberNavigation);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		if (this.isWavewhisperer()) {
			this.registerWavewhispererGoals();
		} else {
			this.registerWhispererGoals();
		}
	}

	public void registerWhispererGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new WhispererEntity.BasicAttackGoal(this));
		this.goalSelector.addGoal(2, new WhispererEntity.GrappleGoal(this));
		this.goalSelector.addGoal(3, new WhispererEntity.SummonPQVAttackGoal(this));
		this.goalSelector.addGoal(4, new WhispererEntity.SummonQGVGoal(this));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolem.class, 4.0F, 1.0D, 1.0D));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.0D, 1.0D));
		this.goalSelector.addGoal(6, new ApproachTargetGoal(this, 7, 1.1D, true));
		this.goalSelector.addGoal(7, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public boolean isSpellcasting() {
		return this.summonPQVAnimationTick > 0 || this.summonQGVAnimationTick > 0
				|| this.grappleAnimationTick > 0;
	}

	public void registerWavewhispererGoals() {
		this.goalSelector.addGoal(1, new GoToWaterGoal(this, 1.25D) {
			public boolean canUse() {
				if (this.mob.isInWater()) {
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
		});
		this.goalSelector.addGoal(1, new WhispererEntity.BasicAttackGoal(this));
		this.goalSelector.addGoal(2, new WhispererEntity.GrappleGoal(this));
		this.goalSelector.addGoal(3, new WhispererEntity.SummonPQVAttackGoal(this));
		this.goalSelector.addGoal(4, new WhispererEntity.SummonQGVGoal(this));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolem.class, 4.0F, 1.0D, 1.0D));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.0D, 1.0D));
		this.goalSelector.addGoal(6, new ApproachTargetGoal(this, 7, 1.1D, true));
		this.goalSelector.addGoal(7, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(8, new SwimUpGoal<>(this, 1.2D, this.level().getSeaLevel()));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.isWavewhisperer() && !this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}

		if (this.isClimbing()) {
			this.yBodyRot = this.yHeadRot;
		}
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel) {
		if (this.isWavewhisperer()) {
			return pLevel.isUnobstructed(this);
		}
		return super.checkSpawnObstruction(pLevel);
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean p_70839_1_) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (p_70839_1_) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.entityData.set(DATA_FLAGS_ID, b0);
	}

	public boolean onClimbable() {
		return !this.isWavewhisperer() && this.isClimbing();
	}

	public boolean isWavewhisperer() {
		return this.getType() == ModEntities.WAVEWHISPERER.get();
	}

	public boolean isInWrongHabitat() {
		return this.isWavewhisperer() && !this.isInWaterOrBubble();
	}

	protected int decreaseAirSupply(int p_70682_1_) {
		return this.isWavewhisperer() ? p_70682_1_ : super.decreaseAirSupply(p_70682_1_);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.FOLLOW_RANGE, 17.5D).add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.ARMOR, 5.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundeventVocal = this.getAmbientSound();
		SoundEvent soundeventFoley = this.getAmbientSoundFoley();
		this.playSound(soundeventVocal, soundeventFoley, this.getSoundVolume(), this.getVoicePitch(),
				this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	protected void playHurtSound(DamageSource p_184581_1_) {
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		SoundEvent soundeventVocal = this.getHurtSound(p_184581_1_);
		SoundEvent soundeventFoley = this.getHurtSoundFoley(p_184581_1_);
		this.playSound(soundeventVocal, soundeventFoley, this.getSoundVolume(), this.getVoicePitch(),
				this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(this.getStepSound(), 0.5F, 1.0F);
		this.playSound(this.getStepSoundFoley(), 0.5F, 1.0F);
	}

	public void playSound(SoundEvent vocalSound, SoundEvent foleySound, float vocalVolume, float vocalPitch,
			float foleyVolume, float foleyPitch) {
		if (!this.isSilent()) {
			if (vocalSound != null) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), vocalSound,
						this.getSoundSource(), vocalVolume, vocalPitch);
			}
			if (foleySound != null) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), foleySound,
						this.getSoundSource(), foleyVolume, foleyPitch);
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_IDLE.get()
				: ModSoundEvents.WHISPERER_IDLE_VOCAL.get();
	}

	protected SoundEvent getAmbientSoundFoley() {
		return this.isWavewhisperer() ? null : ModSoundEvents.WHISPERER_IDLE_FOLEY.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return 150;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return this.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_HURT.get()
				: ModSoundEvents.WHISPERER_HURT_VOCAL.get();
	}

	protected SoundEvent getHurtSoundFoley(DamageSource p_184601_1_) {
		return this.isWavewhisperer() ? null : ModSoundEvents.WHISPERER_HURT_FOLEY.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_DEATH.get()
				: ModSoundEvents.WHISPERER_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.WHISPERER_STEP_VOCAL.get();
	}

	protected SoundEvent getStepSoundFoley() {
		return ModSoundEvents.WHISPERER_STEP_FOLEY.get();
	}

	@Override
	protected void playSwimSound(float p_203006_1_) {
		this.playSound(this.getSwimSound(), 1.0F,
				1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
	}

	@Override
	public void playSound(SoundEvent p_184185_1_, float p_184185_2_, float p_184185_3_) {
		if (p_184185_1_ == this.getDeathSound()) {
			super.playSound(p_184185_1_, p_184185_2_, p_184185_3_ / 2);
			super.playSound(ModSoundEvents.WHISPERER_STEP_FOLEY.get(), p_184185_2_, p_184185_3_);
			super.playSound(ModSoundEvents.WHISPERER_HURT_FOLEY.get(), p_184185_2_, p_184185_3_);
		} else {
			super.playSound(p_184185_1_, p_184185_2_, p_184185_3_);
		}
	}

	@Override
	protected SoundEvent getSwimSound() {
		return this.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_STEP.get() : null;
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();

		if (!this.level().isClientSide) {
			if (this.isInWrongHabitat() && this.random.nextInt(200) == 0) {
				this.kill();
			}
		}
	}

	public void tickDownAnimTimers() {
		if (this.summonQGVAnimationTick > 0) {
			this.summonQGVAnimationTick--;
		}

		if (this.summonPQVAnimationTick > 0) {
			this.summonPQVAnimationTick--;
		}

		if (this.grappleAnimationTick > 0) {
			this.grappleAnimationTick--;
		}

		if (this.attackAnimationTick > 0) {
			this.attackAnimationTick--;
		}
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 4) {
			this.summonQGVAnimationTick = this.summonQGVAnimationLength;
		} else if (p_70103_1_ == 5) {
			this.summonPQVAnimationTick = this.summonPQVAnimationLength;
		} else if (p_70103_1_ == 6) {
			this.grappleAnimationTick = this.grappleAnimationLength;
		} else if (p_70103_1_ == 7) {
			this.attackAnimationTick = this.attackAnimationLength;
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_, DamageSource p_147189_) {
		return false;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.attackAnimationTick > 0) {
			if (this.isEyeInFluid(FluidTags.WATER) && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_attack", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_attack", LoopType.LOOP));
			}
		} else if (this.summonPQVAnimationTick > 0) {
			if (this.isEyeInFluid(FluidTags.WATER) && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_summon_pa", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_summon_pqv", LoopType.LOOP));
			}
		} else if (this.summonQGVAnimationTick > 0) {
			if (this.isEyeInFluid(FluidTags.WATER) && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_summon_qgk", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_summon_qgv", LoopType.LOOP));
			}
		} else if (this.grappleAnimationTick > 0) {
			if (this.isEyeInFluid(FluidTags.WATER) && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_grapple", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_grapple", LoopType.LOOP));
			}
		} else if (this.isClimbing()) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("whisperer_climb", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			if (this.isEyeInFluid(FluidTags.WATER) && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_swim", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_walk", LoopType.LOOP));
			}
		} else {
			if (this.isInWater() && this.isWavewhisperer()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_idle", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_idle", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public boolean isAlliedTo(Entity entity) {
		if (super.isAlliedTo(entity)) {
			return true;
		} else if (entity instanceof LivingEntity && entity.getType().is(EntityTags.PLANT_MOBS)) {
			return this.getTeam() == null && entity.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public boolean isPushedByFluid() {
		return this.isWavewhisperer() ? !this.isSwimming() : super.isPushedByFluid();
	}

	@Override
	public void travel(Vec3 travelVec) {
		if (this.isWavewhisperer()) {
			this.checkAquaticTravel(this, travelVec);
		} else {
			this.normalTravel(travelVec);
		}
	}

	@Override
	public void normalTravel(Vec3 travelVec) {
		super.travel(travelVec);
	}

	@Override
	public void updateSwimming() {
		if (this.isWavewhisperer()) {
			this.updateNavigation(this);
		} else {
			super.updateSwimming();
		}
	}

	@Override
	public boolean isSearchingForLand() {
		return false;
	}

	@Override
	public void setNavigation(PathNavigation navigation) {
		this.navigation = navigation;
	}

	@Override
	public GroundPathNavigation getGroundNavigation() {
		return this.groundNavigation;
	}

	@Override
	public WaterBoundPathNavigation getWaterNavigation() {
		return this.waterNavigation;
	}

	@Override
	public void setSearchingForLand(boolean searchingForLand) {

	}

	@Override
	public <T extends Mob & IAquaticMob> boolean wantsToSwim(T aquaticMob) {
		return this.isWavewhisperer() && this.getTarget() != null;
	}

	@Override
	public <T extends LivingEntity & IAquaticMob> boolean okTarget(T aquaticMob, LivingEntity target) {
		return true;
	}

	public boolean canUseGoals() {
		return (this.isWavewhisperer() && this.isInWaterOrBubble()) || !this.isWavewhisperer();
	}

	class BasicAttackGoal extends Goal {

		public WhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public BasicAttackGoal(WhispererEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 3 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.random.nextInt(20) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.attackAnimationTick = mob.attackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 7);
			mob.playSound(ModSoundEvents.WHISPERER_ATTACK_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			int actionPoint = mob.isWavewhisperer() ? mob.underwaterAttackAnimationActionPoint
					: mob.attackAnimationActionPoint;

			if (mob.attackAnimationTick == mob.attackAnimationActionPoint + 5) {
				mob.playSound(mob.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_ATTACK.get()
						: ModSoundEvents.WHISPERER_ATTACK_VOCAL.get(), 1.25F, 1.0F);
			}

			if (target != null && mob.distanceTo(target) < 3.5
					&& mob.attackAnimationTick == actionPoint) {
				mob.doHurtTarget(target);
			}
		}

		public boolean animationsUseable() {
			return mob.attackAnimationTick <= 0;
		}

	}

	class SummonQGVGoal extends Goal {

		public WhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public SummonQGVGoal(WhispererEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 15 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= this.nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.summonQGVAnimationTick = mob.summonQGVAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
			mob.playSound(ModSoundEvents.WHISPERER_SUMMON_QGV_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.summonQGVAnimationTick == mob.summonQGVAnimationLength - 5) {
				mob.playSound(mob.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_SUMMON_QGK.get()
						: ModSoundEvents.WHISPERER_SUMMON_QGV_VOCAL.get(), 1.25F, 1.0F);
			}

			if (target != null
					&& mob.summonQGVAnimationTick == mob.summonQGVAnimationActionPoint) {
				int[] rowToRemove = Util.getRandom(GeomancyHelper.CONFIG_1_ROWS,
						WhispererEntity.this.getRandom());
				GeomancyHelper.summonAreaDenialVineTrap(mob, target,
						mob.isWavewhisperer() ? ModEntities.QUICK_GROWING_KELP.get()
								: ModEntities.QUICK_GROWING_VINE.get(),
						rowToRemove);
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + (150 + mob.random.nextInt(50));
		}

		public boolean animationsUseable() {
			return mob.summonQGVAnimationTick <= 0;
		}

	}

	class SummonPQVAttackGoal extends Goal {

		public WhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public SummonPQVAttackGoal(WhispererEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 12.5 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= this.nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.summonPQVAnimationTick = mob.summonPQVAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 5);
			mob.playSound(mob.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_SUMMON_PA_FOLEY.get()
					: ModSoundEvents.WHISPERER_SUMMON_PQV_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.summonPQVAnimationTick == mob.summonPQVAnimationLength - 12) {
				mob.playSound(mob.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_SUMMON_PA_VOCAL.get()
						: ModSoundEvents.WHISPERER_SUMMON_PQV_VOCAL.get(), 1.25F, 1.0F);
			}

			if (target != null
					&& (mob.summonPQVAnimationTick == mob.summonPQVAnimationActionPoint1
							|| mob.summonPQVAnimationTick == mob.summonPQVAnimationActionPoint2)) {
				boolean movingOnX = mob.random.nextBoolean();
				GeomancyHelper.summonOffensiveVine(mob, mob,
						mob.isWavewhisperer() ? ModEntities.POISON_ANEMONE.get()
								: ModEntities.POISON_QUILL_VINE.get(),
						movingOnX ? (mob.random.nextBoolean() ? 3 : -3) : 0,
						!movingOnX ? (mob.random.nextBoolean() ? 3 : -3) : 0);
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + (350 + mob.random.nextInt(150));
		}

		public boolean animationsUseable() {
			return mob.summonPQVAnimationTick <= 0;
		}

	}

	class GrappleGoal extends Goal {

		public WhispererEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime;

		public GrappleGoal(WhispererEntity mob) {
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
			return target != null && mob.distanceTo(target) <= 15 && animationsUseable()
					&& mob.hasLineOfSight(target) && mob.canUseGoals()
					&& mob.tickCount >= this.nextUseTime;
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.grappleAnimationTick = mob.grappleAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 6);
			mob.playSound(ModSoundEvents.WHISPERER_GRAPPLE_FOLEY.get(), 1.0F, 1.0F);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (mob.grappleAnimationTick == mob.grappleAnimationLength - 30) {
				mob.playSound(mob.isWavewhisperer() ? ModSoundEvents.WAVEWHISPERER_GRAPPLE.get()
						: ModSoundEvents.WHISPERER_GRAPPLE_VOCAL.get(), 1.25F, 1.0F);
			}

			int actionPoint = mob.isWavewhisperer() ? mob.underwaterGrappleAnimationActionPoint
					: mob.grappleAnimationActionPoint;

			if (target != null && mob.grappleAnimationTick == actionPoint) {
				if (mob.isWavewhisperer()) {
					KelpTrapEntity trap = ModEntities.KELP_TRAP.get().create(mob.level());
					trap.moveTo(target.getX(), target.getY(), target.getZ());
					PositionUtils.moveToCorrectHeight(trap);
					trap.owner = mob;
					mob.level().addFreshEntity(trap);
				} else {
					SimpleTrapEntity trap = ModEntities.SIMPLE_TRAP.get().create(mob.level());
					trap.moveTo(target.getX(), target.getY(), target.getZ());
					trap.setTrapType(1);
					PositionUtils.moveToCorrectHeight(trap);
					trap.owner = mob;
					mob.level().addFreshEntity(trap);
				}
			}
		}

		@Override
		public void stop() {
			this.nextUseTime = mob.tickCount + (200 + mob.random.nextInt(400));
		}

		public boolean animationsUseable() {
			return mob.grappleAnimationTick <= 0;
		}

	}
}
