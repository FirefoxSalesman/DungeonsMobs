package net.firefoxsalesman.dungeonsmobs.client.models.undead;

import net.firefoxsalesman.dungeonsmobs.client.animation.SkeletonVanguardAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class SkeletonVanguardModel<T extends SkeletonVanguardEntity> extends ConvenientModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart everything;
	private final ModelPart body;
	private final ModelPart Cape;
	private final ModelPart left_arm;
	private final ModelPart leftHand;
	private final ModelPart right_arm;
	private final ModelPart rightHand;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public SkeletonVanguardModel(ModelPart root) {
		this.everything = root.getChild("everything");
		this.body = this.everything.getChild("body");
		this.Cape = this.body.getChild("Cape");
		this.left_arm = this.body.getChild("left_arm");
		this.leftHand = this.left_arm.getChild("leftHand");
		this.right_arm = this.body.getChild("right_arm");
		this.rightHand = this.right_arm.getChild("rightHand");
		this.head = this.body.getChild("head");
		this.hat = this.head.getChild("hat");
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.left_leg = this.everything.getChild("left_leg");
		this.right_leg = this.everything.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition everything = partdefinition.addOrReplaceChild("everything", CubeListBuilder.create(),
				PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition body = everything.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16)
				.addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 50).addBox(-5.0F, -12.5F, -3.0F, 10.0F, 7.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition Cape = body.addOrReplaceChild("Cape", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.5F, -12.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild(
				"left_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F,
						12.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition leftHand = left_arm.addOrReplaceChild("leftHand", CubeListBuilder.create(),
				PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild(
				"right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F,
						12.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightHand = right_arm.addOrReplaceChild("rightHand", CubeListBuilder.create(),
				PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -8.0F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32)
				.addBox(-5.0F, -9.0F, -4.25F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-1.0F, -11.0F, -5.25F, 2.0F, 8.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, -0.75F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create(),
				PartPose.offset(1.0F, -3.0F, -4.0F));

		PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create(),
				PartPose.offset(-1.0F, -3.0F, -4.0F));

		PartDefinition left_leg = everything.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 60).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 9.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, -10.0F, 0.0F));

		PartDefinition right_leg = everything.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 45).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 9.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 96);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.walkAnimationState, SkeletonVanguardAnimations.NEW_WALK, ageInTicks);
		animate(entity.blockAnimationState, SkeletonVanguardAnimations.NEW_BLOCKING, ageInTicks);
		animate(entity.attackAnimationState, SkeletonVanguardAnimations.ATTACK, ageInTicks);
		animate(entity.idleAnimationState, SkeletonVanguardAnimations.NEW_IDLE, ageInTicks);
		animate(entity.walkBlockAnimationState, SkeletonVanguardAnimations.NEW_WALK_BLOCKING, ageInTicks);
	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public ModelPart root() {
		return everything;
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		everything.translateAndRotate(pPoseStack);
		body.translateAndRotate(pPoseStack);
		ModelPart arm = pSide == HumanoidArm.RIGHT ? right_arm : left_arm;
		arm.translateAndRotate(pPoseStack);
	}
}
