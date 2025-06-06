package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.golem.SquallGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.GeomancerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.IceologerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageCloneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MageEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.RoyalGuardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.WindcallerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.LeapleafEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.PoisonQuillVineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.QuickGrowingVineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.WhispererEntity;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneGolemEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerBombEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.GeomancerWallEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.PoisonAnemoneEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.QuickGrowingKelpEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.SunkenSkeletonEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeAttributes {
	@SubscribeEvent
	public static void initEntityTypeAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.JUNGLE_ZOMBIE.get(), JungleZombieEntity.setCustomAttributes().build());
		event.put(ModEntities.FROZEN_ZOMBIE.get(), FrozenZombieEntity.setCustomAttributes().build());

		event.put(ModEntities.MOSSY_SKELETON.get(), MossySkeletonEntity.setCustomAttributes().build());
		event.put(ModEntities.SKELETON_VANGUARD.get(), SkeletonVanguardEntity.setCustomAttributes().build());

		event.put(ModEntities.ROYAL_GUARD.get(), RoyalGuardEntity.setCustomAttributes().build());
		event.put(ModEntities.MOUNTAINEER.get(), MountaineerEntity.setCustomAttributes().build());

		event.put(ModEntities.ICEOLOGER.get(), IceologerEntity.setCustomAttributes().build());
		event.put(ModEntities.GEOMANCER.get(), GeomancerEntity.setCustomAttributes().build());
		event.put(ModEntities.WINDCALLER.get(), WindcallerEntity.setCustomAttributes().build());
		event.put(ModEntities.MAGE.get(), MageEntity.setCustomAttributes().build());
		event.put(ModEntities.MAGE_CLONE.get(), MageCloneEntity.setCustomAttributes().build());

		event.put(ModEntities.ICY_CREEPER.get(), IcyCreeperEntity.setCustomAttributes().build());

		event.put(ModEntities.WRAITH.get(), WraithEntity.setCustomAttributes().build());

		event.put(ModEntities.REDSTONE_GOLEM.get(), RedstoneGolemEntity.setCustomAttributes().build());

		event.put(ModEntities.WHISPERER.get(), WhispererEntity.setCustomAttributes().build());
		event.put(ModEntities.LEAPLEAF.get(), LeapleafEntity.setCustomAttributes().build());
		event.put(ModEntities.QUICK_GROWING_VINE.get(),
				QuickGrowingVineEntity.setCustomAttributes().build());
		event.put(ModEntities.POISON_QUILL_VINE.get(), PoisonQuillVineEntity.setCustomAttributes().build());

		event.put(ModEntities.SQUALL_GOLEM.get(), SquallGolemEntity.setCustomAttributes().build());

		event.put(ModEntities.GEOMANCER_WALL.get(), GeomancerWallEntity.setCustomAttributes().build());
		event.put(ModEntities.GEOMANCER_BOMB.get(), GeomancerBombEntity.setCustomAttributes().build());

		event.put(ModEntities.FUNGUS_THROWER.get(), Piglin.createAttributes().build());
		event.put(ModEntities.ZOMBIFIED_FUNGUS_THROWER.get(), ZombifiedPiglin.createAttributes().build());

		event.put(ModEntities.SUNKEN_SKELETON.get(), SunkenSkeletonEntity.setCustomAttributes().build());
		event.put(ModEntities.WAVEWHISPERER.get(), WhispererEntity.setCustomAttributes().build());
		event.put(ModEntities.QUICK_GROWING_KELP.get(),
				QuickGrowingKelpEntity.setCustomAttributes().build());
		event.put(ModEntities.POISON_ANEMONE.get(), PoisonAnemoneEntity.setCustomAttributes().build());

		event.put(ModEntities.ENDERSENT.get(), EndersentEntity.setCustomAttributes().build());
		event.put(ModEntities.BLASTLING.get(), BlastlingEntity.setCustomAttributes().build());
		event.put(ModEntities.WATCHLING.get(), WatchlingEntity.setCustomAttributes().build());
		event.put(ModEntities.SNARELING.get(), SnarelingEntity.setCustomAttributes().build());
	}
}
