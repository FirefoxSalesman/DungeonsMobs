package net.firefoxsalesman.dungeonsmobs.client.models;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class ConvenientModel<T extends Entity> extends HierarchicalModel<T> implements HeadedModel {

	private AnimationDefinition walkAnimation;

	public ConvenientModel(AnimationDefinition walkAnimation) {
		this.walkAnimation = walkAnimation;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		animateWalk(walkAnimation, limbSwing, limbSwingAmount, 3f, 2f);
	}

	private void applyHeadRotation(T entity, float netHeadYaw, float headPitch,
			float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -30.0F, 30.0F);
		getHead().yRot = netHeadYaw * ((float) Math.PI / 180F);
		getHead().xRot = headPitch * ((float) Math.PI / 180F);
	}
}
