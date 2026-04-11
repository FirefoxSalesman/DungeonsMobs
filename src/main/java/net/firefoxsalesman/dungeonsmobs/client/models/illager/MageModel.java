package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import net.firefoxsalesman.dungeonsmobs.client.animation.MageAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageEntity;
import net.firefoxsalesman.dungeonsmobs.interfaces.AnimatedMage;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.monster.AbstractIllager;

// Made with Blockbench 5.1.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class MageModel<T extends AbstractIllager & AnimatedMage> extends ConvenientModel<T> {
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
	private final ModelPart righteyebrows;
	private final ModelPart lefteyebrows;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public MageModel(ModelPart root) {
		super(MageAnimations.WALK);
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
		this.righteyebrows = this.head.getChild("righteyebrows");
		this.lefteyebrows = this.head.getChild("lefteyebrows");
		this.left_leg = this.everything.getChild("left_leg");
		this.right_leg = this.everything.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition everything = partdefinition.addOrReplaceChild("everything", CubeListBuilder.create(),
				PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition body = everything.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 0)
				.addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(60, 0).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition Cape = body.addOrReplaceChild("Cape",
				CubeListBuilder.create().texOffs(106, 46).addBox(-5.5F, -0.0428F, -0.6526F, 10.0F,
						17.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.5F, -12.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild(
				"left_arm", CubeListBuilder.create().texOffs(104, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(104, 16).mirror()
						.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightHand = right_arm.addOrReplaceChild("rightHand", CubeListBuilder.create(),
				PartPose.offset(-1.0F, 11.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -10.0F, -3.3098F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(48, 28)
				.addBox(-4.0F, -10.0F, -3.3098F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(24, 0).addBox(-1.0F, -3.0F, -5.3098F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, -0.75F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

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

		PartDefinition left_leg = everything.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(88, 0).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(2.0F, -10.0F, 0.0F));

		PartDefinition right_leg = everything.addOrReplaceChild(
				"right_leg", CubeListBuilder.create().texOffs(88, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F,
						12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		animate(entity.getIdleState(), MageAnimations.IDLE, ageInTicks, 1f);
		animate(entity.getCelebrateState(), MageAnimations.CELEBRATE, ageInTicks, 1f);
		animate(entity.getAttackState(), MageAnimations.THROW, ageInTicks, 1f);
		animate(entity.getVanishState(), MageAnimations.VANISH, ageInTicks, 1f);
		animate(entity.getAppearState(), MageAnimations.APPEAR, ageInTicks, 1f);
	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public ModelPart root() {
		return everything;
	}
}
