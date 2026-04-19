package net.firefoxsalesman.dungeonsmobs.client.models.redstone;

import net.firefoxsalesman.dungeonsmobs.client.animation.RedstoneCubeAnimations;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneCubeEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

// Made with Blockbench 5.0.7

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class RedstoneCubeModel<T extends RedstoneCubeEntity> extends ConvenientModel<T> {
	private final ModelPart redstonecube;

	public RedstoneCubeModel(ModelPart root) {
		super(RedstoneCubeAnimations.WALK);
		this.redstonecube = root.getChild("redstonecube");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition redstonecube = partdefinition.addOrReplaceChild(
				"redstonecube", CubeListBuilder.create().texOffs(0, 64).addBox(-16.0F, -32.0F, -16.0F,
						32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public ModelPart root() {
		return redstonecube;
	}

	@Override
	public ModelPart getHead() {
		return redstonecube;
	}
}
