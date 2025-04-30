package net.firefoxsalesman.dungeonsmobs.lib.client.gui.elementconfig;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.CodecJsonDataManager;

public class GuiElementConfigRegistry {
    public static final ResourceLocation ELEMENT_CONFIG_BUILTIN_RESOURCELOCATION = new ResourceLocation(MOD_ID, "gui/element");

    public static final CodecJsonDataManager<GuiElementConfig> GUI_ELEMENT_CONFIGS = new CodecJsonDataManager<>("gui/element", GuiElementConfig.CODEC);


    public static GuiElementConfig getConfig(ResourceLocation resourceLocation) {
        return GUI_ELEMENT_CONFIGS.getData().getOrDefault(resourceLocation, GuiElementConfig.DEFAULT);
    }

    public static boolean guiElementConfigExists(ResourceLocation resourceLocation) {
        return GUI_ELEMENT_CONFIGS.getData().containsKey(resourceLocation);
    }

    public static void initGuiElementConfigs() {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) {
            ResourceManager resourceManager = mc.getResourceManager();
            if (resourceManager instanceof ReloadableResourceManager reloadableResourceManager) {
                reloadableResourceManager.registerReloadListener(GUI_ELEMENT_CONFIGS);
            }
        }
    }
}
