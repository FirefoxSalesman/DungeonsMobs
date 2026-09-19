package net.firefoxsalesman.dungeonsmobs.network.message;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.world.entity.player.Player;
import net.firefoxsalesman.dungeonsmobs.client.renderer.BossBarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

/**
 * This was largely borrowed from Goety, because I am a talentless hack.
 * Many thanks to Polarice.
 */
public class BossBarMessage {
	private final UUID bar;
	private final int boss;
	private final boolean remove;

	public BossBarMessage(UUID bar, int boss, boolean remove) {
		this.bar = bar;
		this.boss = boss;
		this.remove = remove;
	}

	public BossBarMessage(UUID bar, Mob boss, boolean remove) {
		this(bar, boss.getId(), remove);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUUID(bar);
		buffer.writeInt(boss);
		buffer.writeBoolean(remove);
	}

	public static BossBarMessage decode(FriendlyByteBuf buffer) {
		return new BossBarMessage(buffer.readUUID(), buffer.readInt(), buffer.readBoolean());
	}

	public static boolean onPacketReceived(BossBarMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
			context.enqueueWork(() -> {
				Player player = Minecraft.getInstance().player;
				if (player != null) {
					Entity boss = player.level().getEntity(message.boss);
					if (boss instanceof Mob mob) {
						if (message.remove) {
							BossBarRenderer.removeBossBar(message.bar, mob);
						} else {
							BossBarRenderer.addBossBar(message.bar, mob);

						}
					}
				}
			});
		}
		return true;
	}
}
