package net.firefoxsalesman.dungeonsmobs.entity.water;

import org.joml.Vector3f;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.AquaticMoveHelperController;
import net.firefoxsalesman.dungeonsmobs.goals.GoToBeachGoal;
import net.firefoxsalesman.dungeonsmobs.goals.GoToWaterGoal;
import net.firefoxsalesman.dungeonsmobs.goals.SwimUpGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class SunkenSkeletonEntity extends AbstractSkeleton implements CrossbowAttackMob, IAquaticMob {
	private final RangedBowAttackGoal<SunkenSkeletonEntity> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20,
			15.0F);
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {

		@Override
		public boolean canUse() {
			return super.canUse() && okTarget(SunkenSkeletonEntity.this,
					getTarget());
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && okTarget(SunkenSkeletonEntity.this,
					getTarget());
		}
	};
	private final RangedCrossbowAttackGoal<SunkenSkeletonEntity> crossbowGoal = new RangedCrossbowAttackGoal<>(this,
			1.2D, 10.0F);

	private static final EntityDataAccessor<Boolean> CHARGING_CROSSBOW = SynchedEntityData
			.defineId(SunkenSkeletonEntity.class, EntityDataSerializers.BOOLEAN);

	private boolean searchingForLand;
	protected final WaterBoundPathNavigation waterNavigation;
	protected final GroundPathNavigation groundNavigation;
	private final boolean isConstructed;

	public SunkenSkeletonEntity(EntityType<? extends SunkenSkeletonEntity> entityType, Level world) {
		super(entityType, world);
		isConstructed = true;
		setMaxUpStep(1.0F);
		moveControl = new AquaticMoveHelperController<>(this);
		setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		waterNavigation = new WaterBoundPathNavigation(this, world);
		groundNavigation = new GroundPathNavigation(this, world);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return AbstractSkeleton.createAttributes();
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader worldReader) {
		return worldReader.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid() {
		return !isSwimming();
	}

	@Override
	public void travel(Vec3 travelVec) {
		checkAquaticTravel(this, travelVec);
	}

	@Override
	public void normalTravel(Vec3 travelVec) {
		super.travel(travelVec);
	}

	@Override
	public void updateSwimming() {
		updateNavigation(this);
	}

	@Override
	public boolean isSearchingForLand() {
		return searchingForLand;
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
	public void setSearchingForLand(boolean searchingForLand) {
		this.searchingForLand = searchingForLand;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new GoToWaterGoal(this, 1.0D));
		goalSelector.addGoal(5, new GoToBeachGoal<>(this, 1.0D));
		goalSelector.addGoal(6, new SwimUpGoal<>(this, 1.2D, level().getSeaLevel()));
		goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		targetSelector.addGoal(1, (new HurtByTargetGoal(this, SunkenSkeletonEntity.class))
				.setAlertOthers(SunkenSkeletonEntity.class));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
				target -> okTarget(this, target)));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false,
				Turtle.BABY_ON_LAND_SELECTOR));
	}

	@Override
	public void reassessWeaponGoal() {
		if (isConstructed && level() != null && !level().isClientSide) {
			goalSelector.removeGoal(meleeGoal);
			goalSelector.removeGoal(bowGoal);
			goalSelector.removeGoal(crossbowGoal);
			ItemStack bowStack = getItemInHand(
					ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
			ItemStack crossbowStack = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					item -> item instanceof CrossbowItem));
			if (bowStack.getItem() instanceof BowItem) {
				int i = 20;
				if (level().getDifficulty() != Difficulty.HARD) {
					i = 40;
				}

				bowGoal.setMinAttackInterval(i);
				goalSelector.addGoal(2, bowGoal);
			} else if (crossbowStack.getItem() instanceof CrossbowItem) {
				goalSelector.addGoal(2, crossbowGoal);
			} else {
				goalSelector.addGoal(2, meleeGoal);
			}
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(CHARGING_CROSSBOW, false);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance p_180481_1_) {
		SpawnEquipmentHelper.equipMainhand(Items.CROSSBOW.getDefaultInstance(), this);
	}

	@Override
	public void playAmbientSound() {
		if (isInWater()) {
			SoundEvent soundevent = getAmbientSound();
			if (soundevent != null) {
				playSound(soundevent, 0.5F, getVoicePitch());
			}
		} else {
			super.playAmbientSound();
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return isInWater() ? ModSoundEvents.SUNKEN_SKELETON_IDLE.get() : SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return isInWater() ? ModSoundEvents.SUNKEN_SKELETON_HURT.get() : SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return isInWater() ? ModSoundEvents.SUNKEN_SKELETON_DEATH.get() : SoundEvents.SKELETON_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return ModSoundEvents.SUNKEN_SKELETON_STEP.get();
	}

	@Override
	public void setChargingCrossbow(boolean chargingCrossbow) {
		entityData.set(CHARGING_CROSSBOW, chargingCrossbow);
	}

	@Override
	public void shootCrossbowProjectile(LivingEntity target, ItemStack crossbow, Projectile projectile,
			float inaccuracy) {
		shootCrossbowProjectile(this, target, projectile, inaccuracy, 1.6F);
	}

	@Override
	public void shootCrossbowProjectile(LivingEntity pUser, LivingEntity p_234279_2_,
			Projectile projectile, float projectileAngle, float p_234279_5_) {
		double d0 = p_234279_2_.getX() - pUser.getX();
		double d1 = p_234279_2_.getZ() - pUser.getZ();
		double d2 = Mth.sqrt((float) (d0 * d0 + d1 * d1));
		double d3 = p_234279_2_.getY(0.3333333333333333D) - projectile.getY() + d2 * (double) 0.2F;
		Vector3f vector3f = getProjectileShotVector(pUser, new Vec3(d0, d3, d1), projectileAngle);
		projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), p_234279_5_,
				(float) (14 - pUser.level().getDifficulty().getId() * 4));
		pUser.playSound(isInWater() ? ModSoundEvents.SUNKEN_SKELETON_SHOOT.get()
				: SoundEvents.CROSSBOW_SHOOT, 1.0F,
				1.0F / (pUser.getRandom().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void onCrossbowAttackPerformed() {
		noActionTime = 0;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float p_82196_2_) {
		if (isHolding(itemStack -> itemStack.getItem() instanceof BowItem)) {
			super.performRangedAttack(target, p_82196_2_);
		} else {
			performCrossbowAttack(this, 1.6F);
		}
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem shootable) {
		return shootable instanceof BowItem || shootable instanceof CrossbowItem;
	}
}
