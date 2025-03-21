package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.resources.ResourceLocation;

public class QuickGrowingVineModel extends AbstractVineModel {
    @Override
    public ResourceLocation getAnimationResource(AbstractVineEntity entity) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/quick_growing_vine.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AbstractVineEntity entity) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/quick_growing_vine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbstractVineEntity entity) {
        return new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/jungle/quick_growing_vine.png");
    }
}
