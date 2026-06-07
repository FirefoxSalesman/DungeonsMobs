package net.firefoxsalesman.dungeonsmobs.lib.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.firefoxsalesman.dungeonsmobs.lib.entities.elite.EliteMobEvents;
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

public class SummonEliteCommand {

	private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.failed"));
	private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.failed.uuid"));
	private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.invalidPosition"));

	public static void register(CommandDispatcher<CommandSourceStack> sourceStack,
			CommandBuildContext buildContext) {
		sourceStack.register(Commands.literal("summonelite").requires((p_138819_) -> {
			return p_138819_.hasPermission(2);
		}).then(Commands.argument("entity",
				ResourceArgument.resource(buildContext, Registries.ENTITY_TYPE))
				.suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((p_138832_) -> {
					return spawnEntity(p_138832_.getSource(),
							ResourceArgument.getSummonableEntityType(p_138832_, "entity"),
							p_138832_.getSource().getPosition(), new CompoundTag(), true);
				}).then(Commands.argument("pos", Vec3Argument.vec3()).executes((context) -> {
					return spawnEntity(context.getSource(),
							ResourceArgument.getSummonableEntityType(context, "entity"),
							Vec3Argument.getVec3(context, "pos"), new CompoundTag(),
							true);
				}).then(Commands.argument("nbt", CompoundTagArgument.compoundTag())
						.executes((p_138817_) -> {
							return spawnEntity(p_138817_.getSource(),
									ResourceArgument.getSummonableEntityType(
											p_138817_, "entity"),
									Vec3Argument.getVec3(p_138817_, "pos"),
									CompoundTagArgument.getCompoundTag(p_138817_,
											"nbt"),
									false);
						})))));
	}

	private static Entity createEntity(CommandSourceStack commandSource,
			Holder.Reference<EntityType<?>> resourceLocation,
			Vec3 vec, CompoundTag tag, boolean randomizeProperties) throws CommandSyntaxException {
		BlockPos blockpos = BlockPos.containing(vec);
		if (!Level.isInSpawnableBounds(blockpos)) {
			throw INVALID_POSITION.create();
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
				throw ERROR_FAILED.create();
			} else {
				if (randomizeProperties && entity instanceof Mob) {
					((Mob) entity).finalizeSpawn(commandSource.getLevel(),
							commandSource.getLevel().getCurrentDifficultyAt(
									entity.blockPosition()),
							MobSpawnType.COMMAND, (SpawnGroupData) null,
							(CompoundTag) null);
				}
				if (entity instanceof LivingEntity livingEntity) {
					EliteMobEvents.makeElite(livingEntity);
				}
				if (!serverlevel.tryAddFreshEntityWithPassengers(entity)) {
					throw ERROR_DUPLICATE_UUID.create();
				} else {
					return entity;
				}
			}
		}
	}

	private static int spawnEntity(CommandSourceStack commandSource,
			Holder.Reference<EntityType<?>> resourceLocation,
			Vec3 vec, CompoundTag tag, boolean p_138825_) throws CommandSyntaxException {
		Entity entity = createEntity(commandSource, resourceLocation, vec, tag, p_138825_);
		commandSource.sendSuccess(() -> {
			return Component.translatable("commands.summonelite.success", entity.getDisplayName());
		}, true);
		return 1;
	}
}
