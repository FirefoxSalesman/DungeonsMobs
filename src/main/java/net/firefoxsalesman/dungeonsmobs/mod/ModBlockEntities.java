package net.firefoxsalesman.dungeonsmobs.mod;

import dev.obscuria.fragmentum.api.registry.Deferred;
import kotlin.jvm.functions.Function0;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.blocks.entities.MagicFireBlockEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
	public static final Deferred<BlockEntityType<MagicFireBlockEntity>> MAGIC_FIRE = registerBlockEntities(
			"magic_fire", () -> BlockEntityType.Builder.of(MagicFireBlockEntity::new,
					ModBlocks.MAGIC_FIRE.get()).build(null));

	private static <T extends BlockEntity> Deferred<BlockEntityType<T>> registerBlockEntities(String name,
			Function0<? extends BlockEntityType<T>> fn) {
		return DungeonsMobs.REGISTRAR.register(Registries.BLOCK_ENTITY_TYPE, GeneralHelper.modLoc(name), fn)
				.as();
	}

	public static void init() {
	}
}
