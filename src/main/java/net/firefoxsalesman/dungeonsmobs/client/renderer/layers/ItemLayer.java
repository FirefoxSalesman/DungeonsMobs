package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class ItemLayer<T extends LivingEntity & GeoAnimatable> extends BlockAndItemGeoLayer<T> {
	public ItemLayer(GeoRenderer<T> renderer) {
		super(renderer);
	}

	@Override
	protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable,
			MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
		if (stack == animatable.getMainHandItem()) {
			poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

			if (stack.getItem() instanceof ShieldItem)
				poseStack.translate(0, 0.125, -0.25);
		} else if (stack == animatable.getOffhandItem()) {
			poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

			if (stack.getItem() instanceof ShieldItem) {
				poseStack.translate(0, 0.125, 0.25);
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
			}
		}
		super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick,
				packedLight, packedOverlay);
	}

	@Override
	protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
		switch (bone.getName()) {
			case "bipedHandLeft":
				return ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
			case "bipedHandRight":
				return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
			default:
				return ItemDisplayContext.NONE;
		}
	}
}
