package net.firefoxsalesman.dungeonsmobs.interfaces;

public interface ITrapsTarget {

    void setTargetTrapped(boolean trapped, boolean notifyOthers);

    boolean isTargetTrapped();
}
