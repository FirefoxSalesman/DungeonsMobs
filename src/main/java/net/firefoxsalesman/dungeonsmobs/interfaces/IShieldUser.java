package net.firefoxsalesman.dungeonsmobs.interfaces;

public interface IShieldUser {

    int getShieldCooldownTime();

    void setShieldCooldownTime(int shieldCooldownTime);

    void disableShield(boolean guaranteeDisable);

    boolean isShieldDisabled();
}
