package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import net.firefoxsalesman.dungeonsmobs.gear.utilities.GeneralHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.LootTableHelper;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonslibs.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;;

public class SatchelOfElixirsItem extends ArtifactItem {

	public SatchelOfElixirsItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemstack = c.getItemStack();

		if (playerIn == null)
			return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);

		if (!c.getLevel().isClientSide) {
			for (int i = 0; i < 2; i++) {
				ItemStack elixirToDrop = LootTableHelper.generateItemStack(
						(ServerLevel) playerIn.level(), playerIn.blockPosition(),
						GeneralHelper.modLoc("items/satchel_of_elixirs"), playerIn.getRandom());
				ItemEntity elixirAsEntity = new ItemEntity(playerIn.level(), playerIn.getX(),
						playerIn.getY(), playerIn.getZ(), elixirToDrop);
				playerIn.level().addFreshEntity(elixirAsEntity);
			}
		}

		itemstack.hurtAndBreak(1, playerIn,
				(entity) -> NetworkHandler.INSTANCE.send(
						PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
						new BreakItemMessage(entity.getId(), itemstack)));

		ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}

	@Override
	public int getCooldownInSeconds() {
		return 30;
	}

	@Override
	public int getDurationInSeconds() {
		return 0;
	}
}
