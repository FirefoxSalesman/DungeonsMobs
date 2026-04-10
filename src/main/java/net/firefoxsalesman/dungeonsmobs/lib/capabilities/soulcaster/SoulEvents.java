package net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_GATHERING;

import net.firefoxsalesman.dungeonsmobs.lib.entities.SoulOrbEntity;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class SoulEvents {

	@SubscribeEvent
	public static void onSoulSpawning(LivingDeathEvent event) {
		LivingEntity entityLiving = event.getEntity();
		Entity sourceEntity = event.getSource().getEntity();
		if (sourceEntity instanceof Player) {
			double soulAmount = ((Player) sourceEntity).getAttributeValue(SOUL_GATHERING.get());
			if (soulAmount > 0) {
				entityLiving.level().addFreshEntity(new SoulOrbEntity((Player) sourceEntity,
						entityLiving.level(), entityLiving.getX(), entityLiving.getY() + 0.5D,
						entityLiving.getZ(), (float) soulAmount));
			}
		}
	}
}
