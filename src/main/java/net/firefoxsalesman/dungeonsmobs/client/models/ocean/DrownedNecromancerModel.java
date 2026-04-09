package net.firefoxsalesman.dungeonsmobs.client.models.ocean;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.animation.DrownedNecromancerAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.water.DrownedNecromancerEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

// Made with Blockbench 5.1.1

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class DrownedNecromancerModel<T extends DrownedNecromancerEntity> extends ConvenientModel<T>
		implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart everything;
	private final ModelPart body;
	private final ModelPart Cape;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart rightHand;
	private final ModelPart head;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public DrownedNecromancerModel(ModelPart root) {
		super();
		this.everything = root.getChild("everything");
		this.body = this.everything.getChild("body");
		this.Cape = this.body.getChild("Cape");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.rightHand = this.right_arm.getChild("rightHand");
		this.head = this.body.getChild("head");
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
				.texOffs(16, 32).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition Cape = body.addOrReplaceChild("Cape",
				CubeListBuilder.create().texOffs(99, 0).addBox(-7.5F, -0.5F, -5.5F, 14.0F, 23.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.5F, -12.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 32)
				.mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.mirror(false)
				.texOffs(96, 44)
				.addBox(-1.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 48)
				.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(1.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 48)
				.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(96, 44).mirror()
				.addBox(-4.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(40, 16)
				.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, 9.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightHand = right_arm.addOrReplaceChild("rightHand", CubeListBuilder.create(),
				PartPose.offset(-1.0F, 11.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(82, 28)
				.addBox(-5.0F, -10.0F, -4.25F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.0F, -8.0F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -3.25F, 8.0F, 8.0F, 8.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -12.0F, -0.75F));

		PartDefinition left_leg = everything.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(0, 32).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, -10.0F, 0.0F));

		PartDefinition right_leg = everything.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(-2.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 137, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.walkAnimationState, DrownedNecromancerAnimations.WALK, ageInTicks, 1f);
		animate(entity.swimAnimationState, DrownedNecromancerAnimations.SWIM, ageInTicks, 1f);
		animate(entity.waterIdleAnimationState, DrownedNecromancerAnimations.WATER_IDLE, ageInTicks, 1f);
		animate(entity.landIdleAnimationState, DrownedNecromancerAnimations.LAND_IDLE, ageInTicks, 1f);
		animate(entity.waterSummonAnimationState, DrownedNecromancerAnimations.WATER_SUMMON, ageInTicks, 1f);
		animate(entity.landSummonAnimationState, DrownedNecromancerAnimations.LAND_SUMMON, ageInTicks,
				1f);
		animate(entity.shootAnimationState, DrownedNecromancerAnimations.SHOOT, ageInTicks, 1f);
		animate(entity.waterShootAnimationState, DrownedNecromancerAnimations.SHOOT_RAIN, ageInTicks, 1f);
		animate(entity.landShootAnimationState, DrownedNecromancerAnimations.SHOOT_LAND, ageInTicks, 1f);
		animate(entity.waterTridentStormAnimationState, DrownedNecromancerAnimations.TRIDENT_STORM, ageInTicks, 1f);
		animate(entity.landTridentStormAnimationState, DrownedNecromancerAnimations.TRIDENT_STORM_LAND, ageInTicks, 1f);
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
