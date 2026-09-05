package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities;
import net.firefoxsalesman.dungeonsmobs.data.AncientDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AncientHelper {
	public static Ancient getAncientCapability(Entity entity) {
		return entity.getCapability(ModCapabilities.ANCIENT_CAPABILITY).orElse(new Ancient());
	}

	public static void makeUniqueAncient(LivingEntity entity) {
		Ancient cap = getAncientCapability(entity);
		cap.setAncient(true);
		cap.initiateBossBar(Component.literal(AncientDataHelper.getAncientName(entity, true)));
	}
}
