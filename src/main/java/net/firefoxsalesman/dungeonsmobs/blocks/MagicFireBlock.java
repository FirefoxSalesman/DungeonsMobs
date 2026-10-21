package net.firefoxsalesman.dungeonsmobs.blocks;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.blocks.entities.MagicFireBlockEntity;
import net.firefoxsalesman.dungeonsmobs.mod.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlock extends BaseFireBlock implements EntityBlock {
	public MagicFireBlock(Properties pProperties) {
		super(pProperties, 2);
	}

	@Override
	protected boolean canBurn(BlockState pState) {
		return true;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new MagicFireBlockEntity(pPos, pState);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
			BlockEntityType<T> pBlockEntityType) {
		return createTickerHelper(pBlockEntityType, ModBlockEntities.MAGIC_FIRE.get(),
				MagicFireBlockEntity::lifespanTick);
	}

	@Nullable
	private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
			BlockEntityType<A> pServerType, BlockEntityType<E> pClientType,
			BlockEntityTicker<? super E> pTicker) {
		return pClientType == pServerType ? (BlockEntityTicker<A>) pTicker : null;
	}
}
