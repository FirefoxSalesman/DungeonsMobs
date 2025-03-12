package net.firefoxsalesman.dungeonsmobs.client;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.client.particle.SnowflakeParticle;
import net.firefoxsalesman.dungeonsmobs.client.renderer.creeper.IcyCreeperRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.BlastlingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.SnarelingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.WatchlingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.golem.SquallGolemRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.OrbProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.SnarelingGlobRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.CustomSkeletonRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.CustomZombieRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.water.SunkenSkeletonRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.JUNGLE_ZOMBIE.get(), CustomZombieRenderer::new);
		event.registerEntityRenderer(ModEntities.FROZEN_ZOMBIE.get(), CustomZombieRenderer::new);

		event.registerEntityRenderer(ModEntities.MOSSY_SKELETON.get(), CustomSkeletonRenderer::new);

		event.registerEntityRenderer(ModEntities.ICY_CREEPER.get(), IcyCreeperRenderer::new);

		event.registerEntityRenderer(ModEntities.SUNKEN_SKELETON.get(), SunkenSkeletonRenderer::new);

		event.registerEntityRenderer(ModEntities.SQUALL_GOLEM.get(), SquallGolemRenderer::new);

		event.registerEntityRenderer(ModEntities.BLASTLING.get(), BlastlingRenderer::new);
		event.registerEntityRenderer(ModEntities.WATCHLING.get(), WatchlingRenderer::new);
		event.registerEntityRenderer(ModEntities.SNARELING.get(), SnarelingRenderer::new);

		event.registerEntityRenderer(ModEntities.BLASTLING_BULLET.get(),
				(manager) -> new OrbProjectileRenderer(manager, 0xFFFF93F7, false));
		event.registerEntityRenderer(ModEntities.SNARELING_GLOB.get(), SnarelingGlobRenderer::new);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onParticleFactory(RegisterParticleProvidersEvent event) {
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.SNOWFLAKE.get(),
				SnowflakeParticle.Factory::new);
	}
}
