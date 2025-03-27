package net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig;

import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ArmorGearModel<T extends ArmorGear> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(ArmorGear object) {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(ArmorGear object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(ArmorGear object) {
        return object.getAnimationFileLocation();
    }
}
