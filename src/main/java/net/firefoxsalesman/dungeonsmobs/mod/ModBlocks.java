package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.blocks.MagicFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			DungeonsMobs.MOD_ID);

	public static RegistryObject<Block> MAGIC_FIRE = BLOCKS.register("magic_fire",
			() -> new MagicFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
					.replaceable().noCollission().instabreak().lightLevel(x -> 10)
					.sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
