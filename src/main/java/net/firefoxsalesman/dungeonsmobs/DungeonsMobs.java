package net.firefoxsalesman.dungeonsmobs;

import com.mojang.logging.LogUtils;

import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.mod.ModEffects;
import net.firefoxsalesman.dungeonsmobs.worldgen.EntitySpawnPlacement;
import net.firefoxsalesman.dungeonsmobs.worldgen.RaidEntries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
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
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModSoundEvents.register(modEventBus);
		ModEffects.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		ModEntities.register(modEventBus);
		ModParticleTypes.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			for (RegistryObject<Item> i : ModEntities.SPAWN_EGGS.getEntries()) {
				event.accept(i);
			}
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(EntitySpawnPlacement::createPlacementTypes);
		event.enqueueWork(EntitySpawnPlacement::initSpawnPlacements);
		event.enqueueWork(RaidEntries::initWaveMemberEntries);
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
		}
	}

	private void onLoadComplete(final FMLLoadCompleteEvent event) {
		EntityType.HUSK.getDimensions().scale(0.6F * 1.2F, 1.95F * 1.2F);
	}
}
