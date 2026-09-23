package net.firefoxsalesman.dungeonsmobs.network;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.firefoxsalesman.dungeonsmobs.network.message.BossBarMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.network.PacketDistributor;

public class DungeonsBossInfo extends ServerBossEvent {
	private final Mob boss;
	private final Set<ServerPlayer> players = new HashSet<>();

	public DungeonsBossInfo(Component displayName, Mob boss, BossBarOverlay pOverlay) {
		super(displayName, BossBarColor.RED, pOverlay);
		System.out.println(displayName);
		this.boss = boss;
	}

	public DungeonsBossInfo(Mob boss, BossBarOverlay pOverlay) {
		this(boss.getDisplayName(), boss, pOverlay);
	}

	public void update(int tickCount) {
		this.setProgress(boss.getHealth() / boss.getMaxHealth());
		if (tickCount % 5 != 0)
			return;
		Iterator<ServerPlayer> it = players.iterator();

		while (it.hasNext()) {
			ServerPlayer player = it.next();
			if (boss.getSensing().hasLineOfSight(player)) {
				super.addPlayer(player);
				it.remove();
			}
		}
	}

	public int getBossId() {
		return boss.getId();
	}

	public void addPlayer(ServerPlayer player) {
		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
				new BossBarMessage(getId(), boss, false));
		if (boss.getSensing().hasLineOfSight(player)) {
			super.addPlayer(player);
		} else {
			players.add(player);
		}

	}

	public void removePlayer(ServerPlayer player) {
		super.removePlayer(player);
		players.remove(player);
		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
				new BossBarMessage(getId(), boss, true));
	}
}
