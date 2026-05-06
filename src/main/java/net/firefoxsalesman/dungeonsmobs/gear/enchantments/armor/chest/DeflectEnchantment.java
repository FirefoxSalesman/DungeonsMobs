package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent.ImpactResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DeflectEnchantment extends DungeonsEnchantment {

	public DeflectEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof ProtectionEnchantment);
	}

	@SubscribeEvent
	public static void onDeflectImpact(ProjectileImpactEvent event) {
		HitResult rayTraceResult = event.getRayTraceResult();
		if (!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult))
			return;
		Projectile projectile = event.getProjectile();
		if (projectile instanceof AbstractArrow arrow) {
			if (!ModEnchantmentHelper.shooterIsLiving(arrow))
				return;
			if (arrow.getPierceLevel() > 0)
				return;
			LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
			if (ModEnchantmentHelper.hasEnchantment(victim, EnchantmentInit.DEFLECT.get())) {
				int deflectLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DEFLECT.get(),
						victim);
				double originalDamage = arrow.getBaseDamage();
				double deflectChance;
				deflectChance = deflectLevel * DungeonsGearConfig.DEFLECT_CHANCE_PER_LEVEL.get();
				float deflectRand = victim.getRandom().nextFloat();
				if (deflectRand <= deflectChance) {
					event.setImpactResult(ImpactResult.STOP_AT_CURRENT_NO_DAMAGE);
					arrow.setBaseDamage(originalDamage * 0.5D);
					arrow.setYRot(arrow.getYRot() + 180.0F);
					arrow.yRotO += 180.0F;
				}
			}
		}
	}
}
