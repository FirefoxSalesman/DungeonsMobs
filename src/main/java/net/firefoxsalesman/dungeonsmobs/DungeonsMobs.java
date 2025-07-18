package net.firefoxsalesman.dungeonsmobs;

import com.mojang.logging.LogUtils;

import net.firefoxsalesman.dungeonsmobs.client.ModItemModelProperties;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonsmobs.lib.items.ItemTagWrappers;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.BowGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.CrossbowGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.MeleeGearConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.materials.armor.DungeonsArmorMaterials;
import net.firefoxsalesman.dungeonsmobs.lib.items.materials.weapon.WeaponMaterials;
import net.firefoxsalesman.dungeonsmobs.mod.ModEffects;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.mod.ModStructureModifiers;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.firefoxsalesman.dungeonsmobs.worldgen.EntitySpawnPlacement;
import net.firefoxsalesman.dungeonsmobs.worldgen.RaidEntries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DungeonsMobs.MOD_ID)
public class DungeonsMobs {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "dungeonsmobs";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();

	public DungeonsMobs() {
		ModLoadingContext.get().registerConfig(Type.COMMON, DungeonsMobsConfig.COMMON_SPEC,
				"dungeons-mobs-common.toml");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ItemTagWrappers.init();
		AttributeRegistry.register(modEventBus);
		DungeonsArmorMaterials.setupVanillaMaterials();
		WeaponMaterials.setupVanillaMaterials();

		ArmorGearConfigRegistry.subscribe();
		MeleeGearConfigRegistry.subscribe();
		BowGearConfigRegistry.subscribe();
		CrossbowGearConfigRegistry.subscribe();
		WeaponMaterials.subscribe();
		DungeonsArmorMaterials.subscribe();
		ArtifactGearConfigRegistry.subscribe();

		LibCapabilities.setupCapabilities();

		ModSoundEvents.register(modEventBus);
		ModEffects.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		ModEntities.register(modEventBus);
		ModItems.register(modEventBus);
		ModParticleTypes.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
		ModStructureModifiers.STRUCTURE_MODIFIER_SERIALIZERS.register(modEventBus);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT)
			ModItems.getEntries().forEach((RegistryObject<Item> item) -> event.accept(item));
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
			ModEntities.getEntries().forEach((RegistryObject<Item> item) -> event.accept(item));
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(NetworkHandler::init);
		event.enqueueWork(EntitySpawnPlacement::createPlacementTypes);
		event.enqueueWork(EntitySpawnPlacement::initSpawnPlacements);
		event.enqueueWork(RaidEntries::initWaveMemberEntries);
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// ITEM MODEL PROPERTIES
		event.enqueueWork(ModItemModelProperties::registerProperties);
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
		}
	}
}
