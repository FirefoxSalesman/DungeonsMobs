package net.firefoxsalesman.dungeonsmobs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlock extends BaseFireBlock {
	private int tick = 50;

	public MagicFireBlock(Properties pProperties) {
		super(pProperties, 2);
	}

	@Override
	public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		pLevel.scheduleTick(pPos, this, 1);
		tick--;
		System.out.println(tick);
		if (tick <= 0)
			pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 2);
	}

	@Override
	protected boolean canBurn(BlockState pState) {
		return true;
	}

	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
		pLevel.scheduleTick(pPos, this, 1);
	}
}
