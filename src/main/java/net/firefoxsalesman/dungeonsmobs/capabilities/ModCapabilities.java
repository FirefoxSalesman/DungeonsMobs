package net.firefoxsalesman.dungeonsmobs.capabilities;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.capabilities.ancient.Ancient;
import net.firefoxsalesman.dungeonsmobs.capabilities.ancient.AttacherAncient;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.AttacherConvertible;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.Convertible;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.AttacherMobProps;
import net.firefoxsalesman.dungeonsmobs.capabilities.properties.MobProps;
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
	public static final Capability<Ancient> ANCIENT_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});
	public static final Capability<Convertible> CONVERTIBLE_CAPABILITY = CapabilityManager
			.get(new CapabilityToken<>() {
			});
	public static final Capability<MobProps> MOB_PROPS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void setupCapabilities() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addGenericListener(Entity.class, AttacherAncient::attach);
		forgeBus.addGenericListener(Entity.class, AttacherConvertible::attach);
		forgeBus.addGenericListener(Entity.class, AttacherMobProps::attach);
	}

	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(Ancient.class);
		event.register(Convertible.class);
		event.register(MobProps.class);
	}
}
