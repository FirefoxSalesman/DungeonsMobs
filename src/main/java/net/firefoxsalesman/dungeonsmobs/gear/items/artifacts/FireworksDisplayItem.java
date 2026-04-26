package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.firefoxsalesman.dungeonsmobs.gear.entities.FireworksDisplayEntity;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EntityTypeInit;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FireworksDisplayItem extends ArtifactItem {
	public FireworksDisplayItem(Properties properties) {
		super(properties);
		procOnItemUse = true;
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		Level world = itemUseContext.getLevel();
		if (world.isClientSide || itemUseContext.isHitMiss()) {
			return InteractionResultHolder.success(itemUseContext.getItemStack());
		} else {
			ItemStack itemUseContextItem = itemUseContext.getItemStack();
			Player itemUseContextPlayer = itemUseContext.getPlayer();
			BlockPos itemUseContextPos = itemUseContext.getClickedPos();
			Direction itemUseContextFace = itemUseContext.getClickedFace();
			BlockState blockState = world.getBlockState(itemUseContextPos);

			BlockPos blockPos;
			if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
				blockPos = itemUseContextPos;
			} else {
				blockPos = itemUseContextPos.relative(itemUseContextFace);
			}
			if (itemUseContextPlayer != null) {
				FireworksDisplayEntity totemEntity = EntityTypeInit.FIREWORKS_DISPLAY.get()
						.create(itemUseContextPlayer.level());
				if (totemEntity != null) {
					totemEntity.moveTo(blockPos, 0, 0);
					totemEntity.setOwner(itemUseContextPlayer);
					itemUseContextPlayer.level().addFreshEntity(totemEntity);
					ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer,
							itemUseContextItem.getItem());
				}
			}
		}
		return InteractionResultHolder.consume(itemUseContext.getItemStack());
	}

	@Override
	public int getCooldownInSeconds() {
		return 60;
	}

	@Override
	public int getDurationInSeconds() {
		return 5;
	}
}
