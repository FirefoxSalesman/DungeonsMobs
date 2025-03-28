package net.firefoxsalesman.dungeonsmobs.lib.utils;

import net.minecraft.world.entity.ai.goal.GoalSelector;

public class GoalUtils {

	public static void removeGoal(GoalSelector goalSelector, Class<?> goalClass) {
		goalSelector.getAvailableGoals().stream()
				.filter(goal -> goalClass.isInstance(goal.getGoal()))
				.toList()
				.forEach(goal -> goalSelector.removeGoal(goal.getGoal()));
	}
}
