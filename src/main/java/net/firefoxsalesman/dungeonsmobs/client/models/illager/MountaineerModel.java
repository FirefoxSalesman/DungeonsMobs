package net.firefoxsalesman.dungeonsmobs.client.models.illager;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.firefoxsalesman.dungeonsmobs.lib.client.ConvenientModel;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.animation.MountaineerAnimations;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class MountaineerModel<T extends MountaineerEntity> extends ConvenientModel<T> implements ArmedModel {
	private final ModelPart root;
	private final ModelPart hip;
	private final ModelPart torso;
	private final ModelPart spine;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart eyepupil0;
	private final ModelPart eyelid0;
	private final ModelPart eyebrow0;
	private final ModelPart eyebrow1;
	private final ModelPart eyepupil1;
	private final ModelPart eyelid1;
	private final ModelPart arm0;
	private final ModelPart arm1;
	private final ModelPart hand;
	private final ModelPart rightitem;
	private final ModelPart weapon;
	private final ModelPart body;
	private final ModelPart climbbody;
	private final ModelPart leg0;
	private final ModelPart leg1;

	public MountaineerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.hip = this.root.getChild("hip");
		this.torso = this.hip.getChild("torso");
		this.spine = this.torso.getChild("spine");
		this.neck = this.spine.getChild("neck");
		this.head = this.neck.getChild("head");
		this.nose = this.head.getChild("nose");
		this.eyepupil0 = this.head.getChild("eyepupil0");
		this.eyelid0 = this.head.getChild("eyelid0");
		this.eyebrow0 = this.head.getChild("eyebrow0");
		this.eyebrow1 = this.head.getChild("eyebrow1");
		this.eyepupil1 = this.head.getChild("eyepupil1");
		this.eyelid1 = this.head.getChild("eyelid1");
		this.arm0 = this.spine.getChild("arm0");
		this.arm1 = this.spine.getChild("arm1");
		this.hand = this.arm1.getChild("hand");
		this.rightitem = this.hand.getChild("rightitem");
		this.weapon = this.rightitem.getChild("weapon");
		this.body = this.torso.getChild("body");
		this.climbbody = this.torso.getChild("climbbody");
		this.leg0 = this.hip.getChild("leg0");
		this.leg1 = this.hip.getChild("leg1");
		animations.put("attack", MountaineerAnimations.ATTACK);
		animations.put("run", MountaineerAnimations.RUN);
		animations.put("walk", MountaineerAnimations.WALK);
		animations.put("idle", MountaineerAnimations.IDLE);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 12.022F));

		PartDefinition torso = hip.addOrReplaceChild("torso", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.379F, 0.082F));

		PartDefinition spine = torso.addOrReplaceChild("spine", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 9.6F));

		PartDefinition neck = spine.addOrReplaceChild("neck", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 2.293F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(-0.006F, 0.0F, 5.298F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 10.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -0.3F, -1.5708F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create(),
				PartPose.offset(0.0F, -4.379F, -3.295F));

		PartDefinition cube_r2 = nose.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.625F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition eyepupil0 = head.addOrReplaceChild(
				"eyepupil0", CubeListBuilder.create().texOffs(1, 6).addBox(-0.425F, -0.028F, 0.0F, 1.0F,
						0.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.577F, -4.0F, -2.295F));

		PartDefinition eyelid0 = head.addOrReplaceChild(
				"eyelid0", CubeListBuilder.create().texOffs(0, 7).addBox(-0.917F, -0.01F, 0.0F, 2.0F,
						0.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.077F, -4.0F, -1.295F));

		PartDefinition eyebrow0 = head.addOrReplaceChild(
				"eyebrow0", CubeListBuilder.create().texOffs(-2, 4).addBox(-1.925F, -0.05F, 0.0F, 3.0F,
						0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.077F, -4.0F, -1.295F));

		PartDefinition eyebrow1 = head.addOrReplaceChild("eyebrow1",
				CubeListBuilder.create().texOffs(-2, 4).mirror()
						.addBox(-0.925F, -0.05F, 0.0F, 3.0F, 0.0F, 2.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offset(1.923F, -4.0F, -1.295F));

		PartDefinition eyepupil1 = head.addOrReplaceChild(
				"eyepupil1", CubeListBuilder.create().texOffs(1, 6).addBox(-0.425F, -0.028F, 0.0F, 1.0F,
						0.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.423F, -4.0F, -2.295F));

		PartDefinition eyelid1 = head.addOrReplaceChild(
				"eyelid1", CubeListBuilder.create().texOffs(0, 7).addBox(-0.917F, -0.01F, 0.0F, 2.0F,
						0.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.923F, -4.0F, -1.295F));

		PartDefinition arm0 = spine.addOrReplaceChild("arm0", CubeListBuilder.create(),
				PartPose.offset(4.0F, 0.0F, 0.061F));

		PartDefinition cube_r3 = arm0.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 46)
				.addBox(-1.0F, -3.379F, -1.765F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(48, 46)
				.addBox(-1.0F, -3.379F, -1.765F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, -0.25F, -1.15F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = arm0.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(23, 54).addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F,
						new CubeDeformation(0.5F)),
				PartPose.offsetAndRotation(2.625F, -0.015F, 0.246F, -1.5708F, 0.0F, 0.0F));

		PartDefinition arm1 = spine.addOrReplaceChild("arm1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-4.0F, 0.0F, 0.061F, -3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r5 = arm1.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(23, 54).mirror()
						.addBox(-3.0F, -3.0F, -2.0F, 4.0F, 5.0F, 5.0F,
								new CubeDeformation(0.5F))
						.mirror(false),
				PartPose.offsetAndRotation(-1.375F, 0.485F, 0.271F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r6 = arm1.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(32, 46).mirror()
						.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F,
								new CubeDeformation(0.5F))
						.mirror(false),
				PartPose.offsetAndRotation(-2.0F, -0.015F, -0.229F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r7 = arm1.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(48, 46).mirror()
						.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offsetAndRotation(-2.0F, -0.015F, 3.771F, 1.5708F, 0.0F, 0.0F));

		PartDefinition hand = arm1.addOrReplaceChild("hand", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.8F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition rightitem = hand.addOrReplaceChild("rightitem", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition weapon = rightitem.addOrReplaceChild("weapon", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.8F, -6.379F, 0.235F, -1.5708F, -0.7854F, -1.5708F));

		PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1",
				CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -2.5F, 8.0F, 12.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.379F, -12.104F, -1.5708F, 0.0F, 0.0F));

		PartDefinition climbbody = torso.addOrReplaceChild("climbbody", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r2 = climbbody.addOrReplaceChild("body_r2",
				CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -24.0F, -2.5F, 8.0F, 13.0F, 6.0F,
						new CubeDeformation(0.25F)),
				PartPose.offsetAndRotation(0.0F, -0.379F, -12.104F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r8 = climbbody.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(50, 17)
				.addBox(4.0F, 7.0F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(38, 15).mirror()
				.addBox(13.0F, 5.0F, -1.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-9.8F, 4.15F, 12.4F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r9 = climbbody.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 7.0F, 11.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.8F, 4.15F, 10.4F, -1.5708F, 0.0F, 0.0F));

		PartDefinition leg0 = hip.addOrReplaceChild("leg0", CubeListBuilder.create(),
				PartPose.offset(2.5F, 0.0F, 0.052F));

		PartDefinition cube_r10 = leg0.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(27, 38)
				.addBox(-2.5188F, -3.3438F, -1.8813F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(44, 25)
				.addBox(-1.9438F, -1.7688F, -0.6563F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.46F)),
				PartPose.offsetAndRotation(-0.4562F, -1.7672F, -6.3862F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r11 = leg0.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -3.379F, -1.765F, 4.0F, 12.0F,
						4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.4F, -0.246F, -3.409F, -1.5708F, 0.0F, 0.0F));

		PartDefinition leg1 = hip.addOrReplaceChild("leg1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.5F, 0.0F, 0.017F, -3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r12 = leg1.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(27, 38).addBox(-2.575F, -5.275F, -2.4875F, 5.0F, 3.0F,
						2.0F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.55F, 1.1265F, 8.305F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r13 = leg1.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(44, 25).addBox(-2.0F, -3.7F, -2.0375F, 4.0F, 7.0F,
						4.0F, new CubeDeformation(0.46F)),
				PartPose.offsetAndRotation(0.55F, 0.3515F, 8.305F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r14 = leg1.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.4F, -0.011F, 6.005F, 1.5708F, 0.0F, -3.1416F));

		return LayerDefinition.create(meshdefinition, 64, 64);
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
		hip.translateAndRotate(pPoseStack);
		torso.translateAndRotate(pPoseStack);
		spine.translateAndRotate(pPoseStack);
		arm1.translateAndRotate(pPoseStack);
		hand.translateAndRotate(pPoseStack);
	}

	protected void applyHeadRotation(T entity, float netHeadYaw, float headPitch,
			float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -30.0F, 30.0F);
		getHead().xRot = netHeadYaw * ((float) Math.PI / 180F);
		getHead().zRot = headPitch * ((float) Math.PI / 180F);
	}
}
