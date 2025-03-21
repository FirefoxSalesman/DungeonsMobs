package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KelpTrapModel<T extends KelpTrapEntity> extends GeoModel<T> {

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
        return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/kelp_trap.png");
    }
}
