package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;

import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.TotemOfRegenerationRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.TotemOfShieldingRenderer;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityTypeInit.TOTEM_OF_SHIELDING.get(), TotemOfShieldingRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.TOTEM_OF_REGENERATION.get(),
				TotemOfRegenerationRenderer::new);
	}
}
