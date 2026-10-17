package net.firefoxsalesman.dungeonsmobs.utils;

import java.util.Collection;

import net.minecraft.util.RandomSource;

public class RandomHelper {
	public static <T> T getRandomElement(RandomSource random, Collection<T> collection) {
		return (T) collection.toArray()[random.nextInt(0, collection.size())];
	}
}
