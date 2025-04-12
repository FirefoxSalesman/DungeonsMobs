package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.SkeletonVanguardModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkeletonVanguardRenderer extends GeoEntityRenderer<SkeletonVanguardEntity> {
	public SkeletonVanguardRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SkeletonVanguardModel());
		addRenderLayer(new ItemLayer<>(this) {
			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					SkeletonVanguardEntity animatable, MultiBufferSource bufferSource,
					float partialTick,
					int packedLight, int packedOverlay) {
				if (stack == animatable.getMainHandItem() && !(stack.getItem() instanceof ShieldItem)) {
					poseStack.translate(0, .4, -.4);
					poseStack.mulPose(Axis.XP.rotationDegrees(180));
				}
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick,
						packedLight, packedOverlay);
			}

			@Override
			public void translateBlockingShield(PoseStack stack) {
				stack.mulPose(Axis.ZP.rotationDegrees(90));
				stack.mulPose(Axis.YP.rotationDegrees(-45));
				stack.mulPose(Axis.XP.rotationDegrees(180));
				stack.translate(.25, .25, .5);
			}
		});
		addRenderLayer(new ArmourLayer<>(this));
	}

	@Override
	public void renderRecursively(PoseStack poseStack, SkeletonVanguardEntity animatable, GeoBone bone,
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
			boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		if (isArmorBone(bone)) {
			bone.setHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected boolean isArmorBone(CoreGeoBone bone) {
		return bone.getName().startsWith("armor");
	}
}
