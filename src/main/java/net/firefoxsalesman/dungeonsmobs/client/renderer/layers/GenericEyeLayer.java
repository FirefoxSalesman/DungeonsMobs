package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.firefoxsalesman.dungeonslibs.client.ConvenientModel;
import net.firefoxsalesman.dungeonslibs.client.KeyframeEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;

public class GenericEyeLayer<T extends Entity & KeyframeEntity, M extends ConvenientModel<T>> extends EyesLayer<T, M> {
	private RenderType eyes;

	public GenericEyeLayer(RenderLayerParent<T, M> parent, String path) {
		super(parent);
		eyes = RenderType.eyes(GeneralHelper.modLoc(path));
	}

	public RenderType renderType() {
		return eyes;
	}

}
