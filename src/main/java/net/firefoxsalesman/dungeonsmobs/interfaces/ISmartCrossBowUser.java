package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.minecraft.nbt.CompoundTag;

public interface ISmartCrossBowUser {

	boolean isCrossbowUser();

	void setCrossbowUser(boolean crossbowUser);

	default void saveXbowNBT(CompoundTag compoundNBT) {
		compoundNBT.putBoolean("CrossbowUser", isCrossbowUser());
	}

	default void loadXbowNBT(CompoundTag compoundNBT) {
		setCrossbowUser(compoundNBT.getBoolean("CrossbowUser"));
	}

	boolean _isChargingCrossbow();
}
