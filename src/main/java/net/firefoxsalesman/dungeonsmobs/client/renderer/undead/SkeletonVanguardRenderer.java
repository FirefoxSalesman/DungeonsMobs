package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.SkeletonVanguardModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import software.bernie.geckolib.cache.object.GeoBone;
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
				    poseStack.translate(0, -.4, .2);
				}
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick,
						packedLight, packedOverlay);
			}
		});
		addRenderLayer(new ArmourLayer<>(this));
	}
}
