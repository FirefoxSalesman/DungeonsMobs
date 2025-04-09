package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.UseShieldGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IShieldUser;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
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
import java.util.Map;
import java.util.UUID;

import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class RoyalGuardEntity extends AbstractIllager implements GeoEntity, IShieldUser, SpawnArmoredMob {

	private static final UUID SPEED_MODIFIER_BLOCKING_UUID = UUID
			.fromString("05cd371b-0ff4-4ded-8630-b380232ed7b1");
	private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(
			SPEED_MODIFIER_BLOCKING_UUID,
			"Blocking speed decrease", -0.1D, AttributeModifier.Operation.ADDITION);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int shieldCooldownTime;

	public int attackAnimationTick;
	public int attackAnimationLength = 27;
	public int attackAnimationActionPoint = 15;

	public RoyalGuardEntity(Level world) {
		super(ModEntities.ROYAL_GUARD.get(), world);
		this.shieldCooldownTime = 0;
	}

	public RoyalGuardEntity(EntityType<? extends RoyalGuardEntity> p_i50189_1_, Level p_i50189_2_) {
		super(p_i50189_1_, p_i50189_2_);
		this.shieldCooldownTime = 0;
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new UseShieldGoal(this, 7.5D, 60, 160, 15, 100, false));
		this.goalSelector.addGoal(1, new RoyalGuardEntity.BasicAttackGoal(this));
		this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 0, 1.0D, true));
		this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(1, new AbstractIllager.RaiderOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_,
			@Nullable CompoundTag p_213386_5_) {
		SpawnGroupData ilivingentitydata = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_,
				p_213386_4_,
				p_213386_5_);
		((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
		this.populateDefaultEquipmentSlots(this.getRandom(), p_213386_2_);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), p_213386_2_);
		return ilivingentitydata;
	}

	@Override
	public boolean isLeftHanded() {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATOR_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.VINDICATOR_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.VINDICATOR_HURT;
	}

	public SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
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
			event.getController()
					.setAnimation(RawAnimation.begin().then("royal_guard_attack", LoopType.LOOP));
		} else if (this.isBlocking()) {
			if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
				event.getController().setAnimation(RawAnimation.begin()
						.then("royal_guard_new_walk_blocking", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("royal_guard_new_blocking", LoopType.LOOP));
			}
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(
					RawAnimation.begin().then("royal_guard_new_walk", LoopType.LOOP));
		} else {
			if (this.isCelebrating()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("royal_guard_celebrate", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("royal_guard_new_idle", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(ModSoundEvents.ROYAL_GUARD_STEP.get(), 0.5F, 1.0F);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Vindicator.createAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
				.add(Attributes.MOVEMENT_SPEED, 0.325F).add(Attributes.FOLLOW_RANGE, 18.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		equipArmorSet(ModItems.ROYAL_GUARD_ARMOR, this);

		if (ModList.get().isLoaded("dungeons_gear")) {
			Item MACE = ForgeRegistries.ITEMS.getValue(new ResourceLocation("dungeons_gear", "mace"));

			ItemStack mace = new ItemStack(MACE);
			if (this.getCurrentRaid() == null) {
				SpawnEquipmentHelper.equipMainhand(mace, this);
			}
		} else {
			if (this.getCurrentRaid() == null) {
				SpawnEquipmentHelper.equipMainhand(Items.IRON_AXE.getDefaultInstance(), this);
			}
		}
		SpawnEquipmentHelper.equipOffhand(ModItems.ROYAL_GUARD_SHIELD.get().getDefaultInstance(), this);
	}

	@Override
	public void applyRaidBuffs(int waveAmount, boolean b) {
		ItemStack mainhandWeapon = new ItemStack(Items.IRON_AXE);
		if (ModList.get().isLoaded("dungeons_gear")) {
			Item MACE = ForgeRegistries.ITEMS.getValue(new ResourceLocation("dungeons_gear", "mace"));

			mainhandWeapon = new ItemStack(MACE);
		}
		Raid raid = this.getCurrentRaid();
		int enchantmentLevel = 1;
		if (raid != null && waveAmount > raid.getNumGroups(Difficulty.NORMAL)) {
			enchantmentLevel = 2;
		}

		boolean applyEnchant = false;
		if (raid != null) {
			applyEnchant = this.random.nextFloat() <= raid.getEnchantOdds();
		}
		if (applyEnchant) {
			Map<Enchantment, Integer> enchantmentIntegerMap = Maps.newHashMap();
			enchantmentIntegerMap.put(Enchantments.SHARPNESS, enchantmentLevel);
			EnchantmentHelper.setEnchantments(enchantmentIntegerMap, mainhandWeapon);
		}

		SpawnEquipmentHelper.equipMainhand(mainhandWeapon, this);
	}

	@Override
	public IllagerArmPose getArmPose() {
		IllagerArmPose illagerArmPose = super.getArmPose();
		if (illagerArmPose == IllagerArmPose.CROSSED) {
			return IllagerArmPose.NEUTRAL;
		}
		return illagerArmPose;
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
	public int getShieldCooldownTime() {
		return this.shieldCooldownTime;
	}

	@Override
	public void setShieldCooldownTime(int shieldCooldownTime) {
		this.shieldCooldownTime = shieldCooldownTime;
	}

	@Override
	public boolean isShieldDisabled() {
		return this.shieldCooldownTime > 0;
	}

	@Override
	public void disableShield(boolean guaranteeDisable) {
		float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
		if (guaranteeDisable) {
			f += 0.75F;
		}
		if (this.random.nextFloat() < f) {
			this.shieldCooldownTime = 100;
			this.stopUsingItem();
			this.level().broadcastEntityEvent(this, (byte) 30);
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
				this.useItem.hurtAndBreak(i, this, (royalGuardEntity) -> {
					royalGuardEntity.broadcastBreakEvent(hand);
					// Forge would have called onPlayerDestroyItem here
				});
				if (this.useItem.isEmpty()) {
					if (hand == InteractionHand.MAIN_HAND) {
						this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
					}

					this.useItem = ItemStack.EMPTY;
					this.playSound(SoundEvents.SHIELD_BREAK, 0.8F,
							0.8F + this.level().random.nextFloat() * 0.4F);
				}
			}
		}
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.ROYAL_GUARD_ARMOR;
	}

	class BasicAttackGoal extends Goal {

		public RoyalGuardEntity mob;
		@Nullable
		public LivingEntity target;

		public BasicAttackGoal(RoyalGuardEntity mob) {
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
			return target != null && !mob.isBlocking() && mob.distanceTo(target) <= 2.5
					&& animationsUseable()
					&& mob.hasLineOfSight(target);
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.attackAnimationTick = mob.attackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (mob.attackAnimationTick == mob.attackAnimationActionPoint) {
				mob.playSound(ModSoundEvents.ROYAL_GUARD_ATTACK.get(), 1.25F, 1.0F);
			}

			if (target != null && mob.distanceTo(target) < 3.5
					&& mob.attackAnimationTick == mob.attackAnimationActionPoint) {
				mob.doHurtTarget(target);
				RoyalGuardEntity.disableShield(target, 60);
			}
		}

		@Override
		public void stop() {
			if (target != null && !isShieldDisabled(mob) && shouldBlockForTarget(target)
					&& mob.getOffhandItem().canPerformAction(
							net.minecraftforge.common.ToolActions.SHIELD_BLOCK)
					&& mob.random.nextInt(6) == 0) {
				mob.startUsingItem(InteractionHand.OFF_HAND);
			}
		}

		public boolean isShieldDisabled(PathfinderMob shieldUser) {
			return shieldUser instanceof IShieldUser && ((IShieldUser) shieldUser).isShieldDisabled();
		}

		public boolean shouldBlockForTarget(LivingEntity target) {
			return !(target instanceof Mob) || ((Mob) target).getTarget() == mob;
		}

		public boolean animationsUseable() {
			return mob.attackAnimationTick <= 0;
		}

	}

	public static void disableShield(LivingEntity livingEntity, int ticks) {
		if (livingEntity instanceof Player && livingEntity.isBlocking()) {
			((Player) livingEntity).getCooldowns()
					.addCooldown(livingEntity.getItemInHand(livingEntity.getUsedItemHand())
							.getItem(), ticks);
			livingEntity.stopUsingItem();
			livingEntity.level().broadcastEntityEvent(livingEntity, (byte) 30);
		}
	}
}
