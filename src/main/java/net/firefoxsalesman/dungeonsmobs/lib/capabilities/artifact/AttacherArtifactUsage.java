package net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class AttacherArtifactUsage {

    private static class ArtifactUsageProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(MOD_ID, "artifact_usage");
        private final ArtifactUsage backend = new ArtifactUsage();
        private final LazyOptional<ArtifactUsage> optionalData = LazyOptional.of(() -> backend);

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
            return LibCapabilities.ARTIFACT_USAGE_CAPABILITY.orEmpty(cap, this.optionalData);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    // attach only to living entities
    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player) {
            final ArtifactUsageProvider provider = new ArtifactUsageProvider();
            event.addCapability(ArtifactUsageProvider.IDENTIFIER, provider);
        }
    }
}
