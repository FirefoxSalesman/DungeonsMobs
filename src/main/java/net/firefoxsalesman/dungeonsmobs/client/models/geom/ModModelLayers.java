package net.firefoxsalesman.dungeonsmobs.client.models.geom;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.SunkenSkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
	public static ModelLayerLocation SUNKEN_SKELETON = makeModelLayerLocation("sunken_skeleton_model");

	private static ModelLayerLocation makeModelLayerLocation(String name) {
		return new ModelLayerLocation(new ResourceLocation(DungeonsMobs.MOD_ID + name),
				name);
	}

	@SubscribeEvent
	public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SUNKEN_SKELETON, SunkenSkeletonModel::createBodyLayer);
	}
}
