package net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig;

import com.mojang.serialization.Codec;

import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.config.ArtifactGearConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.firefoxsalesman.dungeonsmobs.lib.items.GearConfigReloadListener.reloadAllItems;

public class ArtifactGearConfigSyncPacket {
    private static final Codec<Map<ResourceLocation, ArtifactGearConfig>> MAPPER =
            Codec.unboundedMap(ResourceLocation.CODEC, ArtifactGearConfig.CODEC);

    public final Map<ResourceLocation, ArtifactGearConfig> data;

    public ArtifactGearConfigSyncPacket(Map<ResourceLocation, ArtifactGearConfig> data) {
        this.data = data;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.data).result().orElse(new CompoundTag())));
    }

    public static ArtifactGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new ArtifactGearConfigSyncPacket(MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS.setData(this.data);
        reloadAllItems();
    }
}
