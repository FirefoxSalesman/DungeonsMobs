package net.firefoxsalesman.dungeonsmobs.client.models.summonables;// Made with Blockbench 3.6.6

// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;

public class WindcallerTornadoModel extends GeoModel<WindcallerTornadoEntity> {

	@Override
	public ResourceLocation getAnimationResource(WindcallerTornadoEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/windcaller_tornado.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WindcallerTornadoEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/windcaller_tornado.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WindcallerTornadoEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/windcaller_tornado.png");
	}
}
