package net.firefoxsalesman.dungeonsmobs.capabilities;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.AttacherConvertible;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.Convertible;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final Capability<Convertible> CONVERTIBLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void setupCapabilities() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addGenericListener(Entity.class, AttacherConvertible::attach);
    }

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(Convertible.class);
    }
}
