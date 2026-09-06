package net.firefoxsalesman.dungeonsmobs.commands;

import java.util.function.Consumer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonCommandHelper {

	public static void register(CommandDispatcher<CommandSourceStack> sourceStack,
			CommandBuildContext buildContext, SimpleCommandExceptionType errorFailed,
			SimpleCommandExceptionType errorDuplicateUuid, SimpleCommandExceptionType invalidPosition,
			String commandName, Consumer<LivingEntity> consumer) {
		sourceStack.register(Commands.literal(commandName).requires((p_138819_) -> {
			return p_138819_.hasPermission(2);
		}).then(Commands.argument("entity",
				ResourceArgument.resource(buildContext, Registries.ENTITY_TYPE))
				.suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((p_138832_) -> {
					return spawnEntity(p_138832_.getSource(),
							ResourceArgument.getSummonableEntityType(p_138832_, "entity"),
							p_138832_.getSource().getPosition(), new CompoundTag(), true,
							errorFailed, errorDuplicateUuid, invalidPosition, commandName,
							consumer);
				}).then(Commands.argument("pos", Vec3Argument.vec3()).executes((context) -> {
					return spawnEntity(context.getSource(),
							ResourceArgument.getSummonableEntityType(context, "entity"),
							Vec3Argument.getVec3(context, "pos"), new CompoundTag(),
							true, errorFailed, errorDuplicateUuid, invalidPosition,
							commandName, consumer);
				}).then(Commands.argument("nbt", CompoundTagArgument.compoundTag())
						.executes((p_138817_) -> {
							return spawnEntity(p_138817_.getSource(),
									ResourceArgument.getSummonableEntityType(
											p_138817_, "entity"),
									Vec3Argument.getVec3(p_138817_, "pos"),
									CompoundTagArgument.getCompoundTag(p_138817_,
											"nbt"),
									false, errorFailed, errorDuplicateUuid,
									invalidPosition, commandName, consumer);
						})))));
	}

	private static Entity createEntity(CommandSourceStack commandSource,
			Holder.Reference<EntityType<?>> resourceLocation,
			Vec3 vec, CompoundTag tag, boolean randomizeProperties, SimpleCommandExceptionType errorFailed,
			SimpleCommandExceptionType errorDuplicateUuid, SimpleCommandExceptionType invalidPosition,
			Consumer<LivingEntity> consumer)
			throws CommandSyntaxException {
		BlockPos blockpos = BlockPos.containing(vec);
		if (!Level.isInSpawnableBounds(blockpos)) {
			throw invalidPosition.create();
		} else {
			CompoundTag compoundtag = tag.copy();
			compoundtag.putString("id", resourceLocation.key().location().toString());
			ServerLevel serverlevel = commandSource.getLevel();
			Entity entity = EntityType.loadEntityRecursive(compoundtag, serverlevel, (newPos) -> {
				newPos.moveTo(vec.x, vec.y, vec.z, newPos.getYRot(),
						newPos.getXRot());
				return newPos;
			});
			if (entity == null) {
				throw errorFailed.create();
			} else {
				if (randomizeProperties && entity instanceof Mob) {
					((Mob) entity).finalizeSpawn(commandSource.getLevel(),
							commandSource.getLevel().getCurrentDifficultyAt(
									entity.blockPosition()),
							MobSpawnType.COMMAND, (SpawnGroupData) null,
							(CompoundTag) null);
				}
				if (entity instanceof LivingEntity livingEntity) {
					consumer.accept(livingEntity);
				}
				if (!serverlevel.tryAddFreshEntityWithPassengers(entity)) {
					throw errorDuplicateUuid.create();
				} else {
					return entity;
				}
			}
		}
	}

	private static int spawnEntity(CommandSourceStack commandSource,
			Holder.Reference<EntityType<?>> resourceLocation,
			Vec3 vec, CompoundTag tag, boolean p_138825_, SimpleCommandExceptionType errorFailed,
			SimpleCommandExceptionType errorDuplicateUuid, SimpleCommandExceptionType invalidPosition,
			String commandName, Consumer<LivingEntity> consumer)
			throws CommandSyntaxException {
		Entity entity = createEntity(commandSource, resourceLocation, vec, tag, p_138825_, errorFailed,
				errorDuplicateUuid, invalidPosition, consumer);
		commandSource.sendSuccess(() -> {
			return Component.translatable("commands." + commandName + ".success", entity.getDisplayName());
		}, true);
		return 1;
	}
}
