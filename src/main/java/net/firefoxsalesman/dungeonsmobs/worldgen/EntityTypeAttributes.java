package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.BlastlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.SnarelingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.ender.WatchlingEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.entity.water.SunkenSkeletonEntity;
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

		event.put(ModEntities.ICY_CREEPER.get(), IcyCreeperEntity.setCustomAttributes().build());

		event.put(ModEntities.SUNKEN_SKELETON.get(), SunkenSkeletonEntity.setCustomAttributes().build());

		event.put(ModEntities.BLASTLING.get(), BlastlingEntity.setCustomAttributes().build());
		event.put(ModEntities.WATCHLING.get(), WatchlingEntity.setCustomAttributes().build());
		event.put(ModEntities.SNARELING.get(), SnarelingEntity.setCustomAttributes().build());
	}
}
