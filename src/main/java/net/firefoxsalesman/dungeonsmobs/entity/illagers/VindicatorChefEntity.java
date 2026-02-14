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
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.raid.Raid;
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

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
		if (getCurrentRaid() == null) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.WOODEN_LADLE.get()));
		}
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
		if (isCelebrating() && celebrationAnimationTick <= 0) {
			celebrationAnimationTick = 35;
			celebrateAnimationState.start(tickCount);
		} else {
			celebrationAnimationTick--;
		}
		idleAnimationState.animateWhen(
				!walkAnimation.isMoving() && isAlive() && !isCelebrating(),
				tickCount);
	}

	public void setAttacking(boolean attacking) {
		entityData.set(ATTACKING, attacking);
	}

	public Boolean isAttackingBool() {
		return entityData.get(ATTACKING);
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.CHEF_ARMOR;
	}
}
