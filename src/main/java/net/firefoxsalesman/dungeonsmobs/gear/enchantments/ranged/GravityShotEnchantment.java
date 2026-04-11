package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
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
public class GravityShotEnchantment extends DungeonsEnchantment {

	public GravityShotEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	@Override
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
			int gravityLevel = ArrowHelper.enchantmentTagToLevel(arrow, EnchantmentInit.GRAVITY_SHOT.get());
			if (gravityLevel > 0) {
				if (rayTraceResult instanceof BlockHitResult) {
					BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
					BlockPos blockPos = blockRayTraceResult.getBlockPos();
					AreaOfEffectHelper.pullInNearbyEntitiesAtPos(shooter, blockPos,
							3 * gravityLevel, ParticleTypes.PORTAL);
				}
			}
		}
	}
}
