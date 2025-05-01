package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.WindcallerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class WindcallerModel extends GeoModel<WindcallerEntity> {

	@Override
	public ResourceLocation getAnimationResource(WindcallerEntity entity) {
		return new ResourceLocation(MOD_ID, "animations/windcaller.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WindcallerEntity entity) {
		return new ResourceLocation(MOD_ID, "geo/windcaller.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WindcallerEntity entity) {
		return new ResourceLocation(MOD_ID, "textures/entity/illager/windcaller.png");
	}

	@Override
	public void setCustomAnimations(WindcallerEntity entity, long uniqueID,
			AnimationState<WindcallerEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

		CoreGeoBone cape = this.getAnimationProcessor().getBone("bipedCape");
		cape.setHidden(entity.getItemBySlot(EquipmentSlot.CHEST).getItem() != entity.getArmorSet().getChest()
				.get());

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);

		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}
}
