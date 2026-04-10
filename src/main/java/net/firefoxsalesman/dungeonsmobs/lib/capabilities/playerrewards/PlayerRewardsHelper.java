package net.firefoxsalesman.dungeonsmobs.lib.capabilities.playerrewards;

import net.minecraft.world.entity.player.Player;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities.PLAYER_REWARDS_CAPABILITY;

public class PlayerRewardsHelper {

	public static PlayerRewards getPlayerRewardsCapability(Player playerEntity) {
		return playerEntity.getCapability(PLAYER_REWARDS_CAPABILITY).orElse(new PlayerRewards());
	}

}
