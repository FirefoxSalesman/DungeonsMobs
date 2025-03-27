package net.firefoxsalesman.dungeonsmobs.lib.items;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGearConfigRegistry.ARMOR_GEAR_CONFIGS;
import static net.firefoxsalesman.dungeonsmobs.lib.items.materials.armor.DungeonsArmorMaterials.ARMOR_MATERIALS;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IReloadableGear;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GearConfigReloadListener implements ResourceManagerReloadListener {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ARMOR_MATERIALS);
        event.addListener(ARMOR_GEAR_CONFIGS);
        event.addListener(new GearConfigReloadListener());
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        reloadAllItems();
    }

    public static void reloadAllItems() {
        ITEMS.getEntries().stream().filter(registryKeyItemEntry -> registryKeyItemEntry.getValue() instanceof IReloadableGear).map(registryKeyItemEntry -> (IReloadableGear) registryKeyItemEntry.getValue()).forEach(IReloadableGear::reload);
    }
}
