package net.firefoxsalesman.dungeonsmobs.client.models;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class ConvenientModel<T extends Entity> extends HierarchicalModel<T> implements HeadedModel {

	private Optional<AnimationDefinition> walkAnimation;

	public ConvenientModel(AnimationDefinition walkAnimation) {
		this.walkAnimation = Optional.ofNullable(walkAnimation);
	}

	public ConvenientModel() {
		this.walkAnimation = Optional.empty();
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		if (walkAnimation.isPresent())
			animateWalk(walkAnimation.get(), limbSwing, limbSwingAmount, 3f, 2f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private void applyHeadRotation(T entity, float netHeadYaw, float headPitch,
			float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -30.0F, 30.0F);
		getHead().yRot = netHeadYaw * ((float) Math.PI / 180F);
		getHead().xRot = headPitch * ((float) Math.PI / 180F);
	}
}
