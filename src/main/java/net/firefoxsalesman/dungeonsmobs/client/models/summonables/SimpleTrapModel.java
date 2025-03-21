package net.firefoxsalesman.dungeonsmobs.client.models.summonables;


import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SimpleTrapModel<T extends SimpleTrapEntity> extends GeoModel<T> {

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/trap.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/trap.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        if (entity.getTrapType() == 0) {
            return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/web_trap.png");
        } else if (entity.getTrapType() == 1) {
            return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/vine_trap.png");
        } else {
            return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/web_trap.png");
        }
    }
}
