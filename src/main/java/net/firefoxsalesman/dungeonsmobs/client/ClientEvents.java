package net.firefoxsalesman.dungeonsmobs.client;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MageModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MountaineerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.RoyalGuardModel;
import net.firefoxsalesman.dungeonsmobs.client.particle.CorruptedDustParticle;
import net.firefoxsalesman.dungeonsmobs.client.particle.CorruptedMagicParticle;
import net.firefoxsalesman.dungeonsmobs.client.particle.DustParticle;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.client.particle.SnowflakeParticle;
import net.firefoxsalesman.dungeonsmobs.client.renderer.EmptyRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.creeper.IcyCreeperRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.BlastlingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.EndersentRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.SnarelingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.ender.WatchlingRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.golem.SquallGolemRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.illager.DefaultIllagerRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.LeapleafRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.PoisonQuillVineRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.QuickGrowingVineRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.jungle.WhispererRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.CobwebProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.MageMissileRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.OrbProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.PoisonQuillRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.SnarelingGlobRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.redstone.RedstoneGolemRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.redstone.RedstoneMineRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.summonables.KelpTrapRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.summonables.SimpleTrapRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.summonables.SummonSpotRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.summonables.WraithFireRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.CustomSkeletonRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.CustomZombieRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.SkeletonVanguardRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.undead.WraithRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.water.PoisonAnemoneRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.water.QuickGrowingKelpRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.water.SunkenSkeletonRenderer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.water.WavewhispererRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageCloneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
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
		// To match Husk proportions found in MCD
		event.registerEntityRenderer(EntityType.HUSK, CustomZombieRenderer::new);

		event.registerEntityRenderer(ModEntities.MOSSY_SKELETON.get(), CustomSkeletonRenderer::new);
		event.registerEntityRenderer(ModEntities.SKELETON_VANGUARD.get(), SkeletonVanguardRenderer::new);

		event.registerEntityRenderer(ModEntities.ROYAL_GUARD.get(),
				manager -> new DefaultIllagerRenderer<>(manager, new RoyalGuardModel(),
						0.9375F * 1.2F));
		event.registerEntityRenderer(ModEntities.MOUNTAINEER.get(),
				manager -> new DefaultIllagerRenderer<>(manager, new MountaineerModel()));

		event.registerEntityRenderer(ModEntities.MAGE.get(),
				manager -> new DefaultIllagerRenderer<MageEntity>(manager,
						new MageModel<MageEntity>()));
		event.registerEntityRenderer(ModEntities.MAGE_CLONE.get(),
				manager -> new DefaultIllagerRenderer<MageCloneEntity>(manager,
						new MageModel<MageCloneEntity>()));

		event.registerEntityRenderer(ModEntities.REDSTONE_GOLEM.get(), RedstoneGolemRenderer::new);

		event.registerEntityRenderer(ModEntities.WHISPERER.get(), WhispererRenderer::new);
		event.registerEntityRenderer(ModEntities.LEAPLEAF.get(), LeapleafRenderer::new);
		event.registerEntityRenderer(ModEntities.POISON_QUILL_VINE.get(), PoisonQuillVineRenderer::new);
		event.registerEntityRenderer(ModEntities.QUICK_GROWING_VINE.get(), QuickGrowingVineRenderer::new);

		event.registerEntityRenderer(ModEntities.POISON_QUILL.get(), PoisonQuillRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGE_MISSILE.get(), MageMissileRenderer::new);

		event.registerEntityRenderer(ModEntities.ICY_CREEPER.get(), IcyCreeperRenderer::new);

		event.registerEntityRenderer(ModEntities.WRAITH.get(), WraithRenderer::new);

		event.registerEntityRenderer(ModEntities.SIMPLE_TRAP.get(), SimpleTrapRenderer::new);
		event.registerEntityRenderer(ModEntities.KELP_TRAP.get(), KelpTrapRenderer::new);

		event.registerEntityRenderer(ModEntities.WAVEWHISPERER.get(), WavewhispererRenderer::new);
		event.registerEntityRenderer(ModEntities.POISON_ANEMONE.get(), PoisonAnemoneRenderer::new);
		event.registerEntityRenderer(ModEntities.QUICK_GROWING_KELP.get(), QuickGrowingKelpRenderer::new);
		event.registerEntityRenderer(ModEntities.SUNKEN_SKELETON.get(), SunkenSkeletonRenderer::new);

		event.registerEntityRenderer(ModEntities.SQUALL_GOLEM.get(), SquallGolemRenderer::new);

		event.registerEntityRenderer(ModEntities.ENDERSENT.get(), EndersentRenderer::new);
		event.registerEntityRenderer(ModEntities.BLASTLING.get(), BlastlingRenderer::new);
		event.registerEntityRenderer(ModEntities.WATCHLING.get(), WatchlingRenderer::new);
		event.registerEntityRenderer(ModEntities.SNARELING.get(), SnarelingRenderer::new);

		event.registerEntityRenderer(ModEntities.REDSTONE_MINE.get(), RedstoneMineRenderer::new);

		event.registerEntityRenderer(ModEntities.WRAITH_FIRE.get(), WraithFireRenderer::new);

		event.registerEntityRenderer(ModEntities.BLASTLING_BULLET.get(),
				(manager) -> new OrbProjectileRenderer(manager, 0xFFFF93F7, false));
		event.registerEntityRenderer(ModEntities.SNARELING_GLOB.get(), SnarelingGlobRenderer::new);
		event.registerEntityRenderer(ModEntities.COBWEB_PROJECTILE.get(), CobwebProjectileRenderer::new);

		event.registerEntityRenderer(ModEntities.AREA_DAMAGE.get(), EmptyRenderer::new);

		event.registerEntityRenderer(ModEntities.SUMMON_SPOT.get(), SummonSpotRenderer::new);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onParticleFactory(RegisterParticleProvidersEvent event) {
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.DUST.get(),
				DustParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.SNOWFLAKE.get(),
				SnowflakeParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.CORRUPTED_MAGIC.get(),
				CorruptedMagicParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(ModParticleTypes.CORRUPTED_DUST.get(),
				CorruptedDustParticle.Factory::new);
	}
}
