package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;

import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.FREEZING_DURATION;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class FreezingShotEnchantment extends DungeonsEnchantment {

	public FreezingShotEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return enchantment != Enchantments.FLAMING_ARROWS;
	}

	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
		HitResult rayTraceResult = event.getRayTraceResult();
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			if (!ModEnchantmentHelper.shooterIsLiving(arrow))
				return;
			int freezingLevel = ArrowHelper.enchantmentTagToLevel(arrow,
					EnchantmentInit.FREEZING_SHOT.get());
			if (freezingLevel > 0) {
				if (rayTraceResult instanceof EntityHitResult) {
					EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
					if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
						LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult)
								.getEntity();
						applyFreezing(victim, freezingLevel);
					}
				}
			}
		}
	}

	private static void applyFreezing(LivingEntity target, int level) {
		MobEffectInstance freezing = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
				FREEZING_DURATION.get(), level - 1);
		MobEffectInstance miningFatigue = new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
				FREEZING_DURATION.get(), level - 1);
		target.addEffect(freezing);
		target.addEffect(miningFatigue);
		PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
	}
}
