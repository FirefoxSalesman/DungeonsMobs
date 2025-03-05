package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.entities.creepers.IcyCreeper;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.FrozenZombie;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.JungleZombie;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.MossySkeleton;
import net.firefoxsalesman.dungeonsmobs.entity.entities.water.SunkenSkeletonEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dungeonsmobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeAttributes {
	@SubscribeEvent
	public static void initEntityTypeAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.JUNGLE_ZOMBIE.get(), JungleZombie.setCustomAttributes().build());
		event.put(ModEntities.FROZEN_ZOMBIE.get(), FrozenZombie.setCustomAttributes().build());

		event.put(ModEntities.MOSSY_SKELETON.get(), MossySkeleton.setCustomAttributes().build());

		event.put(ModEntities.ICY_CREEPER.get(), IcyCreeper.setCustomAttributes().build());

		event.put(ModEntities.SUNKEN_SKELETON.get(), SunkenSkeletonEntity.setCustomAttributes().build());
	}
}
