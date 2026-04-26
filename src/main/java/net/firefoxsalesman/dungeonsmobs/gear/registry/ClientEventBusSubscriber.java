package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;

import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.BeamEntityRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.ghosts.SoulWizardRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.projectiles.SoulWizardOrbRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.BuzzyNestRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.FireworksDisplayRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.TotemOfRegenerationRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.TotemOfShieldingRenderer;
import net.firefoxsalesman.dungeonsmobs.gear.client.renderer.totem.TotemOfSoulProtectionRenderer;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityTypeInit.BUZZY_NEST.get(), BuzzyNestRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.FIREWORKS_DISPLAY.get(), FireworksDisplayRenderer::new);

		event.registerEntityRenderer(EntityTypeInit.TOTEM_OF_SHIELDING.get(), TotemOfShieldingRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.TOTEM_OF_REGENERATION.get(),
				TotemOfRegenerationRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.TOTEM_OF_SOUL_PROTECTION.get(),
				TotemOfSoulProtectionRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.BEAM_ENTITY.get(), BeamEntityRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.SOUL_WIZARD.get(), SoulWizardRenderer::new);
		event.registerEntityRenderer(EntityTypeInit.SOUL_WIZARD_ORB.get(), SoulWizardOrbRenderer::new);
	}
}
