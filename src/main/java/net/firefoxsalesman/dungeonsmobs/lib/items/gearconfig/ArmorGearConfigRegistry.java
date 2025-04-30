package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.network.gearconfig.ArmorGearConfigSyncPacket;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class ArmorGearConfigRegistry {
	public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = new ResourceLocation(MOD_ID,
			"gear_config");

	public static final CodecJsonDataManager<ArmorGearConfig> ARMOR_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/armor", ArmorGearConfig.CODEC);

	public static ArmorGearConfig getConfig(ResourceLocation resourceLocation) {
		return ARMOR_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, ArmorGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return ARMOR_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static ArmorGearConfigSyncPacket toPacket(Map<ResourceLocation, ArmorGearConfig> map) {
		return new ArmorGearConfigSyncPacket(map);
	}

	public static void subscribe() {
		ARMOR_GEAR_CONFIGS.subscribeAsSyncable(NetworkHandler.INSTANCE, ArmorGearConfigRegistry::toPacket);

	}
}
