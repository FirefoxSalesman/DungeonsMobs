package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import java.util.Optional;

import net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities;
import net.firefoxsalesman.dungeonsmobs.data.AncientDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AncientHelper {
	public static Ancient getAncientCapability(Entity entity) {
		return entity.getCapability(ModCapabilities.ANCIENT_CAPABILITY).orElse(new Ancient());
	}

	public static void makeAncient(LivingEntity entity) {
		Ancient cap = getAncientCapability(entity);
		cap.setAncient(true);
		cap.initiateBossBar(Component.literal(AncientDataHelper.getAncientName(entity)));
	}

	public static Optional<ServerBossEvent> getBossEvent(Entity entity) {
		Ancient cap = getAncientCapability(entity);
		if (cap.isAncient()) {
			System.out.println("It is ancient");
			return Optional.of(cap.getBossInfo());
		}
		return Optional.empty();
	}
}
