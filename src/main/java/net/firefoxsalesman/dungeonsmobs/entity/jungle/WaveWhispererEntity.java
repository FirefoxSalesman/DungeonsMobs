package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AbstractTrapEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.AquaticMoveHelperController;
import net.firefoxsalesman.dungeonsmobs.goals.GoToWaterGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.SwimUpGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class WaveWhispererEntity extends AbstractWhispererEntity implements IAquaticMob {

	public WaveWhispererEntity(EntityType<? extends WaveWhispererEntity> type, Level world) {
		super(type, world);
		if (isWavewhisperer()) {
			moveControl = new AquaticMoveHelperController<>(this);
			setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		}
		waterNavigation = new WaterBoundPathNavigation(this, world);
		groundNavigation = new GroundPathNavigation(this, world);
	}

	protected final GroundPathNavigation groundNavigation;
	protected final WaterBoundPathNavigation waterNavigation;

	@Override
	public boolean isSearchingForLand() {
		return false;
	}

	@Override
	public void normalTravel(Vec3 travelVec) {
		super.travel(travelVec);
	}

	@Override
	public void setNavigation(PathNavigation navigation) {
		this.navigation = navigation;
	}

	@Override
	public GroundPathNavigation getGroundNavigation() {
		return groundNavigation;
	}

	@Override
	public WaterBoundPathNavigation getWaterNavigation() {
		return waterNavigation;
	}

	@Override
	protected AbstractTrapEntity getTrap() {
		return ModEntities.KELP_TRAP.get().create(level());
	}

	@Override
	protected int getBasicAttackActionPoint() {
		return underwaterAttackAnimationActionPoint;
	}

	@Override
	protected int getGrappleActionPoint() {
		return underwaterGrappleAnimationActionPoint;
	}

	@Override
	protected SoundEvent getGrappleSound() {
		return ModSoundEvents.WAVEWHISPERER_GRAPPLE.get();
	}

	@Override
	protected SoundEvent getSummonQGSound() {
		return ModSoundEvents.WAVEWHISPERER_SUMMON_QGK.get();
	}

	@Override
	protected SoundEvent getSummonPoisonFoleySound() {
		return ModSoundEvents.WAVEWHISPERER_SUMMON_PA_FOLEY.get();
	}

	@Override
	protected SoundEvent getSummonPoisonVocalSound() {
		return ModSoundEvents.WAVEWHISPERER_SUMMON_PA_VOCAL.get();
	}

	@Override
	protected SoundEvent getAttackSound() {
		return ModSoundEvents.WAVEWHISPERER_ATTACK.get();
	}

	@Override
	protected EntityType<? extends AbstractVineEntity> getOffensiveVine() {
		return ModEntities.POISON_ANEMONE.get();
	}

	@Override
	protected EntityType<? extends AbstractVineEntity> getAreaDenialVine() {
		return ModEntities.QUICK_GROWING_KELP.get();
	}

	@Override
	public boolean isWavewhisperer() {
		return true;
	}

	@Override
	public boolean isInWrongHabitat() {
		return !isInWaterOrBubble();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WAVEWHISPERER_IDLE.get();
	}

	@Override
	protected SoundEvent getAmbientSoundFoley() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return ModSoundEvents.WAVEWHISPERER_HURT.get();
	}

	@Override
	protected SoundEvent getHurtSoundFoley(DamageSource pDamageSource) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WAVEWHISPERER_DEATH.get();
	}

	@Override
	protected SoundEvent getSwimSound() {
		return ModSoundEvents.WAVEWHISPERER_STEP.get();
	}

	@Override
	protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (attackAnimationTick > 0) {
			if (isEyeInFluid(FluidTags.WATER)) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_attack", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_attack", LoopType.LOOP));
			}
		} else if (summonPQVAnimationTick > 0) {
			if (isEyeInFluid(FluidTags.WATER)) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_summon_pa", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_summon_pqv", LoopType.LOOP));
			}
		} else if (summonQGVAnimationTick > 0) {
			if (isEyeInFluid(FluidTags.WATER)) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_summon_qgk", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_summon_qgv", LoopType.LOOP));
			}
		} else if (grappleAnimationTick > 0) {
			if (isEyeInFluid(FluidTags.WATER)) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_grapple", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_grapple", LoopType.LOOP));
			}
		} else if (isClimbing()) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("whisperer_climb", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			if (isEyeInFluid(FluidTags.WATER)) {
				event.getController().setAnimation(
						RawAnimation.begin().then("wavewhisperer_swim", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("whisperer_walk", LoopType.LOOP));
			}
		} else {
			if (isInWater()) {
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
	public void setSearchingForLand(boolean searchingForLand) {
	}

	@Override
	public void registerGoals() {
		goalSelector.addGoal(1, new GoToWaterGoal(this, 1.25D) {
			public boolean canUse() {
				if (mob.isInWater()) {
					return false;
				} else {
					Vec3 vector3d = getWaterPos();
					if (vector3d == null) {
						return false;
					} else {
						wantedX = vector3d.x;
						wantedY = vector3d.y;
						wantedZ = vector3d.z;
						return true;
					}
				}
			}
		});
		goalSelector.addGoal(1, new AbstractWhispererEntity.BasicAttackGoal(this));
		goalSelector.addGoal(2, new AbstractWhispererEntity.GrappleGoal(this));
		goalSelector.addGoal(3, new AbstractWhispererEntity.SummonPQVAttackGoal(this));
		goalSelector.addGoal(4, new AbstractWhispererEntity.SummonQGVGoal(this));
		goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolem.class, 4.0F, 1.0D, 1.0D));
		goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.0D, 1.0D));
		goalSelector.addGoal(6, new ApproachTargetGoal(this, 7, 1.1D, true));
		goalSelector.addGoal(7, new LookAtTargetGoal(this));
		goalSelector.addGoal(8, new SwimUpGoal<>(this, 1.2D, level().getSeaLevel()));
		goalSelector.addGoal(9, new RandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(11, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	protected int decreaseAirSupply(int p_70682_1_) {
		return p_70682_1_;
	}

	@Override
	public boolean isPushedByFluid() {
		return !isSwimming();
	}

	@Override
	public void travel(Vec3 pTravelVector) {
		if (isEffectiveAi() && isInWater() && wantsToSwim(this)) {
			moveRelative(0.01F, pTravelVector);
			move(MoverType.SELF, getDeltaMovement());
			setDeltaMovement(getDeltaMovement().scale(0.9D));
		} else {
			normalTravel(pTravelVector);
		}

	}

	@Override
	public void updateSwimming() {
		updateNavigation(this);
	}

	@Override
	public <T extends Mob & IAquaticMob> boolean wantsToSwim(T aquaticMob) {
		return getTarget() != null;
	}

	@Override
	public <T extends LivingEntity & IAquaticMob> boolean okTarget(T aquaticMob, LivingEntity target) {
		return true;
	}

	public boolean canUseGoals() {
		return isInWaterOrBubble();
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel) {
		return pLevel.isUnobstructed(this);
	}

	@Override
	public boolean onClimbable() {
		return false;
	}
}
