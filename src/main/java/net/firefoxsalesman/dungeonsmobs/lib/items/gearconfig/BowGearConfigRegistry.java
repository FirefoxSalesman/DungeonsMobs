package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig.BowGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class BowGearConfigRegistry {

    public static final CodecJsonDataManager<BowGearConfig> BOW_GEAR_CONFIGS = new CodecJsonDataManager<>("gearconfig/bow", BowGearConfig.CODEC);


    public static BowGearConfig getConfig(ResourceLocation resourceLocation) {
        return BOW_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, BowGearConfig.DEFAULT);
    }

    public static boolean gearConfigExists(ResourceLocation resourceLocation) {
        return BOW_GEAR_CONFIGS.getData().containsKey(resourceLocation);
    }

    public static BowGearConfigSyncPacket toPacket(Map<ResourceLocation, BowGearConfig> map) {
        return new BowGearConfigSyncPacket(map);
    }
}
