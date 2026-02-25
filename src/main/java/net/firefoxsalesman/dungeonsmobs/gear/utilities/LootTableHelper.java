package net.firefoxsalesman.dungeonsmobs.gear.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LootTableHelper {
	public static ItemStack generateItemStack(ServerLevel world, BlockPos pos, ResourceLocation lootTable,
			RandomSource random) {
		LootParams context = new LootParams.Builder(world)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)) // positional context
				.create(LootContextParamSets.CHEST); // chest set requires positional context, has no
									// other mandatory parameters

		List<ItemStack> stacks = getLootTable(world, lootTable).getRandomItems(context);
		return !stacks.isEmpty()
				? stacks.get(0)
				: ItemStack.EMPTY;
	}

	public static boolean lootTableExists(ServerLevel world, ResourceLocation lootTable) {
		return !getLootTable(world, lootTable).equals(LootTable.EMPTY);
	}

	private static LootTable getLootTable(ServerLevel world, ResourceLocation lootTable) {
		return world.getServer().getLootData().getElement(new LootDataId<>(LootDataType.TABLE, lootTable));
	}
}
