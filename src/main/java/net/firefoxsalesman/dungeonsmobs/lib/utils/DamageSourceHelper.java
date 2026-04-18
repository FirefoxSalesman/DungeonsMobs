package net.firefoxsalesman.dungeonsmobs.lib.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class DamageSourceHelper {
	public static boolean isSource(DamageSource incomingDamage, Holder<DamageType> comparableType) {
		if (comparableType == null)
			return false;
		switch (comparableType.kind()) {
			case REFERENCE:
				return incomingDamage.is(((Holder.Reference<DamageType>) comparableType).key());
			case DIRECT:
				// We get to do this because every implementation of is in Direct returns
				// false.
				return false;
			default:
				return false;
		}
	}

	public static boolean isSource(DamageSource incomingDamage, DamageSource comparableSource) {
		return isSource(incomingDamage, comparableSource.typeHolder());
	}

	public static Holder<DamageType> mkHolder(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}
}
