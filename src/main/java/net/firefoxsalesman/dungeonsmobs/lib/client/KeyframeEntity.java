package net.firefoxsalesman.dungeonsmobs.lib.client;

import java.util.Map;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.WalkAnimationState;

public interface KeyframeEntity {
	/**
	 * @return the map of animationstates
	 */
	Map<String, AnimationState> getStates();

	/**
	 * @return an {@link AnimationState} from the map
	 * @param name: the key for that {@link AnimationState}
	 */
	default AnimationState getState(String name) {
		return getStates().get(name);
	}

	/**
	 * <p>
	 * Add a new {@link AnimationState} to the map
	 * </p>
	 * 
	 * @param name: the key for that {@link AnimationState}
	 */
	default void addState(String name) {
		getStates().put(name, new AnimationState());
	}

	/**
	 * @return the entity's walkAnimation field
	 */
	WalkAnimationState getWalkAnimation();

	/**
	 * @return true if the entity's walk animation is playing. It's not perfect,
	 *         expect a bit of a jitter
	 */
	default boolean isMoving() {
		return getWalkAnimation().speed() > 1.0E-1F;
	}
}
