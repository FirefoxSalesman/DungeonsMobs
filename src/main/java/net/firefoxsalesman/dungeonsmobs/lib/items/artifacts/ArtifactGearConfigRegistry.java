package net.firefoxsalesman.dungeonsmobs.lib.items.artifacts;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.config.ArtifactGearConfig;
import net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig.ArtifactGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class ArtifactGearConfigRegistry {
    public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = new ResourceLocation(MOD_ID, "gear_config");

    public static final CodecJsonDataManager<ArtifactGearConfig> ARTIFACT_GEAR_CONFIGS = new CodecJsonDataManager<>("gearconfig/artifact", ArtifactGearConfig.CODEC);


    public static ArtifactGearConfig getConfig(ResourceLocation resourceLocation) {
        return ARTIFACT_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, ArtifactGearConfig.DEFAULT);
    }

    public static boolean gearConfigExists(ResourceLocation resourceLocation) {
        return ARTIFACT_GEAR_CONFIGS.getData().containsKey(resourceLocation);
    }

    public static ArtifactGearConfigSyncPacket toPacket(Map<ResourceLocation, ArtifactGearConfig> map) {
        return new ArtifactGearConfigSyncPacket(map);
    }
}
