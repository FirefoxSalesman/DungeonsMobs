package net.firefoxsalesman.dungeonsmobs;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.Convertible;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.ConvertibleHelper;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class MobEvents {
	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity instanceof Mob && ConvertibleHelper.convertsInWater((Mob) livingEntity)) {
			Mob mob = (Mob) livingEntity;
			if (!mob.level().isClientSide && mob.isAlive() && !mob.isNoAi()) {
				Convertible convertibleCap = ConvertibleHelper.getConvertibleCapability(mob);
				if (convertibleCap == null)
					return;

				convertibleCap.setCanConvert(mob.isEyeInFluid(FluidTags.WATER));

				if (convertibleCap.isConverting()) {
					convertibleCap.tickConversionTime();

					EntityType<? extends Mob> convertToType = ConvertibleHelper
							.getDrowningConvertTo(mob);

					if (convertibleCap.getConversionTime() < 0
							&& net.minecraftforge.event.ForgeEventFactory.canLivingConvert(
									mob, convertToType,
									convertibleCap::setConversionTime)) {
						convertibleCap.doConversion(mob, convertToType,
								ConvertibleHelper::onDrownedAndConvertedTo);
					}
				} else {
					if (convertibleCap.canConvert()) {
						convertibleCap.tickPrepareConversionTime();
						if (convertibleCap.getPrepareConversionTime() >= 600) {
							convertibleCap.startConversion(300);
						}
					} else {
						convertibleCap.setPrepareConversionTime(-1);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onSnowballHitPlayer(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Snowball snowballEntity = (Snowball) event.getEntity();
			Entity shooter = snowballEntity.getOwner();
			if (shooter instanceof FrozenZombieEntity) {
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
		if (event.getExplosion().getExploder() instanceof IcyCreeperEntity) {
			if (!DungeonsMobsConfig.COMMON.ENABLE_ICY_CREEPER_GRIEFING.get()) {
				event.getAffectedBlocks().clear();
			}
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
