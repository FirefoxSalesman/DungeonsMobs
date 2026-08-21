package net.firefoxsalesman.dungeonsmobs.network;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.network.message.AncientMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
			new ResourceLocation(DungeonsMobs.MOD_ID, "network"))
			.clientAcceptedVersions("1"::equals)
			.serverAcceptedVersions("1"::equals)
			.networkProtocolVersion(() -> "1")
			.simpleChannel();

	protected static int PACKET_COUNTER = 0;

	public NetworkHandler() {
	}

	public static void init() {
		INSTANCE.messageBuilder(AncientMessage.class, incrementAndGetPacketCounter())
				.encoder(AncientMessage::encode).decoder(AncientMessage::decode)
				.consumerMainThread(AncientMessage::onPacketReceived)
				.add();
		// INSTANCE.messageBuilder(AnimatedPropsMessage.class,
		// incrementAndGetPacketCounter())
		// .encoder(AnimatedPropsMessage::encode).decoder(AnimatedPropsMessage::decode)
		// .consumerMainThread(AnimatedPropsMessage::onPacketReceived)
		// .add();
	}

	public static int incrementAndGetPacketCounter() {
		return PACKET_COUNTER++;
	}
}
