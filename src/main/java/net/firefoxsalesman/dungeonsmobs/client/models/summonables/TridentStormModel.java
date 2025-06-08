package net.firefoxsalesman.dungeonsmobs.client.models.summonables;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class TridentStormModel extends GeoModel<TridentStormEntity> {

    @Override
    public ResourceLocation getAnimationResource(TridentStormEntity entity) {
        return new ResourceLocation(MOD_ID, "animations/trident_storm.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TridentStormEntity entity) {
        return new ResourceLocation(MOD_ID, "geo/trident_storm.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TridentStormEntity entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/trident_storm.png");
    }
}
