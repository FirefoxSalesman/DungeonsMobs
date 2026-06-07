package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.minecraft.world.entity.HumanoidArm;
import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.animation.ArmouredIllagerAnimator;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.ArmouredPillagerEntity;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.firefoxsalesman.dungeonsmobs.lib.client.ConvenientModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ArmoredPillagerModel<T extends ArmouredPillagerEntity> extends ConvenientModel<T>
		implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart nose;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public ArmoredPillagerModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = body.getChild("head");
		this.hat = head.getChild("hat");
		this.nose = head.getChild("nose");
		this.left_arm = body.getChild("left_arm");
		this.right_arm = body.getChild("right_arm");
		this.left_leg = body.getChild("left_leg");
		this.right_leg = body.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20)
				.addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F,
						new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64)
				.addBox(-5.0F, -11.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 81).addBox(-6.0F, -6.0F, -5.0F, 12.0F, 3.0F, 11.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition nose = head
				.addOrReplaceChild("nose",
						CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F,
								2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(40, 46).mirror()
						.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild(
				"right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(0, 22).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild(
				"right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 106);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		ArmouredIllagerAnimator.positionLimbs(entity, riding, right_arm, left_arm, right_leg, left_leg, head,
				limbSwing, limbSwingAmount, attackTime, ageInTicks);
	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		root.translateAndRotate(pPoseStack);
		body.translateAndRotate(pPoseStack);
		ModelPart arm = pSide == HumanoidArm.LEFT ? left_arm : right_arm;
		arm.translateAndRotate(pPoseStack);
	}
}
