package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public abstract class CropsBlockMixin {

    @Inject(at = @At("HEAD"), method = "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V")
    private void onEntityCollision(BlockState p_52277_, Level worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci) {
        if (entityIn instanceof RedstoneGolemEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
            worldIn.destroyBlock(pos, true, entityIn);
        } else if (entityIn instanceof SquallGolemEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
            worldIn.destroyBlock(pos, true, entityIn);
        }
    }
}
