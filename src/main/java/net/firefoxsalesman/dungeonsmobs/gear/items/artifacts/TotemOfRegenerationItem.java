package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.firefoxsalesman.dungeonsmobs.gear.entities.TotemOfRegenerationEntity;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EntityTypeInit;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

public class TotemOfRegenerationItem extends ArtifactItem {
	public TotemOfRegenerationItem(Properties properties) {
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
				TotemOfRegenerationEntity totemOfRegenerationEntity = EntityTypeInit.TOTEM_OF_REGENERATION
						.get().create(itemUseContextPlayer.level());
				if (totemOfRegenerationEntity != null) {
					totemOfRegenerationEntity.moveTo(blockPos, 0, 0);
					totemOfRegenerationEntity.setOwner(itemUseContextPlayer);
					itemUseContextPlayer.level().addFreshEntity(totemOfRegenerationEntity);
					itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer,
							(entity) -> NetworkHandler.INSTANCE.send(
									PacketDistributor.TRACKING_ENTITY_AND_SELF
											.with(() -> entity),
									new BreakItemMessage(entity.getId(),
											itemUseContextItem)));
					ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer,
							itemUseContextItem.getItem());
				}
			}
		}
		return InteractionResultHolder.consume(itemUseContext.getItemStack());
	}

	@Override
	public int getCooldownInSeconds() {
		return 25;
	}

	@Override
	public int getDurationInSeconds() {
		return 5;
	}
}
