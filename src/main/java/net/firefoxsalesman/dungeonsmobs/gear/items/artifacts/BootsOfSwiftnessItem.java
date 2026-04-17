package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class BootsOfSwiftnessItem extends ArtifactItem {
	public BootsOfSwiftnessItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemstack = c.getItemStack();

		MobEffectInstance swiftness = new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
				this.getDurationInSeconds() * 20, 2);
		playerIn.addEffect(swiftness);
		itemstack.hurtAndBreak(1, playerIn,
				(entity) -> NetworkHandler.INSTANCE.send(
						PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
						new BreakItemMessage(entity.getId(), itemstack)));
		ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}

	@Override
	public int getCooldownInSeconds() {
		return 5;
	}

	@Override
	public int getDurationInSeconds() {
		return 2;
	}
}
