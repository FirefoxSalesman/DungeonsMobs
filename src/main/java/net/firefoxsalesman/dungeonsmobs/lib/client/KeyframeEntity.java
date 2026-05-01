package net.firefoxsalesman.dungeonsmobs.lib.client;

import java.util.Map;

import net.minecraft.world.entity.AnimationState;

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
}
