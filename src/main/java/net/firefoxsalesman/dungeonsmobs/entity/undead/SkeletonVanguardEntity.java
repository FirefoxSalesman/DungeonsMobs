package net.firefoxsalesman.dungeonsmobs.entity.undead;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.AnimatableMeleeAttackMob;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.BasicModdedAttackGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.UseShieldGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IShieldUser;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
import java.util.UUID;

import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class SkeletonVanguardEntity extends Skeleton
		implements IShieldUser, GeoEntity, SpawnArmoredMob, AnimatableMeleeAttackMob {

	private static final UUID SPEED_MODIFIER_BLOCKING_UUID = UUID
			.fromString("e4c96392-42f5-4028-ac44-cad469c10d51");
	private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(
			SPEED_MODIFIER_BLOCKING_UUID,
			"Blocking speed decrease", -0.05D, AttributeModifier.Operation.ADDITION);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int shieldCooldownTime;

	public int attackAnimationTick;
	public int attackAnimationLength = 22;
	public int attackAnimationActionPoint = 10;

	public SkeletonVanguardEntity(Level worldIn) {
		super(ModEntities.SKELETON_VANGUARD.get(), worldIn);
	}

	public SkeletonVanguardEntity(EntityType<? extends SkeletonVanguardEntity> p_i48555_1_, Level p_i48555_2_) {
		super(p_i48555_1_, p_i48555_2_);
		this.shieldCooldownTime = 0;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new UseShieldGoal(this, 10D, 60, 120, 10, 60, true));
		this.goalSelector.addGoal(1,
				new BasicModdedAttackGoal<>(this, ModSoundEvents.SKELETON_VANGUARD_ATTACK.get(), 20));
		this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 0, 1.0D, true));
		this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false,
				Turtle.BABY_ON_LAND_SELECTOR));
	}

	@Override
	protected boolean isSunBurnTick() {
		return false;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Skeleton.createAttributes().add(Attributes.FOLLOW_RANGE, 26.0D).add(Attributes.ARMOR, 6.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.5D).add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
	}

	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		equipArmorSet(ModItems.VANGUARD_ARMOR, this);

		SpawnEquipmentHelper.equipMainhand(Items.IRON_SWORD.getDefaultInstance(), this);
		SpawnEquipmentHelper.equipOffhand(ModItems.VANGUARD_SHIELD.get().getDefaultInstance(), this);
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
		return ModSoundEvents.SKELETON_VANGUARD_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.SKELETON_VANGUARD_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.SKELETON_VANGUARD_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.SKELETON_VANGUARD_STEP.get();
	}

	@Override
	public boolean isLeftHanded() {
		return true;
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.attackAnimationTick = attackAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	public void baseTick() {
		super.baseTick();
		AttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);

		if (this.isBlocking()) {
			if (!modifiableattributeinstance.hasModifier(SPEED_MODIFIER_BLOCKING)) {
				modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_BLOCKING);
			}
		} else {
			modifiableattributeinstance.removeModifier(SPEED_MODIFIER_BLOCKING);
		}

		this.tickDownAnimTimers();
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
		if (this.attackAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("skeleton_vanguard_attack", LoopType.LOOP));
		} else if (this.isBlocking()) {
			if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
				event.getController()
						.setAnimation(RawAnimation.begin().then(
								"skeleton_vanguard_new_walk_blocking", LoopType.LOOP));
			} else {
				event.getController()
						.setAnimation(RawAnimation.begin()
								.then("skeleton_vanguard_new_blocking", LoopType.LOOP));
			}
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(
					RawAnimation.begin().then("skeleton_vanguard_new_walk", LoopType.LOOP));
		} else {
			event.getController().setAnimation(
					RawAnimation.begin().then("skeleton_vanguard_new_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	// SHIELD STUFF

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.shieldCooldownTime > 0) {
			this.shieldCooldownTime--;
		} else if (this.shieldCooldownTime < 0) {
			this.shieldCooldownTime = 0;
		}
	}

	@Override
	protected void playHurtSound(DamageSource damageSource) {
		if (this.shieldCooldownTime == 100) {
			this.playSound(SoundEvents.SHIELD_BREAK, 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
		} else if (this.isBlocking()) {
			this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
		} else {
			super.playHurtSound(damageSource);
		}
	}

	@Override
	public void blockUsingShield(LivingEntity livingEntity) {
		super.blockUsingShield(livingEntity);
		if (livingEntity.getMainHandItem().canDisableShield(this.useItem, this, livingEntity)) {
			this.disableShield(true);
		}
	}

	@Override
	protected void hurtCurrentlyUsedShield(float amount) {
		if (this.useItem.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) {
			if (amount >= 3.0F) {
				int i = 1 + Mth.floor(amount);
				InteractionHand hand = this.getUsedItemHand();
				this.useItem.hurtAndBreak(i, this, (skeletonVanguardEntity) -> {
					skeletonVanguardEntity.broadcastBreakEvent(hand);
					// Forge would have called onPlayerDestroyItem here
				});
				if (this.useItem.isEmpty()) {
					if (hand == InteractionHand.MAIN_HAND) {
						this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
					}

					this.useItem = ItemStack.EMPTY;
					this.playSound(SoundEvents.SHIELD_BREAK, 1.0F,
							0.8F + this.level().random.nextFloat() * 0.4F);
				}
			}
		}
	}

	@Override
	public int getShieldCooldownTime() {
		return this.shieldCooldownTime;
	}

	@Override
	public void setShieldCooldownTime(int shieldCooldownTime) {
		this.shieldCooldownTime = shieldCooldownTime;
	}

	@Override
	public void disableShield(boolean guaranteeDisable) {
		float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
		if (guaranteeDisable) {
			f += 0.75F;
		}
		if (this.random.nextFloat() < f) {
			this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
			this.shieldCooldownTime = 100;
			this.stopUsingItem();
			this.level().broadcastEntityEvent(this, (byte) 30);
		}
	}

	@Override
	public boolean isShieldDisabled() {
		return this.shieldCooldownTime > 0;
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.VANGUARD_ARMOR;
	}

}
