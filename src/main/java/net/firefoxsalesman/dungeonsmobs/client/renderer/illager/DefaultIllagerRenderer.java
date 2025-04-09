package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DefaultIllagerRenderer<T extends Mob & GeoAnimatable> extends GeoEntityRenderer<T> {
	private float scaleFactor = 0.9375F;

	public DefaultIllagerRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
		super(renderManager, modelProvider);
		addRenderLayer(new ItemLayer<>(this));
		addRenderLayer(new ArmourLayer<>(this) {
			@Override
			protected void prepModelPartForRender(PoseStack poseStack, GeoBone bone, ModelPart sourcePart) {
				super.prepModelPartForRender(poseStack, bone, sourcePart);
				// TODO: If you're having issues with helmet rendering, this is why.
				if (bone.getName().equals("armorBipedHead")) {
					poseStack.translate(0, 0.125, 0); // 1y is 1 cube up, we want 2/16
				}
				if (bone.getName().equals("armorBipedBody")) {
					poseStack.scale(1.0F, 1.0F, 0.8F);
				}
			}

		});
	}

	public DefaultIllagerRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider,
			float scaleFactor) {
		this(renderManager, modelProvider);
		this.scaleFactor = scaleFactor;
	}

	@Override
	protected void applyRotations(T entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}
}
