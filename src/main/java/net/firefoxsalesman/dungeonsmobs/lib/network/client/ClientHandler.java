package net.firefoxsalesman.dungeonsmobs.lib.network.client;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact.ArtifactUsage;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact.ArtifactUsageHelper;
import net.firefoxsalesman.dungeonsmobs.lib.client.message.CuriosArtifactStopMessage;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientHandler {
    public static void handleCuriosArtifactStopMessage(CuriosArtifactStopMessage packet, Supplier<NetworkEvent.Context> ctx) {
        if (packet != null) {
            NetworkEvent.Context context = ctx.get();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                context.enqueueWork(() -> {
                    AbstractClientPlayer player = Minecraft.getInstance().player;
                    if (player != null) {
                        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
                        ItemStack artifactStack = cap.getUsingArtifact();
                        if (artifactStack != null && artifactStack.getItem() instanceof ArtifactItem artifactItem) {
                            artifactItem.stopUsingArtifact(player);
                            cap.stopUsingArtifact();
                        }
                    }
                });
            }
        }
    }

    // public static void handleEliteMobMessage(EliteMobMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
    //     NetworkEvent.Context context = contextSupplier.get();
    //     if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
    //         context.enqueueWork(() -> {
    //             Entity entity = Minecraft.getInstance().player.level.getEntity(message.getEntityId());
    //             if (entity instanceof LivingEntity) {
    //                 EliteMob cap = EliteMobHelper.getEliteMobCapability(entity);
    //                 cap.setElite(message.isElite());
    //                 cap.setTexture(message.getTexture());
    //                 if (cap.isElite()) {
    //                     entity.refreshDimensions();
    //                 }
    //             }
    //         });
    //     }
    // }
}
