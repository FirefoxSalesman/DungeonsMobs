package net.firefoxsalesman.dungeonsmobs.client;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.client.particle.SnowflakeParticle;
import net.firefoxsalesman.dungeonsmobs.client.renderer.creeper.IcyCreeperRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.renderer.undead.CustomZombieRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Dungeonsmobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.JUNGLE_ZOMBIE.get(), CustomZombieRenderer::new);

		event.registerEntityRenderer(ModEntities.ICY_CREEPER.get(), IcyCreeperRenderer::new);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onParticleFactory(RegisterParticleProvidersEvent event) {
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.SNOWFLAKE.get(),
				SnowflakeParticle.Factory::new);
	}
}
