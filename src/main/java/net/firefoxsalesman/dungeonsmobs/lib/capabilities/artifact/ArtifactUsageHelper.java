package net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities.ARTIFACT_USAGE_CAPABILITY;


public class ArtifactUsageHelper {

    public static ArtifactUsage getArtifactUsageCapability(Entity entity) {
        return entity.getCapability(ARTIFACT_USAGE_CAPABILITY).orElse(new ArtifactUsage());
    }

    public static boolean startUsingArtifact(Player playerIn, ArtifactUsage cap, ItemStack itemstack) {
        boolean result = cap.startUsingArtifact(itemstack);
        return result;
    }
}
