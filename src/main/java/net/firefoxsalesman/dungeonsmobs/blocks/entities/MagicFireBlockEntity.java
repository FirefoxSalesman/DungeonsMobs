package net.firefoxsalesman.dungeonsmobs.blocks.entities;

import net.firefoxsalesman.dungeonsmobs.mod.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlockEntity extends BlockEntity {
	private int lifespan = 50;

	public MagicFireBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.MAGIC_FIRE.get(), pPos, pBlockState);
	}

	public static void lifespanTick(Level level, BlockPos pos, BlockState state,
			MagicFireBlockEntity magicFireBlockEntity) {
		magicFireBlockEntity.lifespan--;
		if (magicFireBlockEntity.lifespan <= 0)
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
	}

	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("lifespan", lifespan);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		lifespan = pTag.getInt("lifespan");
	}
}
