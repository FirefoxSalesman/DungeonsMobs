package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig.MeleeGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class MeleeGearConfigRegistry {
    public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = new ResourceLocation(MOD_ID, "gear_config");

    public static final CodecJsonDataManager<MeleeGearConfig> MELEE_GEAR_CONFIGS = new CodecJsonDataManager<>("gearconfig/melee", MeleeGearConfig.CODEC);


    public static MeleeGearConfig getConfig(ResourceLocation resourceLocation) {
        return MELEE_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, MeleeGearConfig.DEFAULT);
    }

    public static boolean gearConfigExists(ResourceLocation resourceLocation) {
        return MELEE_GEAR_CONFIGS.getData().containsKey(resourceLocation);
    }

    public static MeleeGearConfigSyncPacket toPacket(Map<ResourceLocation, MeleeGearConfig> map) {
        return new MeleeGearConfigSyncPacket(map);
    }
}
