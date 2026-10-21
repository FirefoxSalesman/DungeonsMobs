package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.blocks.entities.MagicFireBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DungeonsMobs.MOD_ID);

	public static final RegistryObject<BlockEntityType<MagicFireBlockEntity>> MAGIC_FIRE = BLOCK_ENTITIES
			.register("magic_fire", () -> BlockEntityType.Builder.of(MagicFireBlockEntity::new,
					ModBlocks.MAGIC_FIRE.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
}
