package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.animation.GeomancerAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

// Made with Blockbench 5.0.7

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class GeomancerModel<T extends GeomancerEntity> extends ConvenientModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	private final ModelPart root;
	private final ModelPart bipedBody;
	private final ModelPart illagerArms;
	private final ModelPart hands;
	private final ModelPart armourIllagerRightArm2;
	private final ModelPart armorIllagerLeftArm2;
	private final ModelPart armourBody;
	private final ModelPart necklace;
	private final ModelPart bipedCape;
	private final ModelPart bipedArms;
	private final ModelPart bipedArmRight;
	private final ModelPart bipedHandRight;
	private final ModelPart armorBipedRightArm;
	private final ModelPart bipedArmLeft;
	private final ModelPart bipedHandLeft;
	private final ModelPart armorBipedLeftArm;
	private final ModelPart bipedPotionSlot;
	private final ModelPart bipedHeadBaseRotator;
	private final ModelPart bipedHead;
	private final ModelPart rightEyebrow;
	private final ModelPart leftEyebrow;
	private final ModelPart nose;
	private final ModelPart armorBipedHead;
	private final ModelPart beard;
	private final ModelPart bipedLegs;
	private final ModelPart bipedLegLeft;
	private final ModelPart armorBipedLeftFoot;
	private final ModelPart armorBipedLeftLeg;
	private final ModelPart bipedLegRight;
	private final ModelPart armorBipedRightFoot;
	private final ModelPart armourRightLeg;

	public GeomancerModel(ModelPart root) {
		super(GeomancerAnimations.WALK);
		this.root = root.getChild("root");
		this.bipedBody = this.root.getChild("bipedBody");
		this.illagerArms = this.bipedBody.getChild("illagerArms");
		this.hands = this.illagerArms.getChild("hands");
		this.armourIllagerRightArm2 = this.illagerArms.getChild("armourIllagerRightArm2");
		this.armorIllagerLeftArm2 = this.illagerArms.getChild("armorIllagerLeftArm2");
		this.armourBody = this.bipedBody.getChild("armourBody");
		this.necklace = this.armourBody.getChild("necklace");
		this.bipedCape = this.bipedBody.getChild("bipedCape");
		this.bipedArms = this.bipedBody.getChild("bipedArms");
		this.bipedArmRight = this.bipedArms.getChild("bipedArmRight");
		this.bipedHandRight = this.bipedArmRight.getChild("bipedHandRight");
		this.armorBipedRightArm = this.bipedArmRight.getChild("armorBipedRightArm");
		this.bipedArmLeft = this.bipedArms.getChild("bipedArmLeft");
		this.bipedHandLeft = this.bipedArmLeft.getChild("bipedHandLeft");
		this.armorBipedLeftArm = this.bipedArmLeft.getChild("armorBipedLeftArm");
		this.bipedPotionSlot = this.bipedBody.getChild("bipedPotionSlot");
		this.bipedHeadBaseRotator = this.bipedBody.getChild("bipedHeadBaseRotator");
		this.bipedHead = this.bipedHeadBaseRotator.getChild("bipedHead");
		this.rightEyebrow = this.bipedHead.getChild("rightEyebrow");
		this.leftEyebrow = this.bipedHead.getChild("leftEyebrow");
		this.nose = this.bipedHead.getChild("nose");
		this.armorBipedHead = this.bipedHead.getChild("armorBipedHead");
		this.beard = this.bipedHead.getChild("beard");
		this.bipedLegs = this.root.getChild("bipedLegs");
		this.bipedLegLeft = this.bipedLegs.getChild("bipedLegLeft");
		this.armorBipedLeftFoot = this.bipedLegLeft.getChild("armorBipedLeftFoot");
		this.armorBipedLeftLeg = this.bipedLegLeft.getChild("armorBipedLeftLeg");
		this.bipedLegRight = this.bipedLegs.getChild("bipedLegRight");
		this.armorBipedRightFoot = this.bipedLegRight.getChild("armorBipedRightFoot");
		this.armourRightLeg = this.bipedLegRight.getChild("armourRightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition bipedBody = root.addOrReplaceChild("bipedBody", CubeListBuilder.create().texOffs(32, 0)
				.addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(60, 0).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 20.0F, 6.0F,
						new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition illagerArms = bipedBody.addOrReplaceChild("illagerArms", CubeListBuilder.create()
				.texOffs(0, 18).addBox(4.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(32, 18).addBox(-4.0F, 3.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18)
				.addBox(-8.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition hands = illagerArms.addOrReplaceChild("hands", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 7.0F, -2.0F, 0.7418F, 0.0F, 0.0F));

		PartDefinition armourIllagerRightArm2 = illagerArms.addOrReplaceChild(
				"armourIllagerRightArm2", CubeListBuilder.create().texOffs(44, 48).addBox(-4.0F, -2.0F,
						-2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)),
				PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition armorIllagerLeftArm2 = illagerArms.addOrReplaceChild("armorIllagerLeftArm2",
				CubeListBuilder.create().texOffs(44, 48).mirror()
						.addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(1.0F))
						.mirror(false),
				PartPose.offset(4.0F, 0.0F, 0.0F));

		PartDefinition armourBody = bipedBody.addOrReplaceChild("armourBody", CubeListBuilder.create()
				.texOffs(76, 48)
				.addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.75F))
				.texOffs(16, 48)
				.addBox(-4.0F, -12.0F, -3.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.4F))
				.texOffs(68, 32).addBox(-4.0F, -1.25F, -3.0F, 8.0F, 9.0F, 6.0F,
						new CubeDeformation(0.4F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition necklace = armourBody.addOrReplaceChild("necklace", CubeListBuilder.create()
				.texOffs(0, 32).addBox(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(2.0F, 1.5F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(4.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(4.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-6.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(3.0F, 0.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-5.0F, 0.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(1.0F, 1.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-3.0F, 1.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-1.0F, 2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-6.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-4.0F, 1.5F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition bipedCape = bipedBody.addOrReplaceChild(
				"bipedCape", CubeListBuilder.create().texOffs(96, 32).addBox(-5.0F, 0.0F, 0.0F, 10.0F,
						16.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, 3.0F));

		PartDefinition bipedArms = bipedBody.addOrReplaceChild("bipedArms", CubeListBuilder.create(),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition bipedArmRight = bipedArms.addOrReplaceChild("bipedArmRight",
				CubeListBuilder.create().texOffs(104, 0).mirror()
						.addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition bipedHandRight = bipedArmRight.addOrReplaceChild("bipedHandRight",
				CubeListBuilder.create(), PartPose.offset(-2.0F, 11.0F, 0.0F));

		PartDefinition armorBipedRightArm = bipedArmRight.addOrReplaceChild(
				"armorBipedRightArm", CubeListBuilder.create().texOffs(44, 48).addBox(-4.0F, -2.0F,
						-2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bipedArmLeft = bipedArms.addOrReplaceChild(
				"bipedArmLeft", CubeListBuilder.create().texOffs(104, 0).addBox(0.0F, -2.0F, -2.0F,
						4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, 0.0F, 0.0F));

		PartDefinition bipedHandLeft = bipedArmLeft.addOrReplaceChild("bipedHandLeft", CubeListBuilder.create(),
				PartPose.offset(2.0F, 11.0F, 0.0F));

		PartDefinition armorBipedLeftArm = bipedArmLeft.addOrReplaceChild("armorBipedLeftArm",
				CubeListBuilder.create().texOffs(44, 48).mirror()
						.addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(1.0F))
						.mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bipedPotionSlot = bipedBody.addOrReplaceChild(
				"bipedPotionSlot", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 0.0F,
						0.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, -1.0F, 0.0F));

		PartDefinition bipedHeadBaseRotator = bipedBody.addOrReplaceChild("bipedHeadBaseRotator",
				CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bipedHead = bipedHeadBaseRotator.addOrReplaceChild(
				"bipedHead", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F,
						10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition rightEyebrow = bipedHead.addOrReplaceChild("rightEyebrow", CubeListBuilder.create()
				.texOffs(0, 6).addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(1, 4).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, -4.0F, -5.0F));

		PartDefinition leftEyebrow = bipedHead.addOrReplaceChild("leftEyebrow", CubeListBuilder.create()
				.texOffs(0, 6).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(1, 4).addBox(0.0F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, -4.0F, -5.0F));

		PartDefinition nose = bipedHead
				.addOrReplaceChild("nose",
						CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F,
								2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition armorBipedHead = bipedHead.addOrReplaceChild(
				"armorBipedHead", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F,
						8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)),
				PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition beard = bipedHead.addOrReplaceChild(
				"beard", CubeListBuilder.create().texOffs(120, 0).addBox(-3.0F, -1.0F, -0.9F, 6.0F,
						5.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -1.0F, -4.0F));

		PartDefinition bipedLegs = root.addOrReplaceChild("bipedLegs", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bipedLegLeft = bipedLegs.addOrReplaceChild("bipedLegLeft",
				CubeListBuilder.create().texOffs(88, 0).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(2.0F, 6.0F, 0.0F));

		PartDefinition armorBipedLeftFoot = bipedLegLeft.addOrReplaceChild("armorBipedLeftFoot",
				CubeListBuilder.create().texOffs(60, 48).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.5F))
						.mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armorBipedLeftLeg = bipedLegLeft.addOrReplaceChild("armorBipedLeftLeg",
				CubeListBuilder.create().texOffs(0, 48).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(1.0F))
						.mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bipedLegRight = bipedLegs.addOrReplaceChild(
				"bipedLegRight", CubeListBuilder.create().texOffs(88, 0).addBox(-2.0F, 0.0F, -2.0F,
						4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 6.0F, 0.0F));

		PartDefinition armorBipedRightFoot = bipedLegRight.addOrReplaceChild(
				"armorBipedRightFoot", CubeListBuilder.create().texOffs(60, 48).addBox(-2.0F, 0.0F,
						-2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armourRightLeg = bipedLegRight.addOrReplaceChild(
				"armourRightLeg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F,
						4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 134, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.idleAnimationState, GeomancerAnimations.IDLE, ageInTicks, 1f);
		animate(entity.summonAnimationState, GeomancerAnimations.SUMMON, ageInTicks, 1f);
		animate(entity.celebrationAnimationState, GeomancerAnimations.CELEBRATE, ageInTicks, 1f);
	}

	@Override
	public ModelPart getHead() {
		return bipedHead;
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		ModelPart hand = pSide == HumanoidArm.RIGHT ? bipedArmRight : bipedArmLeft;
		hand.translateAndRotate(pPoseStack);
		pPoseStack.translate(.125, .1875, .5);
	}
}
