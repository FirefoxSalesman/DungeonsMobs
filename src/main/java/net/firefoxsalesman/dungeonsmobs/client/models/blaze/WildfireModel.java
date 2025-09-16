package net.firefoxsalesman.dungeonsmobs.client.models.blaze;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.animation.WildfireAnimations;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class WildfireModel<T extends WildfireEntity> extends HierarchicalModel<T> {
	private final ModelPart everything;
	private final ModelPart head;
	private final ModelPart armorHead;
	private final ModelPart rod;
	private final ModelPart shields;
	private final ModelPart shield2;
	private final ModelPart shield1;
	private final ModelPart shield3;
	private final ModelPart shield4;

	public WildfireModel(ModelPart root) {
		this.everything = root.getChild("everything");
		this.head = this.everything.getChild("head");
		this.armorHead = this.head.getChild("armorHead");
		this.rod = this.everything.getChild("rod");
		this.shields = this.everything.getChild("shields");
		this.shield2 = this.shields.getChild("shield2");
		this.shield1 = this.shields.getChild("shield1");
		this.shield3 = this.shields.getChild("shield3");
		this.shield4 = this.shields.getChild("shield4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition everything = partdefinition.addOrReplaceChild("everything", CubeListBuilder.create(),
				PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition head = everything
				.addOrReplaceChild("head",
						CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F,
								8.0F, 8.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition armorHead = head.addOrReplaceChild(
				"armorHead", CubeListBuilder.create().texOffs(32, 48).addBox(-4.0F, -8.0F, -4.0F, 8.0F,
						8.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rod = everything
				.addOrReplaceChild("rod",
						CubeListBuilder.create().texOffs(2, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F,
								20.0F, 4.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition shields = everything.addOrReplaceChild("shields", CubeListBuilder.create(),
				PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition shield2 = shields.addOrReplaceChild("shield2",
				CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -8.0F, -1.0F, 10.0F, 17.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.0F, -13.0F, 0.0F, 0.0F, 1.5708F, 0.3054F));

		PartDefinition shield1 = shields.addOrReplaceChild("shield1",
				CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -8.0F, -1.0F, 10.0F, 17.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -13.0F, -13.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition shield3 = shields.addOrReplaceChild("shield3",
				CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -8.0F, -1.0F, 10.0F, 17.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -13.0F, 13.0F, 2.8362F, 0.0F, 3.1416F));

		PartDefinition shield4 = shields.addOrReplaceChild("shield4",
				CubeListBuilder.create().texOffs(20, 16).addBox(-5.0F, -8.0F, -1.0F, 10.0F, 17.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.0F, -13.0F, 0.0F, 0.0F, -1.5708F, -0.3054F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		everything.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private void applyHeadRotation(T entity, float netHeadYaw, float headPitch, float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -30.0F, 30.0F);
		head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		head.xRot = headPitch * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return everything;
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
			float pHeadPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(pEntity, pNetHeadYaw, pHeadPitch, pAgeInTicks);
		animate(pEntity.idleAnimationState, WildfireAnimations.wildfire_idle, pAgeInTicks, 1f);
	}
}
