package net.firefoxsalesman.dungeonsmobs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.firefoxsalesman.dungeonsmobs.capabilities.ancient.AncientHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class SummonUniqueAncientCommand {

	private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(
			Component.translatable("commands.summonuniqueancient.failed"));
	private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(
			Component.translatable("commands.summonuniqueancient.failed.uuid"));
	private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
			Component.translatable("commands.summonuniqueancient.invalidPosition"));

	public static void register(CommandDispatcher<CommandSourceStack> sourceStack,
			CommandBuildContext buildContext) {
		SummonCommandHelper.register(sourceStack, buildContext, ERROR_FAILED,
				ERROR_DUPLICATE_UUID, INVALID_POSITION, "summonuniqueancient",
				AncientHelper::makeUniqueAncient);
	}

}
