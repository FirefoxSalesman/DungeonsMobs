package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.LOVE_MEDALLION_BLACKLIST;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.applyToNearbyEntities;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Follower;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Leader;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class LoveMedallionItem extends ArtifactItem {
	public LoveMedallionItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemstack = c.getItemStack();

		applyToNearbyEntities(playerIn, 5, 2,
				(nearbyEntity) -> nearbyEntity instanceof Enemy
						&& !FollowerLeaderHelper.isFollower(nearbyEntity)
						&& nearbyEntity.isAlive()
						&& nearbyEntity.canChangeDimensions()
						&& !LOVE_MEDALLION_BLACKLIST.get().contains(ForgeRegistries.ENTITY_TYPES
								.getKey(nearbyEntity.getType()).toString()),
				(LivingEntity nearbyEntity) -> makeMinionOf(playerIn, nearbyEntity));

		itemstack.hurtAndBreak(1, playerIn,
				(entity) -> NetworkHandler.INSTANCE.send(
						PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
						new BreakItemMessage(entity.getId(), itemstack)));

		ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}

	private void makeMinionOf(Player playerIn, LivingEntity nearbyEntity) {
		if (nearbyEntity instanceof Monster) {
			Monster mobEntity = (Monster) nearbyEntity;
			PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
			Leader leaderCapability = FollowerLeaderHelper.getLeaderCapability(playerIn);
			Follower followerCapability = FollowerLeaderHelper.getFollowerCapability(nearbyEntity);
			leaderCapability.addMinion(mobEntity);
			followerCapability.setLeader(playerIn);
			followerCapability.setTemporary(true);
			followerCapability.setRevertsOnExpiration(true);
			followerCapability.setFollowerDuration(200);
			followerCapability.setGoalsAdded(false);
			((Monster) nearbyEntity).setTarget(null);
			FollowerLeaderHelper.addFollowerGoals(mobEntity);
		}
	}

	@Override
	public int getCooldownInSeconds() {
		return 40;
	}

	@Override
	public int getDurationInSeconds() {
		return 0;
	}
}
