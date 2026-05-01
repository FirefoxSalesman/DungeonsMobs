package net.firefoxsalesman.dungeonsmobs.gear.client.models.totem;

import static net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfShieldingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TotemOfShieldingModel extends GeoModel<TotemOfShieldingEntity> {

	@Override
	public ResourceLocation getAnimationResource(TotemOfShieldingEntity entity) {
		return modLoc("animations/totem_of_shielding.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemOfShieldingEntity entity) {
		return modLoc("geo/totem_of_shielding.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemOfShieldingEntity entity) {
		return modLoc("textures/entity/totem_of_shielding.png");
	}
}
