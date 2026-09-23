package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities;
import net.firefoxsalesman.dungeonsmobs.data.AncientDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class AncientHelper {
	public static Ancient getAncientCapability(Entity entity) {
		return entity.getCapability(ModCapabilities.ANCIENT_CAPABILITY).orElse(new Ancient());
	}

	private static void makeAncient(LivingEntity entity, boolean unique) {
		Ancient cap = getAncientCapability(entity);
		cap.setAncient(true);
		if (entity instanceof Mob mob) {
			cap.initiateBossBar(mob, Component.literal(AncientDataHelper.getAncientName(mob, unique)));
		}
	}

	public static void makeUniqueAncient(LivingEntity entity) {
		makeAncient(entity, true);
	}

	public static void makeNonUniqueAncient(LivingEntity entity) {
		makeAncient(entity, false);
	}
}
