package net.firefoxsalesman.dungeonsmobs.lib.summon;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SummonConfigRegistry {
    public static final ResourceLocation SUMMON_RESOURCELOCATION = new ResourceLocation(MOD_ID, "summon");

    public static final CodecJsonDataManager<SummonConfig> SUMMON_CONFIGS = new CodecJsonDataManager<>("summon", SummonConfig.CODEC);


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
