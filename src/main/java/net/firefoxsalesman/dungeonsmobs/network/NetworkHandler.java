package net.firefoxsalesman.dungeonsmobs.network;

import net.firefoxsalesman.dungeonsmobs.gear.network.entity.PlayerBeamMessage;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
			GeneralHelper.modLoc("network"))
			.clientAcceptedVersions("1"::equals)
			.serverAcceptedVersions("1"::equals)
			.networkProtocolVersion(() -> "1")
			.simpleChannel();

	protected static int PACKET_COUNTER = 0;

	public NetworkHandler() {
	}

	public static void init() {
		INSTANCE.registerMessage(incrementAndGetPacketCounter(), PlayerBeamMessage.class,
				PlayerBeamMessage::encode, PlayerBeamMessage::decode,
				PlayerBeamMessage.PlayerBeamMessageHandler::handle);
	}

	public static int incrementAndGetPacketCounter() {
		return PACKET_COUNTER++;
	}
}
