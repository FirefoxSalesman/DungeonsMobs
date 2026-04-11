package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.minecraft.world.entity.AnimationState;

/**
 * Ugly way to get the mage & mage clone's animations
 */
public interface AnimatedMage {
	AnimationState getIdleState();

	AnimationState getCelebrateState();

	AnimationState getAttackState();

	AnimationState getVanishState();

	AnimationState getAppearState();
}
