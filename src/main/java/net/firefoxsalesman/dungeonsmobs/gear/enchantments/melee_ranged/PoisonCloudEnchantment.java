package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit.POISON_CLOUD;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.PlayerAttackHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.Timers;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.TimersHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class PoisonCloudEnchantment extends DungeonsEnchantment {

	public PoisonCloudEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	@SubscribeEvent
	public static void onPoisonousWeaponAttack(LivingAttackEvent event) {
		if (event.getSource().getDirectEntity() != event.getSource().getEntity())
			return;
		if (!(event.getSource().getEntity() instanceof LivingEntity))
			return;
		LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
		if (attacker.getLastHurtMobTimestamp() == attacker.tickCount)
			return;
		LivingEntity victim = event.getEntity();
		ItemStack mainhand = attacker.getMainHandItem();
		if (ModEnchantmentHelper.hasEnchantment(mainhand, POISON_CLOUD.get())) {
			float chance = attacker.getRandom().nextFloat();
			int level = EnchantmentHelper.getItemEnchantmentLevel(POISON_CLOUD.get(), mainhand);
			if (chance <= DungeonsGearConfig.POISON_CLOUD_CHANCE.get()
					&& !PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) {
				checkForPlayer(attacker);
				AOECloudHelper.spawnPoisonCloud(attacker, victim, level - 1);
			}
		}
	}

	@SubscribeEvent
	public static void onPoisonBowImpact(ProjectileImpactEvent event) {
		HitResult rayTraceResult = event.getRayTraceResult();
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			if (!ModEnchantmentHelper.shooterIsLiving(arrow))
				return;
			LivingEntity shooter = (LivingEntity) arrow.getOwner();

			int poisonLevel = ArrowHelper.enchantmentTagToLevel(arrow, POISON_CLOUD.get());

			if (poisonLevel > 0) {
				if (rayTraceResult instanceof EntityHitResult) {
					EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
					if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
						LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult)
								.getEntity();
						float poisonRand = shooter.getRandom().nextFloat();
						if (poisonRand <= DungeonsGearConfig.POISON_CLOUD_CHANCE.get()) {
							checkForPlayer(shooter);
							AOECloudHelper.spawnPoisonCloud(shooter, victim,
									poisonLevel - 1);
						}
					}
				}
				if (rayTraceResult instanceof BlockHitResult) {
					BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
					BlockPos blockPos = blockRayTraceResult.getBlockPos();
					float poisonRand = shooter.getRandom().nextFloat();
					if (poisonRand <= 0.3F) {
						checkForPlayer(shooter);
						AOECloudHelper.spawnPoisonCloudAtPos(shooter, true, blockPos,
								poisonLevel);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPoisonEvent(MobEffectEvent.Applicable event) {
		if (event.getEffectInstance().getEffect() == MobEffects.POISON) {
			if (event.getEntity() instanceof Player) {
				Player playerEntity = (Player) event.getEntity();
				Timers timersCapability = TimersHelper.getTimersCapability(playerEntity);
				int enchantmentTimer = timersCapability.getEnchantmentTimer(POISON_CLOUD.get());
				if (enchantmentTimer > 0) {
					event.setResult(Event.Result.DENY);
				}
			}
		}
	}

	private static void checkForPlayer(LivingEntity livingEntity) {
		if (livingEntity instanceof Player) {
			Player playerEntity = (Player) livingEntity;
			Timers timersCapability = TimersHelper.getTimersCapability(playerEntity);
			int enchantmentTimer = timersCapability.getEnchantmentTimer(POISON_CLOUD.get());
			if (enchantmentTimer <= 0) {
				timersCapability.setEnchantmentTimer(POISON_CLOUD.get(), 60);
			}
		}
	}

	public int getMaxLevel() {
		return 3;
	}
}
