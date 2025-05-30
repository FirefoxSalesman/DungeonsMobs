package net.firefoxsalesman.dungeonsmobs.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.IceologerArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.items.armor.IceologerArmorGear;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class IceologerArmorGearRenderer extends ArmorGearRenderer<IceologerArmorGear> {

	public IceologerArmorGearRenderer() {
		super(new IceologerArmorGearModel<>());
	}

	@Override
	public void renderFinal(PoseStack poseStack, IceologerArmorGear animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		GeoModel<IceologerArmorGear> geoModelProvider = getGeoModel();
		if (geoModelProvider instanceof IceologerArmorGearModel && getCurrentEntity() instanceof LivingEntity) {
			((IceologerArmorGearModel<IceologerArmorGear>) geoModelProvider)
					.setWearer((LivingEntity) getCurrentEntity());
		}
		super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight,
				packedOverlay, red,
				green, blue, alpha);
	}
}
