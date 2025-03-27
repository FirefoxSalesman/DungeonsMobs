package net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig;

import com.mojang.serialization.Codec;

import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.firefoxsalesman.dungeonsmobs.lib.items.GearConfigReloadListener.reloadAllItems;

public class MeleeGearConfigSyncPacket {
    private static final Codec<Map<ResourceLocation, MeleeGearConfig>> MAPPER =
            Codec.unboundedMap(ResourceLocation.CODEC, MeleeGearConfig.CODEC);

    public final Map<ResourceLocation, MeleeGearConfig> data;

    public MeleeGearConfigSyncPacket(Map<ResourceLocation, MeleeGearConfig> data) {
        this.data = data;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.data).result().orElse(new CompoundTag())));
    }

    public static MeleeGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new MeleeGearConfigSyncPacket(MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        MeleeGearConfigRegistry.MELEE_GEAR_CONFIGS.setData(this.data);
        reloadAllItems();
    }
}
