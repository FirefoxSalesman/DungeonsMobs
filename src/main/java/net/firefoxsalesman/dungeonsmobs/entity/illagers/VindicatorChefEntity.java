package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import java.util.Map;

import com.google.common.collect.Maps;

import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;
import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
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
import net.minecraft.world.entity.WalkAnimationState;
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

public class VindicatorChefEntity extends Vindicator implements KeyframeEntity {
	private Map<String, AnimationState> states;
	private final AnimationTimer celebrationTimer = new AnimationTimer(35);

	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(
			VindicatorChefEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimationTimer attackTimer = new AnimationTimer(5);

	public VindicatorChefEntity(EntityType<? extends Vindicator> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		states = genStates("idle", "celebrate", "attack");
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
		if (isAttacking() && attackTimer.animationsUseable()) {
			attackTimer.reset();
			getState("attack").start(tickCount);
		} else
			attackTimer.dec();
		if (isCelebrating() && celebrationTimer.animationsUseable() && !isAttacking()) {
			celebrationTimer.reset();
			getState("celebrate").start(tickCount);
		} else
			celebrationTimer.dec();
		getState("idle").animateWhen(
				!isMoving() && isAlive() && !isCelebrating() && !isAttacking(),
				tickCount);
	}

	public void setAttacking(boolean attacking) {
		entityData.set(ATTACKING, attacking);
	}

	public boolean isAttacking() {
		return entityData.get(ATTACKING);
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

	@Override
	public Map<String, AnimationState> getStates() {
		return states;
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}
}
