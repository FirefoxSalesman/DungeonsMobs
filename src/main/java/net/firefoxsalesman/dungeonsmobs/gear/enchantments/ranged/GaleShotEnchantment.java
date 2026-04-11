package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GaleShotEnchantment extends DungeonsEnchantment {

	public GaleShotEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
		HitResult rayTraceResult = event.getRayTraceResult();
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			if (!ModEnchantmentHelper.shooterIsLiving(arrow))
				return;
			LivingEntity shooter = (LivingEntity) arrow.getOwner();
			int enchantmentLevel = ArrowHelper.enchantmentTagToLevel(arrow,
					EnchantmentInit.GALE_SHOT.get());
			if (enchantmentLevel > 0) {
				if (rayTraceResult instanceof EntityHitResult) {
					EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
					if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
						LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult)
								.getEntity();
						AreaOfEffectHelper.pullVictimTowardsTarget(shooter, victim,
								ParticleTypes.ENTITY_EFFECT,
								AreaOfEffectHelper.PULL_IN_SPEED_FACTOR
										* enchantmentLevel);
					}
				}
			}
		}
	}
}
