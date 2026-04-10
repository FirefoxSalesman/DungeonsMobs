package net.firefoxsalesman.dungeonsmobs.lib.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;

import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.SoulOrbRenderer;
import net.firefoxsalesman.dungeonsmobs.lib.entities.LibEntityTypes;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(LibEntityTypes.SOUL_ORB.get(), SoulOrbRenderer::new);
	}

	// @SubscribeEvent
	// public static void registerArmorRenderers(final
	// EntityRenderersEvent.AddLayers event) {
	// GeoArmorRenderer.registerArmorRenderer(ArmorGear.class,
	// ArmorGearRenderer::new);
	// }
}
