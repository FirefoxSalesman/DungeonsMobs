package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.AnimatableMeleeAttackMob;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.BasicModdedAttackGoal;
import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;
import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
import net.firefoxsalesman.dungeonsmobs.lib.utils.GoalUtils;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import javax.annotation.Nullable;
import java.util.Map;

public class MountaineerEntity extends Vindicator implements AnimatableMeleeAttackMob, KeyframeEntity {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData
			.defineId(MountaineerEntity.class, EntityDataSerializers.BYTE);
	private final AnimationTimer attackTimer = new AnimationTimer(7);
	private static final int attackAnimationActionPoint = 6;
	private final Map<String, AnimationState> states;

	public MountaineerEntity(Level worldIn) {
		this(ModEntities.MOUNTAINEER.get(), worldIn);
	}

	public MountaineerEntity(EntityType<? extends MountaineerEntity> entityType, Level world) {
		super(entityType, world);
		states = genStates("attack", "run", "walk", "celebrate", "idle");
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		GoalUtils.removeGoal(goalSelector, MeleeAttackGoal.class);
		goalSelector.addGoal(4, new BasicModdedAttackGoal<>(this, null, 20));
		goalSelector.addGoal(5, new ApproachTargetGoal(this, 0, 1.0D, true));
	}

	protected PathNavigation createNavigation(Level world) {
		return new WallClimberNavigation(this, world);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			setClimbing(horizontalCollision);
		}
		setupAnimationStates();
	}

	private void setupAnimationStates() {
		getState("attack").animateWhen(attackTimer.isRunning(), tickCount);
		getState("run").animateWhen(!attackTimer.isRunning() && isAggressive() && isMoving(), tickCount);
		getState("walk").animateWhen(!attackTimer.isRunning() && !isAggressive() && isMoving(), tickCount);
		getState("celebrate").animateWhen(!attackTimer.isRunning() && !isMoving() && isCelebrating(),
				tickCount);
		getState("idle").animateWhen(!attackTimer.isRunning() && !isMoving() && !isCelebrating(), tickCount);
	}

	public boolean onClimbable() {
		return isClimbing();
	}

	public boolean isClimbing() {
		return (entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean p_70839_1_) {
		byte b0 = entityData.get(DATA_FLAGS_ID);
		if (p_70839_1_) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		entityData.set(DATA_FLAGS_ID, b0);
	}

	protected float getSoundVolume() {
		return 0.5F;
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.MOUNTAINEER_IDLE.get();
	}

	public SoundEvent getCelebrateSound() {
		return ModSoundEvents.MOUNTAINEER_IDLE.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.MOUNTAINEER_DEATH.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.MOUNTAINEER_HURT.get();
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 28.0D)
				.add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		SpawnEquipmentHelper.equipMainhand(ModItems.MOUNTAINEER_AXE.get().getDefaultInstance(), this);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty,
			MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		SpawnGroupData iLivingEntityData = super.finalizeSpawn(accessor, difficulty, spawnType, groupData, tag);
		populateDefaultEquipmentSlots(getRandom(), difficulty);
		populateDefaultEquipmentEnchantments(getRandom(), difficulty);
		return iLivingEntityData;
	}

	public void applyRaidBuffs(int waveNumber, boolean bool) {
		ItemStack itemStack = new ItemStack(ModItems.MOUNTAINEER_AXE.get());
		Raid raid = getCurrentRaid();
		int i = 1;
		if (raid != null && waveNumber > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = false;
		if (raid != null) {
			flag = random.nextFloat() <= raid.getEnchantOdds();
		}
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.setEnchantments(map, itemStack);
		}

		SpawnEquipmentHelper.equipMainhand(itemStack, this);
	}

	@Override
	public IllagerArmPose getArmPose() {
		IllagerArmPose illagerArmPose = super.getArmPose();
		if (illagerArmPose == IllagerArmPose.CROSSED) {
			return IllagerArmPose.NEUTRAL;
		}
		return illagerArmPose;
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	public void handleEntityEvent(byte event) {
		if (event == 4) {
			attackTimer.reset();
		} else {
			super.handleEntityEvent(event);
		}
	}

	@Override
	public int getAttackAnimationActionPoint() {
		return attackAnimationActionPoint;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		attackTimer.dec();
	}

	@Override
	public Map<String, AnimationState> getStates() {
		return states;
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}

	@Override
	public AnimationTimer getTimer() {
		return attackTimer;
	}
}
