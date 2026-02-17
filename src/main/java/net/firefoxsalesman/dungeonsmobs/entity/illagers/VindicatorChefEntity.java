package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import java.util.Map;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class VindicatorChefEntity extends Vindicator implements SpawnArmoredMob {
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState celebrateAnimationState = new AnimationState();
	public int celebrationAnimationTick = 0;

	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(
			VindicatorChefEntity.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState attackAnimationState = new AnimationState();
	private int attackAnimationTimeout = 0;

	public VindicatorChefEntity(EntityType<? extends Vindicator> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Vindicator.createAttributes();
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new VindicatorChefEntity.AttackGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
		if (getCurrentRaid() == null) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.WOODEN_LADLE.get()));
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ATTACKING, false);
	}

	@Override
	public void applyRaidBuffs(int pWave, boolean pUnusedFalse) {
		ItemStack itemstack = new ItemStack(ModItems.WOODEN_LADLE.get());
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (pWave > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
		}

		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		if (isAttacking() && attackAnimationTimeout <= 0) {
			attackAnimationTimeout = 5;
			attackAnimationState.start(tickCount);
		} else {
			attackAnimationTimeout--;
		}
		if (isCelebrating() && celebrationAnimationTick <= 0 && !isAttacking()) {
			celebrationAnimationTick = 35;
			celebrateAnimationState.start(tickCount);
		} else {
			celebrationAnimationTick--;
		}
		idleAnimationState.animateWhen(
				!walkAnimation.isMoving() && isAlive() && !isCelebrating() && isAttacking(),
				tickCount);
	}

	public void setAttacking(boolean attacking) {
		entityData.set(ATTACKING, attacking);
	}

	public boolean isAttacking() {
		return entityData.get(ATTACKING);
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.CHEF_ARMOR;
	}

	class AttackGoal extends MeleeAttackGoal {
		private final VindicatorChefEntity entity;

		public AttackGoal(VindicatorChefEntity pMob) {
			super(pMob, 1.0D, false);
			entity = pMob;
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {

			double d0 = this.getAttackReachSqr(pEnemy);
			if (pDistToEnemySqr <= d0 && isTimeToAttack()) {
				this.resetAttackCooldown();
				entity.setAttacking(true);
				this.mob.swing(InteractionHand.MAIN_HAND);
				this.mob.doHurtTarget(pEnemy);
			} else {
				entity.setAttacking(false);
			}
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAttacking(false);
		}
	}
}
