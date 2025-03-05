package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.JungleZombie;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dungeonsmobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeAttributes {
	@SubscribeEvent
	public static void initEntityTypeAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.JUNGLE_ZOMBIE.get(), JungleZombie.setCustomAttributes().build());
	}
}
