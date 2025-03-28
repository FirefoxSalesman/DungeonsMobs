package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.AnimatableMeleeAttackMob;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.BasicModdedAttackGoal;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
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
import java.util.Map;

public class MountaineerEntity extends Vindicator implements SpawnArmoredMob, GeoEntity, AnimatableMeleeAttackMob {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData
			.defineId(MountaineerEntity.class, EntityDataSerializers.BYTE);
	public int attackAnimationTick;
	public int attackAnimationLength = 7;
	public int attackAnimationActionPoint = 6;

	public MountaineerEntity(Level worldIn) {
		super(ModEntities.MOUNTAINEER.get(), worldIn);
	}

	public MountaineerEntity(EntityType<? extends MountaineerEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		GoalUtils.removeGoal(this.goalSelector, MeleeAttackGoal.class);
		this.goalSelector.addGoal(4, new BasicModdedAttackGoal<>(this, null, 20));
		this.goalSelector.addGoal(5, new ApproachTargetGoal(this, 0, 1.0D, true));
	}

	protected PathNavigation createNavigation(Level p_175447_1_) {
		return new WallClimberNavigation(this, p_175447_1_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}

	}

	public boolean onClimbable() {
		return this.isClimbing();
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
		SpawnEquipmentHelper.equipArmorSet(ModItems.MOUNTAINEER_ARMOR, this);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_,
			@Nullable CompoundTag p_213386_5_) {
		SpawnGroupData iLivingEntityData = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_,
				p_213386_4_,
				p_213386_5_);
		this.populateDefaultEquipmentSlots(this.getRandom(), p_213386_2_);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), p_213386_2_);
		return iLivingEntityData;
	}

	public void applyRaidBuffs(int waveNumber, boolean bool) {
		ItemStack itemStack = new ItemStack(ModItems.MOUNTAINEER_AXE.get());
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (raid != null && waveNumber > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = false;
		if (raid != null) {
			flag = this.random.nextFloat() <= raid.getEnchantOdds();
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

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.MOUNTAINEER_ARMOR;
	}

	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.attackAnimationTick = attackAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	@Override
	public int getAttackAnimationTick() {
		return attackAnimationTick;
	}

	@Override
	public void setAttackAnimationTick(int attackAnimationTick) {
		this.attackAnimationTick = attackAnimationTick;
	}

	@Override
	public int getAttackAnimationLength() {
		return attackAnimationLength;
	}

	@Override
	public int getAttackAnimationActionPoint() {
		return attackAnimationActionPoint;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();
	}

	public void tickDownAnimTimers() {
		if (this.attackAnimationTick > 0) {
			this.attackAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		String animation = "animation.vindicator";
		// if (false) {
		// animation += "_mcd";
		// }
		String handSide = "_right";
		if (this.isLeftHanded()) {
			handSide = "_left";
		}
		if (this.getMainHandItem().isEmpty()) {
			handSide += "_both";
		}
		String crossed = "";
		if (IllagerArmsUtil.armorHasCrossedArms(this, this.getItemBySlot(EquipmentSlot.CHEST))) {
			crossed = "_crossed";
		}
		if (this.attackAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then(animation + ".attack" + handSide, LoopType.LOOP));
		} else if (this.isAggressive()
				&& !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin()
					.then(animation + ".run" + handSide, LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin()
					.then(animation + ".walk" + crossed, LoopType.LOOP));
		} else {
			if (this.isCelebrating()) {
				event.getController().setAnimation(
						RawAnimation.begin().then(animation + ".win", LoopType.LOOP));
			} else {
				event.getController().setAnimation(RawAnimation.begin()
						.then(animation + ".idle" + crossed, LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
