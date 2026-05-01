package net.firefoxsalesman.dungeonsmobs.client.models.summonables;// Made with Blockbench 3.6.6

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;

// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindcallerTornadoModel extends GeoModel<WindcallerTornadoEntity> {

	@Override
	public ResourceLocation getAnimationResource(WindcallerTornadoEntity entity) {
		return modLoc("animations/windcaller_tornado.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WindcallerTornadoEntity entity) {
		return modLoc("geo/windcaller_tornado.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WindcallerTornadoEntity entity) {
		return modLoc("textures/entity/windcaller_tornado.png");
	}
}
