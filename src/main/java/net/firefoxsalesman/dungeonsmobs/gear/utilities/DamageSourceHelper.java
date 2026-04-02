package net.firefoxsalesman.dungeonsmobs.gear.utilities;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

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
}
