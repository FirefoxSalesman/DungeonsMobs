package net.firefoxsalesman.dungeonsmobs.lib.client;

/**
 * A timer for animations. Intended to remove the need to declare all the
 * different ticks & lengths by hand.
 */
public class AnimationTimer {
	private int tick;
	private final int length;

	public AnimationTimer(int length) {
		this.length = length;
		this.tick = 0;
	}

	/**
	 * If the tick is greater than 0, decrement the tick.
	 */
	public void dec() {
		if (tick > 0) {
			tick--;
		}
	}

	/**
	 * Reset the tick to the length.
	 */
	public void reset() {
		tick = length;
	}

	/**
	 * @return true if the timer is currently running
	 */
	public boolean isRunning() {
		return tick > 0;
	}

	/**
	 * @return true if the tick is equal to the given time
	 */
	public boolean tickEquals(int time) {
		return tick == time;
	}

	/**
	 * @return the current tick of the timer
	 */
	public int getTick() {
		return tick;
	}

	/**
	 * Tends to be used in animationsUseable methods for goals, thus the name.
	 * 
	 * @return true if the tick is less than or equal to 0.
	 */
	public boolean animationsUseable() {
		return tick <= 0;
	}
}
