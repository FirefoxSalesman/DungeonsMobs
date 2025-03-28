package net.firefoxsalesman.dungeonsmobs.worldgen;

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

		Raid.RaiderType.create("mountaineer", ModEntities.MOUNTAINEER.get(),
				new int[] { 0, 0, 2, 0, 1, 4, 2, 5 });

		Raid.RaiderType.create("squall_golem", ModEntities.SQUALL_GOLEM.get(),
				new int[] { 0, 0, 0, 1, 0, 1, 0, 2 });

		Raid.RaiderType.create("redstone_golem", ModEntities.REDSTONE_GOLEM.get(),
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1 });
	}
}
