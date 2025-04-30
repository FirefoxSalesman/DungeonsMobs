package net.firefoxsalesman.dungeonsmobs.lib.integration.curios.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.lib.integration.curios.client.CuriosKeyBindings.*;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosClientIntegration {
	@SubscribeEvent
	public static void setupCuriosKeybindings(RegisterKeyMappingsEvent event) {
		activateArtifact1.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact1);
		activateArtifact2.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact2);
		activateArtifact3.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact3);
	}
}
