package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;

public class IceCloudModel extends GeoModel<IceCloudEntity> {

    @Override
    public ResourceLocation getAnimationResource(IceCloudEntity entity) {
        return new ResourceLocation(MOD_ID, "animations/ice_chunk.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(IceCloudEntity entity) {
        return new ResourceLocation(MOD_ID, "geo/ice_chunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IceCloudEntity entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/ice_chunk.png");
    }
}
