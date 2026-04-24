package net.firefoxsalesman.dungeonsmobs.client.models.illager;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.animation.VindicatorChefAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.VindicatorChefEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

public class VindicatorChefModel<T extends VindicatorChefEntity> extends ConvenientModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart body;
	private final ModelPart arms;
	private final ModelPart mirrored;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public VindicatorChefModel(ModelPart root) {
		super(VindicatorChefAnimations.WALK);
		this.root = root;
		this.body = root.getChild("body");
		this.head = body.getChild("head");
		this.nose = head.getChild("nose");
		this.arms = body.getChild("arms");
		this.mirrored = this.arms.getChild("mirrored");
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
				.texOffs(0, 38)
				.addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(66, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F,
						new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(72, 0)
				.addBox(-6.0F, -16.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(32, 5).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 3.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nose = head
				.addOrReplaceChild("nose",
						CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F,
								2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms",
				CubeListBuilder.create().texOffs(40, 38)
						.addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
						.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 2.95F, -1.05F, -0.7505F, 0.0F, 0.0F));

		PartDefinition mirrored = arms.addOrReplaceChild("mirrored",
				CubeListBuilder.create().texOffs(44, 22).mirror()
						.addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(0.0F, 21.05F, 1.05F));

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

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.idleAnimationState, VindicatorChefAnimations.IDLE, ageInTicks, 1f);
		animate(entity.celebrateAnimationState, VindicatorChefAnimations.CELEBRATE, ageInTicks, 1f);
		animate(entity.attackAnimationState, VindicatorChefAnimations.FAST_ATTACK, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay,
			float red, float green, float blue, float alpha) {
		arms.skipDraw = true;
		mirrored.skipDraw = true;
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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
		ModelPart arm = pSide == HumanoidArm.RIGHT ? right_arm : left_arm;
		arm.translateAndRotate(pPoseStack);
	}

}
