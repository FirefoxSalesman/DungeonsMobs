package net.firefoxsalesman.dungeonsmobs.client.models.ender;
// Made with Blockbench 4.12.3

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.animation.EndersentAnimations;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class EndersentModel<T extends EndersentEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart humanoid;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart leftArmBone;
	private final ModelPart leftHand;
	private final ModelPart rightArm;
	private final ModelPart rightArmBone;
	private final ModelPart rightHand;
	private final ModelPart eye;

	public EndersentModel(ModelPart root) {
		this.humanoid = root.getChild("humanoid");
		this.leftLeg = this.humanoid.getChild("leftLeg");
		this.rightLeg = this.humanoid.getChild("rightLeg");
		this.body = this.humanoid.getChild("body");
		this.head = this.body.getChild("head");
		this.leftArm = this.body.getChild("leftArm");
		this.leftArmBone = this.leftArm.getChild("leftArmBone");
		this.leftHand = this.leftArmBone.getChild("leftHand");
		this.rightArm = this.body.getChild("rightArm");
		this.rightArmBone = this.rightArm.getChild("rightArmBone");
		this.rightHand = this.rightArmBone.getChild("rightHand");
		this.eye = this.body.getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition humanoid = partdefinition.addOrReplaceChild("humanoid", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leftLeg = humanoid.addOrReplaceChild("leftLeg", CubeListBuilder.create(),
				PartPose.offset(3.5F, -61.0F, 0.0F));

		PartDefinition leftLeg_r1 = leftLeg.addOrReplaceChild("leftLeg_r1",
				CubeListBuilder.create().texOffs(0, 20).addBox(-5.5F, -61.0F, 0.0F, 4.0F, 61.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.5F, 61.0F, 2.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition rightLeg = humanoid.addOrReplaceChild("rightLeg", CubeListBuilder.create(),
				PartPose.offset(-3.5F, -61.0F, 0.0F));

		PartDefinition rightLeg_r1 = rightLeg.addOrReplaceChild("rightLeg_r1",
				CubeListBuilder.create().texOffs(0, 20).addBox(1.5F, -61.0F, 0.0F, 4.0F, 61.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.5F, 61.0F, 2.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition body = humanoid.addOrReplaceChild(
				"body", CubeListBuilder.create().texOffs(16, 20).addBox(-7.5F, -29.0F, -4.0F, 15.0F,
						29.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -61.0F, 0.0F));

		PartDefinition head = body
				.addOrReplaceChild("head",
						CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -8.0F, -5.0F, 9.0F,
								14.0F, 6.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -29.0F, -4.0F));

		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create(),
				PartPose.offset(9.0F, -26.0F, 0.0F));

		PartDefinition leftArm_r1 = leftArm.addOrReplaceChild("leftArm_r1",
				CubeListBuilder.create().texOffs(62, 20).addBox(-2.0F, -29.0F, -10.5F, 4.0F, 30.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.0F, 26.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition leftArmBone = leftArm.addOrReplaceChild("leftArmBone", CubeListBuilder.create(),
				PartPose.offset(0.0F, 27.0F, 0.0F));

		PartDefinition leftArmBone_r1 = leftArmBone.addOrReplaceChild("leftArmBone_r1",
				CubeListBuilder.create().texOffs(76, 19).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 42.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition leftHand = leftArmBone.addOrReplaceChild(
				"leftHand", CubeListBuilder.create().texOffs(112, 58).addBox(-4.0F, -0.01F, -2.0F, 8.0F,
						11.0F, 17.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.5F, 42.0F, 0.0F));

		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create(),
				PartPose.offset(-9.0F, -26.0F, 0.0F));

		PartDefinition rightArm_r1 = rightArm.addOrReplaceChild("rightArm_r1",
				CubeListBuilder.create().texOffs(62, 20).addBox(-2.0F, -29.0F, 7.5F, 4.0F, 30.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.0F, 26.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition rightArmBone = rightArm.addOrReplaceChild("rightArmBone", CubeListBuilder.create(),
				PartPose.offset(0.0F, 27.0F, 0.0F));

		PartDefinition rightArmBone_r1 = rightArmBone.addOrReplaceChild("rightArmBone_r1",
				CubeListBuilder.create().texOffs(76, 19).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 42.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition rightHand = rightArmBone.addOrReplaceChild(
				"rightHand", CubeListBuilder.create().texOffs(112, 58).addBox(-4.0F, -0.01F, -2.0F,
						8.0F, 11.0F, 17.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.5F, 42.0F, 0.0F));

		PartDefinition eye = body.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(45, 61)
				.addBox(-6.5F, -2.0F, -1.0F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 61)
				.addBox(-5.5F, -4.0F, -1.0F, 11.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 72)
				.addBox(-4.5F, -5.0F, -1.0F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(45, 70)
				.addBox(-3.5F, -6.0F, -1.0F, 7.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(65, 68).addBox(-1.5F, -7.0F, -1.0F, 3.0F, 13.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 184, 117);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		animateWalk(EndersentAnimations.WALK, limbSwing, limbSwingAmount, 2f, 1f);
		animate(entity.attackAnimationState, EndersentAnimations.ATTACK, ageInTicks, 1f);
		animate(entity.deathAnimationState, EndersentAnimations.DEATH, ageInTicks, 1f);
		animate(entity.summonAnimationState, EndersentAnimations.SUMMON_WATCHLINGS, ageInTicks, 1f);
		animate(entity.teleportAnimationState, EndersentAnimations.TELEPORT, ageInTicks, 1f);
	}

	private void applyHeadRotation(T entity, float netHeadYaw, float headPitch, float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -30.0F, 30.0F);
		head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		head.xRot = headPitch * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		humanoid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return humanoid;
	}
}
