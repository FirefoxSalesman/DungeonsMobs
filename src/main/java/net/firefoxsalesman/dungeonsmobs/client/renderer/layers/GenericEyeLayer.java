package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ConvenientModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GenericEyeLayer<T extends Entity, M extends ConvenientModel<T>> extends EyesLayer<T, M> {
	private RenderType eyes;

	public GenericEyeLayer(RenderLayerParent<T, M> parent, String path) {
		super(parent);
		eyes = RenderType.eyes(new ResourceLocation(DungeonsMobs.MOD_ID, path));
	}

	public RenderType renderType() {
		return eyes;
	}

}
