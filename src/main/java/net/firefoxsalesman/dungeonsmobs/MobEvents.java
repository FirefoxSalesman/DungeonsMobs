package net.firefoxsalesman.dungeonsmobs;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.entity.entities.creepers.IcyCreeper;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.FrozenZombie;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dungeonsmobs.MOD_ID)
public class MobEvents {
	@SubscribeEvent
	public static void onSnowballHitPlayer(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Snowball snowballEntity = (Snowball) event.getEntity();
			Entity shooter = snowballEntity.getOwner();
			if (shooter instanceof FrozenZombie) {
				snowballEntity.playSound(ModSoundEvents.FROZEN_ZOMBIE_SNOWBALL_LAND.get(), 1.0F, 1.0F);
				HitResult rayTraceResult = event.getRayTraceResult();
				if (rayTraceResult instanceof EntityHitResult) {
					EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
					if (entityRayTraceResult.getEntity() instanceof Player) {
						Player playerEntity = (Player) entityRayTraceResult.getEntity();
						// playerEntity.hurt(DamageSource.thrown(snowballEntity, shooter),
						// 2.0F);
						playerEntity.hurt(playerEntity.damageSources()
								.mobProjectile(snowballEntity, (LivingEntity) shooter),
								2.0F);
						int i = 0;
						if (event.getEntity().level().getDifficulty() == Difficulty.NORMAL) {
							i = 3;
						} else if (event.getEntity().level()
								.getDifficulty() == Difficulty.HARD) {
							i = 6;
						}

						if (i > 0) {
							playerEntity.addEffect(new MobEffectInstance(
									MobEffects.MOVEMENT_SLOWDOWN, i * 20, 1));
						}
					}
				}
			}
		}

	}

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
