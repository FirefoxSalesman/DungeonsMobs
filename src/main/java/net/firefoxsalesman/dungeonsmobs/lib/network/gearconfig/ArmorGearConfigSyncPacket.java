package net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig;

import com.mojang.serialization.Codec;

import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGearConfig;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.firefoxsalesman.dungeonsmobs.lib.items.GearConfigReloadListener.reloadAllItems;

public class ArmorGearConfigSyncPacket {
    private static final Codec<Map<ResourceLocation, ArmorGearConfig>> MAPPER =
            Codec.unboundedMap(ResourceLocation.CODEC, ArmorGearConfig.CODEC);

    public final Map<ResourceLocation, ArmorGearConfig> data;

    public ArmorGearConfigSyncPacket(Map<ResourceLocation, ArmorGearConfig> data) {
        this.data = data;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.data).result().orElse(new CompoundTag())));
    }

    public static ArmorGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new ArmorGearConfigSyncPacket(MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        ArmorGearConfigRegistry.ARMOR_GEAR_CONFIGS.setData(this.data);
        reloadAllItems();
    }
}
