package net.firefoxsalesman.dungeonsmobs.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class CommandEvents {
	@SubscribeEvent
	public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
			SummonAncientCommand.register(commandDispatcher, event.getBuildContext());
		}
	}
}
