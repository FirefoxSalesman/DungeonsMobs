package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class TempoTheftEnchantment extends DungeonsEnchantment {

	public static final String INTRINSIC_TEMPO_THEFT_TAG = "IntrinsicTempoTheft";

	public TempoTheftEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onNocturnalBowImpact(ProjectileImpactEvent event) {
		HitResult rayTraceResult = event.getRayTraceResult();
		if (!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult))
			return;
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			try {
				if (!ModEnchantmentHelper.shooterIsLiving(arrow))
					return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LivingEntity shooter = (LivingEntity) arrow.getOwner();
			LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
			int tempoTheftLevel = ArrowHelper.enchantmentTagToLevel(arrow,
					EnchantmentInit.TEMPO_THEFT.get());
			boolean uniqueWeaponFlag = arrow.getTags().contains(INTRINSIC_TEMPO_THEFT_TAG);
			if (tempoTheftLevel > 0 || uniqueWeaponFlag) {
				if (uniqueWeaponFlag)
					tempoTheftLevel++;
				if (shooter == victim)
					return;
				MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80,
						tempoTheftLevel - 1);
				MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80,
						tempoTheftLevel - 1);
				shooter.addEffect(speed);
				victim.addEffect(slowness);
			}
		}
	}
}
