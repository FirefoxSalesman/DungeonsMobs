package net.firefoxsalesman.dungeonsmobs.lib.summon;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ResourceLocationHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SummonConfigRegistry {
	public static final ResourceLocation SUMMON_RESOURCELOCATION = ResourceLocationHelper.modLoc("summon");

	public static final CodecJsonDataManager<SummonConfig> SUMMON_CONFIGS = new CodecJsonDataManager<>("summon",
			SummonConfig.CODEC);

	public static SummonConfig getConfig(ResourceLocation resourceLocation) {
		return SUMMON_CONFIGS.getData().getOrDefault(resourceLocation, SummonConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return SUMMON_CONFIGS.getData().containsKey(resourceLocation);
	}

	@SubscribeEvent
	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(SUMMON_CONFIGS);
	}
}
