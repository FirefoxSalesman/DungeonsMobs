package net.firefoxsalesman.dungeonsmobs.client.models.projectile;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.PoisonQuillEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PoisonQuillModel extends GeoModel<PoisonQuillEntity> {

	@Override
	public ResourceLocation getAnimationResource(PoisonQuillEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "animations/poison_quill.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PoisonQuillEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID, "geo/poison_quill.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PoisonQuillEntity entity) {
		return new ResourceLocation(DungeonsMobs.MOD_ID,
				entity.isKelp() ? "textures/entity/projectile/water_poison_quill.png"
						: "textures/entity/projectile/poison_quill.png");
	}

	@Override
	public void setCustomAnimations(PoisonQuillEntity entity, long uniqueID,
			AnimationState<PoisonQuillEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone everything = this.getAnimationProcessor().getBone("everything");

		everything.setRotY(-1.5708F);
	}
}
