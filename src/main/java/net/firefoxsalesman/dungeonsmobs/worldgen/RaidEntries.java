package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.world.entity.raid.Raid;

public class RaidEntries {

	public static void initWaveMemberEntries() {
		/*
		 * VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
		 * EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
		 * PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
		 * WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
		 * RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});
		 */

		// WARRIOR
		if (DungeonsMobsConfig.COMMON.ENABLE_MOUNTAINEERS_IN_RAIDS.get()) {
			Raid.RaiderType.create("mountaineer", ModEntities.MOUNTAINEER.get(),
					new int[] { 0, 0, 2, 0, 1, 4, 2, 5 });
		}

		if (DungeonsMobsConfig.COMMON.ENABLE_ROYAL_GUARDS_IN_RAIDS.get()) {
			Raid.RaiderType.create("royal_guard", ModEntities.ROYAL_GUARD.get(),
					new int[] { 0, 0, 1, 0, 0, 2, 1, 2 });
		}

		// SPELLCASTER
		if (DungeonsMobsConfig.COMMON.ENABLE_GEOMANCERS_IN_RAIDS.get()) {
			Raid.RaiderType.create("geomancer", ModEntities.GEOMANCER.get(),
					new int[] { 0, 0, 0, 0, 0, 1, 1, 2 });
		}

		if (DungeonsMobsConfig.COMMON.ENABLE_MAGES_IN_RAIDS.get()) {
			Raid.RaiderType.create("mage", ModEntities.MAGE.get(), new int[] { 0, 0, 1, 0, 0, 1, 0, 2 });
		}

		if (DungeonsMobsConfig.COMMON.ENABLE_WINDCALLERS_IN_RAIDS.get()) {
			Raid.RaiderType.create("windcaller", ModEntities.WINDCALLER.get(),
					new int[] { 0, 0, 0, 0, 0, 1, 1, 2 });
		}

		// BEAST / GOLEM
		if (DungeonsMobsConfig.COMMON.ENABLE_SQUALL_GOLEMS_IN_RAIDS.get()) {
			Raid.RaiderType.create("squall_golem", ModEntities.SQUALL_GOLEM.get(),
					new int[] { 0, 0, 0, 1, 0, 1, 0, 2 });
		}

		if (DungeonsMobsConfig.COMMON.ENABLE_REDSTONE_GOLEMS_IN_RAIDS.get()) {
			Raid.RaiderType.create("redstone_golem", ModEntities.REDSTONE_GOLEM.get(),
					new int[] { 0, 0, 0, 0, 0, 0, 0, 1 });
		}
	}
}
