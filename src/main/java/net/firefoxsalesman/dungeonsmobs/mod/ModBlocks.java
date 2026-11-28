package net.firefoxsalesman.dungeonsmobs.mod;

import dev.obscuria.fragmentum.api.registry.Deferred;
import kotlin.jvm.functions.Function0;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.blocks.MagicFireBlock;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
	public static Deferred<Block> MAGIC_FIRE = registerBlocks("magic_fire",
			() -> new MagicFireBlock(Block.Properties.copy(Blocks.SOUL_FIRE)));

	public static void init() {
	}

	private static Deferred<Block> registerBlocks(String name, Function0<? extends Block> fn) {
		return DungeonsMobs.REGISTRAR.register(Registries.BLOCK, GeneralHelper.modLoc(name), fn);
	}
}
