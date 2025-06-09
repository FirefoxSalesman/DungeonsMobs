package net.firefoxsalesman.dungeonsmobs.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.DrownedNecromancerArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.items.armor.DrownedNecromancerArmorGear;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class DrownedNecromancerArmorGearRenderer extends ArmorGearRenderer<DrownedNecromancerArmorGear> {

	public String hoodBone = "armorHood";

	public DrownedNecromancerArmorGearRenderer() {
		super(new DrownedNecromancerArmorGearModel<>());
	}

	// @Override
	// public void fitToBiped() {
	// super.fitToBiped();
	// if (this.hoodBone != null) {
	// IBone hoodBone = this.getGeoModelProvider().getBone(this.hoodBone);
	// GeoUtils.copyRotations(this.head, hoodBone);
	// hoodBone.setPositionX(this.head.x);
	// hoodBone.setPositionY(-this.head.y);
	// hoodBone.setPositionZ(this.head.z);
	// }
	// }

	// @SuppressWarnings("incomplete-switch")
	// @Override
	// public GeoArmorRenderer applySlot(EquipmentSlot slot) {
	// super.applySlot(slot);

	// this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(currentArmorItem));

	// IBone hoodBone = this.getAndHideBone(this.hoodBone);

	// switch (slot) {
	// case HEAD:
	// if (hoodBone != null)
	// hoodBone.setHidden(false);
	// break;
	// case CHEST:
	// break;
	// case LEGS:
	// break;
	// case FEET:
	// break;
	// }
	// return this;
	// }

	@Override
	public void renderFinal(PoseStack poseStack, DrownedNecromancerArmorGear animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		GeoModel<DrownedNecromancerArmorGear> geoModelProvider = getGeoModel();
		if (getCurrentEntity() != null && geoModelProvider instanceof DrownedNecromancerArmorGearModel) {
			((DrownedNecromancerArmorGearModel<DrownedNecromancerArmorGear>) geoModelProvider)
					.setWearer((LivingEntity) getCurrentEntity());
		}
		super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight,
				packedOverlay, red,
				green, blue, alpha);
	}
}
