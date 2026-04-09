package net.firefoxsalesman.dungeonsmobs.client.models.undead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.animation.NecromancerAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

// Made with Blockbench 5.1.1

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class NecromancerModel<T extends NecromancerEntity> extends ConvenientModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart everything;
	private final ModelPart body;
	private final ModelPart Cape;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart rightHand;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart leftEye;
	private final ModelPart rightEye;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public NecromancerModel(ModelPart root) {
		super(NecromancerAnimations.WALK);
		this.everything = root.getChild("everything");
		this.body = this.everything.getChild("body");
		this.Cape = this.body.getChild("Cape");
		this.left_arm = this.body.getChild("left_arm");
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
				.texOffs(73, 36).addBox(-1.0F, -0.3F, -2.31F, 2.0F, 10.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition Cape = body.addOrReplaceChild("Cape",
				CubeListBuilder.create().texOffs(99, 0).addBox(-8.5F, -0.5F, -4.5F, 16.0F, 23.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.5F, -12.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(96, 44)
				.addBox(-1.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(40, 16)
				.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16)
				.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(96, 44).mirror()
				.addBox(-4.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, 9.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightHand = right_arm.addOrReplaceChild("rightHand", CubeListBuilder.create(),
				PartPose.offset(-1.0F, 11.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -8.0F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(96, 31).addBox(-4.0F, -9.0F, -3.25F, 8.0F, 4.0F, 8.0F,
						new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, -12.0F, -0.75F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leftEye = head
				.addOrReplaceChild("leftEye",
						CubeListBuilder.create().texOffs(6, 7).addBox(0.0F, -1.0F, 0.74F, 1.0F,
								1.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(1.0F, -3.0F, -4.0F));

		PartDefinition rightEye = head.addOrReplaceChild(
				"rightEye", CubeListBuilder.create().texOffs(6, 7).addBox(-1.0F, -1.0F, 0.74F, 1.0F,
						1.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.0F, -3.0F, -4.0F));

		PartDefinition left_leg = everything.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 16).mirror()
				.addBox(-2.25F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.29F))
				.mirror(false), PartPose.offset(2.0F, -10.0F, 0.0F));

		PartDefinition right_leg = everything.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 16).addBox(-1.75F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F,
						new CubeDeformation(0.29F)),
				PartPose.offset(-2.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 139, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.idleAnimationState, NecromancerAnimations.IDLE, ageInTicks, 1f);
		animate(entity.summonAnimationState, NecromancerAnimations.SUMMON, ageInTicks, 1f);
		animate(entity.shootAnimationState, NecromancerAnimations.SHOOT, ageInTicks, 1f);
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
