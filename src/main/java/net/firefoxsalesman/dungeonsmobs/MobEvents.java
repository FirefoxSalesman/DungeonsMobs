package net.firefoxsalesman.dungeonsmobs;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.entity.entities.creepers.IcyCreeper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dungeonsmobs.MOD_ID)
public class MobEvents {
	@SubscribeEvent
	public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
		if (event.getExplosion().getExploder() instanceof IcyCreeper) {
			List<Entity> entityList = event.getAffectedEntities();
			for (Entity entity : entityList) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.addEffect(
							new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
				}
			}
		}
	}
}
