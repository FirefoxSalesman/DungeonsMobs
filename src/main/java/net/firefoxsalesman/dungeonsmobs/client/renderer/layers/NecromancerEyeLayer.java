package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.NecromancerModel;
import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NecromancerEyeLayer<T extends NecromancerEntity> extends EyesLayer<T, NecromancerModel<T>> {
	private static final RenderType ENDERMAN_EYES = RenderType
			.eyes(new ResourceLocation(DungeonsMobs.MOD_ID,
					"textures/entity/skeleton/necromancer_eyes.png"));

	public NecromancerEyeLayer(RenderLayerParent<T, NecromancerModel<T>> parent) {
		super(parent);
	}

	public RenderType renderType() {
		return ENDERMAN_EYES;
	}
}
