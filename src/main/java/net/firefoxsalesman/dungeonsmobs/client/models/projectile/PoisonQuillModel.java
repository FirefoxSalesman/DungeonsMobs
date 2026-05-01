package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PoisonQuillModel extends GeoModel<PoisonQuillEntity> {

	@Override
	public ResourceLocation getAnimationResource(PoisonQuillEntity entity) {
		return modLoc("animations/poison_quill.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PoisonQuillEntity entity) {
		return modLoc("geo/poison_quill.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PoisonQuillEntity entity) {
		return modLoc(entity.isKelp() ? "textures/entity/projectile/water_poison_quill.png"
				: "textures/entity/projectile/poison_quill.png");
	}
}
