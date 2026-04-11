package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
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
public class SuperchargeEnchantment extends DungeonsEnchantment {

	public SuperchargeEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return enchantment != Enchantments.PUNCH_ARROWS || enchantment != Enchantments.POWER_ARROWS;
	}

	@SubscribeEvent
	public static void onSuperchargeImpact(ProjectileImpactEvent event) {
		if (!ModEnchantmentHelper.arrowHitLivingEntity(event.getRayTraceResult()))
			return;
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			try {
				if (!ModEnchantmentHelper.shooterIsLiving(arrow))
					return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int superchargeLevel = ArrowHelper.enchantmentTagToLevel(arrow,
					EnchantmentInit.SUPERCHARGE.get());
			if (superchargeLevel > 0) {
				double originalDamage = arrow.getBaseDamage();
				int originalKnockback = arrow.getKnockback();
				double damageModifier = 1.0D + 0.2D * superchargeLevel;
				arrow.setBaseDamage(originalDamage * damageModifier);
				arrow.setKnockback(originalKnockback + 1);
			}
		}
	}
}
