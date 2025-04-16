package net.firefoxsalesman.dungeonsmobs.entity.piglin.ai;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.FungusThrowerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlueNethershroomEntity;
import net.firefoxsalesman.dungeonsmobs.items.BlueNethershroomItem;
import net.firefoxsalesman.dungeonsmobs.tasks.ThrowAtTargetTask;
import net.firefoxsalesman.dungeonsmobs.utils.BrainHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class FungusThrowerAi {

	public static final Predicate<Item> FUNGUS_ITEM_PREDICATE = item -> item instanceof BlueNethershroomItem;
	public static final Predicate<ItemStack> FUNGUS_ITEM_STACK_PREDICATE = itemStack -> itemStack
			.getItem() instanceof BlueNethershroomItem;

	public static <E extends FungusThrowerEntity> void addFungusThrowerTasks(Brain<E> brain) {

		ImmutableList<? extends Behavior<? super E>> additionalFightTasks = ImmutableList.of(
				// TODO: Make fungus thrower back up
				// new RunIf<>(FungusThrowerAi::hasBlueNethershroom, BackUpIfTooClose.create(6,
				// 0.75F)),
				new ThrowAtTargetTask<>(FUNGUS_ITEM_STACK_PREDICATE,
						FungusThrowerAi::performFungusThrow));

		int priorityStart = 7; // Number of fight tasks piglins start with - would like to find a way to
					// dynamically get this from the brain
		ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> prioritizedFightTasks = BrainHelper
				.createPriorityPairs(priorityStart, additionalFightTasks);

		BrainHelper.addPrioritizedBehaviors(Activity.FIGHT, prioritizedFightTasks, brain);
	}

	public static void performFungusThrow(LivingEntity fungusThrower, LivingEntity attackTarget) {
		Vec3 targetDeltaMove = attackTarget.getDeltaMovement();
		double xDiff = attackTarget.getX() + targetDeltaMove.x - fungusThrower.getX();
		double yDiff = attackTarget.getY() - fungusThrower.getY();
		double zDiff = attackTarget.getZ() + targetDeltaMove.z - fungusThrower.getZ();
		float horizDistSq = Mth.sqrt((float) (xDiff * xDiff + zDiff * zDiff));
		InteractionHand weaponHoldingHand = ProjectileUtil.getWeaponHoldingHand(fungusThrower,
				FUNGUS_ITEM_PREDICATE);
		ItemStack fungusStack = fungusThrower.getItemInHand(weaponHoldingHand);
		BlueNethershroomEntity blueNethershroom = BlueNethershroomItem
				.createBlueNethershroom(fungusThrower.level(), fungusThrower, fungusStack.copy());

		blueNethershroom.setXRot(blueNethershroom.getXRot() + 20.0F);
		blueNethershroom.shoot(xDiff, yDiff + (double) (horizDistSq * 0.2F), zDiff, 0.75F, 8.0F);
		if (!fungusThrower.isSilent()) {
			fungusThrower.level().playSound(null, fungusThrower.getX(), fungusThrower.getY(),
					fungusThrower.getZ(), ModSoundEvents.FUNGUS_THROWER_THROW.get(),
					fungusThrower.getSoundSource(), 1.0F,
					(fungusThrower.getRandom().nextFloat() - fungusThrower.getRandom().nextFloat())
							* 0.2F + 1.0F);
		}

		fungusThrower.level().addFreshEntity(blueNethershroom);
		fungusThrower.swing(weaponHoldingHand);
	}

	public static boolean isBlueNethershroom(ItemStack itemStack) {
		return FUNGUS_ITEM_PREDICATE.test(itemStack.getItem());
	}
}
