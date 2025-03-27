package net.firefoxsalesman.dungeonsmobs.interfaces;

public interface IHasInventorySprite {
    default String getModelResource() {
        return null;
    }
}
