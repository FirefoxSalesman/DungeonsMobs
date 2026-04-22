package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.animation.RoyalGuardAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;

// Made with Blockbench 5.1.3

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class RoyalGuardModel<T extends RoyalGuardEntity> extends ConvenientModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart everything;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart leftHand;
	private final ModelPart armorLeftArm;
	private final ModelPart right_arm;
	private final ModelPart rightHand;
	private final ModelPart armorRightArm;
	private final ModelPart head;
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart righteyebrows;
	private final ModelPart lefteyebrows;
	private final ModelPart armorHead;
	private final ModelPart left_leg;
	private final ModelPart armorLeftLeg;
	private final ModelPart right_leg;
	private final ModelPart armorRightLeg;

	public RoyalGuardModel(ModelPart root) {
		this.everything = root.getChild("everything");
		this.body = this.everything.getChild("body");
		this.left_arm = this.body.getChild("left_arm");
		this.leftHand = this.left_arm.getChild("leftHand");
		this.armorLeftArm = this.left_arm.getChild("armorLeftArm");
		this.right_arm = this.body.getChild("right_arm");
		this.rightHand = this.right_arm.getChild("rightHand");
		this.armorRightArm = this.right_arm.getChild("armorRightArm");
		this.head = this.body.getChild("head");
		this.leftEye = this.head.getChild("leftEye");
		this.rightEye = this.head.getChild("rightEye");
		this.righteyebrows = this.head.getChild("righteyebrows");
		this.lefteyebrows = this.head.getChild("lefteyebrows");
		this.armorHead = this.head.getChild("armorHead");
		this.left_leg = this.everything.getChild("left_leg");
		this.armorLeftLeg = this.left_leg.getChild("armorLeftLeg");
		this.right_leg = this.everything.getChild("right_leg");
		this.armorRightLeg = this.right_leg.getChild("armorRightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition everything = partdefinition.addOrReplaceChild("everything", CubeListBuilder.create(),
				PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition body = everything.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20)
				.addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 13.0F, 6.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition upperBody_r1 = body.addOrReplaceChild("upperBody_r1",
				CubeListBuilder.create().texOffs(61, 28).addBox(-1.8F, 1.2F, -0.5F, 8.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.25F, -12.0F, -3.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild(
				"left_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-1.0F, -2.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition leftHand = left_arm.addOrReplaceChild("leftHand", CubeListBuilder.create(),
				PartPose.offset(1.0F, 11.0F, 0.0F));

		PartDefinition armorLeftArm = left_arm.addOrReplaceChild("armorLeftArm", CubeListBuilder.create()
				.texOffs(56, 46).mirror()
				.addBox(0.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(68, 48).mirror()
				.addBox(4.8F, -1.1F, -0.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(72, 46).addBox(0.0F, -1.5F, -2.0F, 4.0F, 6.0F, 4.0F,
						new CubeDeformation(0.1F)),
				PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(40, 46).mirror()
						.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightHand = right_arm.addOrReplaceChild("rightHand", CubeListBuilder.create(),
				PartPose.offset(-1.0F, 11.0F, 0.0F));

		PartDefinition armorRightArm = right_arm.addOrReplaceChild("armorRightArm", CubeListBuilder.create()
				.texOffs(56, 46)
				.addBox(-4.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(68, 48)
				.addBox(-6.8F, -1.1F, -0.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(72, 46).mirror()
				.addBox(-4.0F, -1.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false),
				PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -10.0F, -3.3098F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-1.0F, -3.0F, -5.3098F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, -0.75F));

		PartDefinition leftEye = head.addOrReplaceChild(
				"leftEye", CubeListBuilder.create().texOffs(6, 5).addBox(0.0F, -1.0F, 0.6702F, 1.0F,
						1.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, -3.0F, -4.0F));

		PartDefinition rightEye = head.addOrReplaceChild(
				"rightEye", CubeListBuilder.create().texOffs(6, 5).addBox(-1.0F, -1.0F, 0.6702F, 1.0F,
						1.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.0F, -3.0F, -4.0F));

		PartDefinition righteyebrows = head.addOrReplaceChild("righteyebrows", CubeListBuilder.create(),
				PartPose.offset(-2.5F, -5.0F, -4.0F));

		PartDefinition righteyebrows_r1 = righteyebrows.addOrReplaceChild("righteyebrows_r1",
				CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -0.5396F, -0.5F, 3.0F, 2.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.4604F, 1.1402F, 0.0F, 3.1416F, 0.0F));

		PartDefinition lefteyebrows = head.addOrReplaceChild("lefteyebrows", CubeListBuilder.create(),
				PartPose.offset(2.5F, -5.0F, -4.0F));

		PartDefinition lefteyebrows_r1 = lefteyebrows.addOrReplaceChild("lefteyebrows_r1",
				CubeListBuilder.create().texOffs(0, 5).mirror()
						.addBox(-1.5F, -0.5396F, -0.5F, 3.0F, 2.0F, 1.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offsetAndRotation(0.0F, -0.4604F, 1.1402F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorHead = head.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(32, 0)
				.addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(74, 7)
				.addBox(-2.0F, -13.0F, -5.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(63, 4).mirror()
				.addBox(-8.0F, -12.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(62, 0).addBox(5.0F, -9.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(63, 4).addBox(6.0F, -12.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 0).mirror()
				.addBox(-8.0F, -9.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, -0.5F, 0.75F));

		PartDefinition left_leg = everything.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(0, 22).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(2.0F, -10.0F, 0.0F));

		PartDefinition armorLeftLeg = left_leg.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create()
				.texOffs(0, 57).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(20, 57).mirror()
				.addBox(-1.9F, 3.7F, -3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = everything.addOrReplaceChild(
				"right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, -10.0F, 0.0F));

		PartDefinition armorRightLeg = right_leg.addOrReplaceChild("armorRightLeg", CubeListBuilder.create()
				.texOffs(0, 57).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(20, 57).mirror()
				.addBox(-1.9F, 3.7F, -3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.walkAnimationState, RoyalGuardAnimations.NEW_WALK, ageInTicks, 2F);
		animate(entity.walkBlockAnimationState, RoyalGuardAnimations.NEW_WALK_BLOCKING, ageInTicks);
		animate(entity.idleAnimationState, RoyalGuardAnimations.NEW_IDLE, ageInTicks);
		animate(entity.attackAnimationState, RoyalGuardAnimations.ATTACK, ageInTicks);
		animate(entity.celebrateAnimationState, RoyalGuardAnimations.CELEBRATE, ageInTicks);
		animate(entity.blockAnimationState, RoyalGuardAnimations.NEW_BLOCKING, ageInTicks);

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
