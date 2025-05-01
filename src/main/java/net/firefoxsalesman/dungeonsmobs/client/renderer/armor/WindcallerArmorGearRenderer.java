package net.firefoxsalesman.dungeonsmobs.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.WindcallerArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.items.armor.WindcallerArmorGear;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class WindcallerArmorGearRenderer extends ArmorGearRenderer<WindcallerArmorGear> {
	public WindcallerArmorGearRenderer() {
		super(new WindcallerArmorGearModel<>());
	}

	@Override
	public void renderFinal(PoseStack poseStack, WindcallerArmorGear animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		GeoModel<WindcallerArmorGear> geoModelProvider = getGeoModel();
		if (geoModelProvider instanceof WindcallerArmorGearModel && getCurrentEntity() instanceof LivingEntity) {
		    ((WindcallerArmorGearModel<WindcallerArmorGear>) geoModelProvider).setWearer((LivingEntity) getCurrentEntity());
		}
		super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight,
				packedOverlay, red,
				green, blue, alpha);
	}
}
