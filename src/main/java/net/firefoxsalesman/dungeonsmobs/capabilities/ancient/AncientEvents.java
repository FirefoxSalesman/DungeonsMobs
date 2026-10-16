package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.items.GildedItemHelper;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.firefoxsalesman.dungeonsmobs.network.message.AncientMessage;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.firefoxsalesman.dungeonsmobs.utils.LootTableHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class AncientEvents {

	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		if (player instanceof ServerPlayer && target != null && player != null) {
			Ancient cap = AncientHelper.getAncientCapability(target);
			if (cap.isAncient()) {
				NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
						new AncientMessage(target.getId(), cap.isAncient()));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entityLiving = event.getEntity();
		if (!entityLiving.level().isClientSide) {
			Ancient cap = AncientHelper.getAncientCapability(entityLiving);
			if (cap.isAncient() && cap.getBossInfo() != null) {
				if (entityLiving.isAlive()) {
					List<ServerPlayer> nearbyEntities = entityLiving.level().getNearbyEntities(
							ServerPlayer.class,
							TargetingConditions.forNonCombat().range(20.0D)
									.ignoreInvisibilityTesting(),
							entityLiving,
							entityLiving.getBoundingBox().inflate(20D, 10D, 20D));
					nearbyEntities.forEach(
							playerEntity -> cap.getBossInfo().addPlayer(playerEntity));
					List<ServerPlayer> trackingPlayers = new ObjectArrayList<>(
							cap.getBossInfo().getPlayers());
					List<ServerPlayer> furtherEntities = entityLiving.level().getNearbyEntities(
							ServerPlayer.class,
							TargetingConditions.forNonCombat().range(50.0D)
									.ignoreInvisibilityTesting(),
							entityLiving,
							entityLiving.getBoundingBox().inflate(50D, 20D, 50D));
					trackingPlayers.forEach(playerEntity -> {
						if (!furtherEntities.contains(playerEntity)) {
							cap.getBossInfo().removePlayer(playerEntity);
						}
					});

				} else {
					cap.getBossInfo().removeAllPlayers();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Ancient cap = AncientHelper.getAncientCapability(livingEntity);
		if (cap.isAncient() && cap.getBossInfo() != null) {
			cap.getBossInfo().setProgress(livingEntity.getHealth() / livingEntity.getMaxHealth());
		}
	}

	@SubscribeEvent
	public static void onJoinLevel(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Mob mob) {
			Ancient cap = AncientHelper.getAncientCapability(mob);
			if (cap.isAncient())
				cap.initiateBossBar(mob, cap.getDisplayName());
		}
	}

	@SubscribeEvent
	public static void onLivingDeathEvent(LivingDeathEvent event) {
		LivingEntity victim = event.getEntity();
		if (!victim.level().isClientSide && AncientHelper.getAncientCapability(victim).isAncient())
			dropGildedItem(
					LootTableHelper.generateItemStack((ServerLevel) victim.level(),
							victim.blockPosition(),
							GeneralHelper.modLoc("entities/gilded_entity"),
							victim.getRandom()),
					event.getEntity());
	}

	private static void dropGildedItem(ItemStack item, LivingEntity defender) {
		if (defender.level().isClientSide())
			return;
		ItemStack gildedItem = GildedItemHelper.getGildedItem(defender.getRandom(), item);
		ItemEntity gildedItemDrop = new ItemEntity(defender.level(), defender.getX(), defender.getY(),
				defender.getZ(),
				gildedItem);
		defender.level().addFreshEntity(gildedItemDrop);
	}
}
