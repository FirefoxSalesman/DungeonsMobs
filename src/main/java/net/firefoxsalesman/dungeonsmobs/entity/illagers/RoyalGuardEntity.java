package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.UseShieldGoal;
import net.firefoxsalesman.dungeonsmobs.interfaces.IShieldUser;
import net.firefoxsalesman.dungeonslibs.client.AnimationTimer;
import net.firefoxsalesman.dungeonslibs.client.KeyframeEntity;
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
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;
import java.util.UUID;

public class RoyalGuardEntity extends AbstractIllager implements IShieldUser, KeyframeEntity {
	private Map<String, AnimationState> states;

	private static final UUID SPEED_MODIFIER_BLOCKING_UUID = UUID
			.fromString("05cd371b-0ff4-4ded-8630-b380232ed7b1");
	private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(
			SPEED_MODIFIER_BLOCKING_UUID,
			"Blocking speed decrease", -0.1D, AttributeModifier.Operation.ADDITION);

	private int shieldCooldownTime;

	private final AnimationTimer attackTimer = new AnimationTimer(27);
	private int attackAnimationActionPoint = 15;

	public RoyalGuardEntity(Level world) {
		super(ModEntities.ROYAL_GUARD.get(), world);
		shieldCooldownTime = 0;
	}

	public RoyalGuardEntity(EntityType<? extends RoyalGuardEntity> type, Level level) {
		super(type, level);
		shieldCooldownTime = 0;
		states = genStates("idle", "celebrate", "attack", "walk", "walkBlock", "block");
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new UseShieldGoal(this, 7.5D, 60, 160, 15, 100, false));
		goalSelector.addGoal(1, new RoyalGuardEntity.BasicAttackGoal(this));
		goalSelector.addGoal(2, new ApproachTargetGoal(this, 0, 1.0D, true));
		goalSelector.addGoal(3, new LookAtTargetGoal(this));
		goalSelector.addGoal(1, new AbstractIllager.RaiderOpenDoorGoal(this));
		goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty,
			MobSpawnType spawnType, @Nullable SpawnGroupData groupData,
			@Nullable CompoundTag compoundTag) {
		SpawnGroupData ilivingentitydata = super.finalizeSpawn(accessor, difficulty, spawnType,
				groupData,
				compoundTag);
		((GroundPathNavigation) getNavigation()).setCanOpenDoors(true);
		populateDefaultEquipmentSlots(getRandom(), difficulty);
		populateDefaultEquipmentEnchantments(getRandom(), difficulty);
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

	public void handleEntityEvent(byte event) {
		if (event == 4) {
			attackTimer.reset();
		} else {
			super.handleEntityEvent(event);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		getState("attack").animateWhen(attackTimer.isRunning(), tickCount);
		getState("walkBlock").animateWhen(!attackTimer.isRunning() && isMoving() && isBlocking(), tickCount);
		getState("block").animateWhen(!attackTimer.isRunning() && !isMoving() && isBlocking(), tickCount);
		getState("walk").animateWhen(!attackTimer.isRunning() && isMoving() && !isBlocking(), tickCount);
		getState("celebrate").animateWhen(
				!attackTimer.isRunning() && !isMoving() && !isBlocking() && isCelebrating(),
				tickCount);
		getState("idle").animateWhen(
				!attackTimer.isRunning() && !isMoving() && !isBlocking() && !isCelebrating(),
				tickCount);
	}

	public void baseTick() {
		super.baseTick();
		AttributeInstance modifiableattributeinstance = getAttribute(Attributes.MOVEMENT_SPEED);

		if (isBlocking()) {
			if (!modifiableattributeinstance.hasModifier(SPEED_MODIFIER_BLOCKING)) {
				modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_BLOCKING);
			}
		} else {
			modifiableattributeinstance.removeModifier(SPEED_MODIFIER_BLOCKING);
		}

		attackTimer.dec();
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(ModSoundEvents.ROYAL_GUARD_STEP.get(), 0.5F, 1.0F);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Vindicator.createAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
				.add(Attributes.MOVEMENT_SPEED, 0.325F).add(Attributes.FOLLOW_RANGE, 18.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		if (ModList.get().isLoaded("dungeonsmobs")) {
			Item MACE = ForgeRegistries.ITEMS.getValue(new ResourceLocation("dungeonsmobs", "mace"));

			ItemStack mace = new ItemStack(MACE);
			if (getCurrentRaid() == null) {
				SpawnEquipmentHelper.equipMainhand(mace, this);
			}
		} else {
			if (getCurrentRaid() == null) {
				SpawnEquipmentHelper.equipMainhand(Items.IRON_AXE.getDefaultInstance(), this);
			}
		}
		SpawnEquipmentHelper.equipOffhand(ModItems.ROYAL_GUARD_SHIELD.get().getDefaultInstance(), this);
	}

	@Override
	public void applyRaidBuffs(int waveAmount, boolean b) {
		ItemStack mainhandWeapon = new ItemStack(Items.IRON_AXE);
		if (ModList.get().isLoaded("dungeonsmobs")) {
			Item MACE = ForgeRegistries.ITEMS.getValue(new ResourceLocation("dungeonsmobs", "mace"));

			mainhandWeapon = new ItemStack(MACE);
		}
		Raid raid = getCurrentRaid();
		int enchantmentLevel = 1;
		if (raid != null && waveAmount > raid.getNumGroups(Difficulty.NORMAL)) {
			enchantmentLevel = 2;
		}

		boolean applyEnchant = false;
		if (raid != null) {
			applyEnchant = random.nextFloat() <= raid.getEnchantOdds();
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
		if (shieldCooldownTime > 0) {
			shieldCooldownTime--;
		} else if (shieldCooldownTime < 0) {
			shieldCooldownTime = 0;
		}
	}

	@Override
	public int getShieldCooldownTime() {
		return shieldCooldownTime;
	}

	@Override
	public void setShieldCooldownTime(int shieldCooldownTime) {
		this.shieldCooldownTime = shieldCooldownTime;
	}

	@Override
	public boolean isShieldDisabled() {
		return shieldCooldownTime > 0;
	}

	@Override
	public void disableShield(boolean guaranteeDisable) {
		float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
		if (guaranteeDisable) {
			f += 0.75F;
		}
		if (random.nextFloat() < f) {
			shieldCooldownTime = 100;
			stopUsingItem();
			level().broadcastEntityEvent(this, (byte) 30);
		}
	}

	@Override
	protected void playHurtSound(DamageSource damageSource) {
		if (shieldCooldownTime == 100) {
			playSound(SoundEvents.SHIELD_BREAK, 1.0F, 0.8F + level().random.nextFloat() * 0.4F);
		} else if (isBlocking()) {
			playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + level().random.nextFloat() * 0.4F);
		} else {
			super.playHurtSound(damageSource);
		}
	}

	@Override
	public void blockUsingShield(LivingEntity livingEntity) {
		super.blockUsingShield(livingEntity);
		if (livingEntity.getMainHandItem().canDisableShield(useItem, this, livingEntity)) {
			disableShield(true);
		}
	}

	@Override
	protected void hurtCurrentlyUsedShield(float amount) {
		if (useItem.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) {
			if (amount >= 3.0F) {
				int i = 1 + Mth.floor(amount);
				InteractionHand hand = getUsedItemHand();
				useItem.hurtAndBreak(i, this, (royalGuardEntity) -> {
					royalGuardEntity.broadcastBreakEvent(hand);
					// Forge would have called onPlayerDestroyItem here
				});
				if (useItem.isEmpty()) {
					if (hand == InteractionHand.MAIN_HAND) {
						setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
					}

					useItem = ItemStack.EMPTY;
					playSound(SoundEvents.SHIELD_BREAK, 0.8F,
							0.8F + level().random.nextFloat() * 0.4F);
				}
			}
		}
	}

	class BasicAttackGoal extends Goal {

		public RoyalGuardEntity mob;
		@Nullable
		public LivingEntity target;

		public BasicAttackGoal(RoyalGuardEntity mob) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.mob = mob;
			target = mob.getTarget();
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
			mob.attackTimer.reset();
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (mob.attackTimer.tickEquals(mob.attackAnimationActionPoint)) {
				mob.playSound(ModSoundEvents.ROYAL_GUARD_ATTACK.get(), 1.25F, 1.0F);
			}

			if (target != null && mob.distanceTo(target) < 3.5
					&& mob.attackTimer.tickEquals(mob.attackAnimationActionPoint)) {
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
			return mob.attackTimer.animationsUseable();
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

	@Override
	public Map<String, AnimationState> getStates() {
		return states;
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}
}
