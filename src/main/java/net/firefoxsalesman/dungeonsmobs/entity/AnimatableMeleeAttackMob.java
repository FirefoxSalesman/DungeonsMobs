package net.firefoxsalesman.dungeonsmobs.entity;

import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;

public interface AnimatableMeleeAttackMob {

	AnimationTimer getTimer();

	default int getAttackAnimationTick() {
		return getTimer().getTick();
	};

	default void resetAttackTimer() {
		getTimer().reset();
	}

	int getAttackAnimationActionPoint();
}
