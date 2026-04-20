package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.getCanHealPredicate;

import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;

public class IronHideAmuletItem extends ArtifactItem {
	public IronHideAmuletItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemstack = c.getItemStack();

		MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 1);
		playerIn.addEffect(resistance);
		applyToNearbyEntities(playerIn, 8,
				getCanHealPredicate(playerIn), (LivingEntity nearbyEntity) -> {
					MobEffectInstance effectInstance = new MobEffectInstance(
							MobEffects.DAMAGE_RESISTANCE, 220, 1);
					nearbyEntity.addEffect(effectInstance);
				});

		itemstack.hurtAndBreak(1, playerIn,
				(entity) -> NetworkHandler.INSTANCE.send(
						PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
						new BreakItemMessage(entity.getId(), itemstack)));
		ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}

	@Override
	public int getCooldownInSeconds() {
		return 25;
	}

	@Override
	public int getDurationInSeconds() {
		return 11;
	}
}
