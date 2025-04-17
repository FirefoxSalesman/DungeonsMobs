package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.minecraft.nbt.CompoundTag;

public interface ISmartCrossbowUser {

    boolean isCrossbowUser();

    void setCrossbowUser(boolean crossbowUser);

    default void writeCrossbowUserNBT(CompoundTag compoundNBT) {
        compoundNBT.putBoolean("CrossbowUser", isCrossbowUser());
    }

    default void readCrossbowUserNBT(CompoundTag compoundNBT) {
        setCrossbowUser(compoundNBT.getBoolean("CrossbowUser"));
    }

    boolean _isChargingCrossbow();
}
