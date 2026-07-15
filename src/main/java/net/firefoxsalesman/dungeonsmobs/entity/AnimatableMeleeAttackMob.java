package net.firefoxsalesman.dungeonsmobs.entity;

import net.firefoxsalesman.dungeonslibs.client.AnimationTimer;

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
