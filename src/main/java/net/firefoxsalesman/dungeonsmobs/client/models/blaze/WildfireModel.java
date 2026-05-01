package net.firefoxsalesman.dungeonsmobs.client.models.blaze;

import net.firefoxsalesman.dungeonsmobs.client.animation.WildfireAnimations;
import net.firefoxsalesman.dungeonsmobs.entity.blaze.WildfireEntity;
import net.firefoxsalesman.dungeonsmobs.lib.client.ConvenientModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class WildfireModel<T extends WildfireEntity> extends ConvenientModel<T> {
	public final ModelPart everything;
	public final ModelPart head;
	public final ModelPart armorHead;
	public final ModelPart rod;
	public final ModelPart shields;
	public final ModelPart shield2;
	public final ModelPart shield1;
	public final ModelPart shield3;
	public final ModelPart shield4;

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
		animations.put("idle", WildfireAnimations.wildfire_idle);
		animations.put("shockwave", WildfireAnimations.wildfire_shockwave);
		animations.put("shoot", WildfireAnimations.wildfire_shoot);
		animations.put("summon", WildfireAnimations.wildfire_summon);
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
	public ModelPart root() {
		return everything;
	}

	public void setAllVisible(boolean pVisible) {
		this.head.visible = pVisible;
		this.armorHead.visible = pVisible;
		this.rod.visible = pVisible;
		this.shields.visible = pVisible;
		this.shield1.visible = pVisible;
		this.shield2.visible = pVisible;
		this.shield3.visible = pVisible;
		this.shield4.visible = pVisible;
	}

	@Override
	public ModelPart getHead() {
		return head;
	}
}
