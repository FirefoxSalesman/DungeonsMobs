package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class FuseShotEnchantment extends DungeonsEnchantment {

	public static final String FUSE_SHOT_TAG = "FuseShot";

	public FuseShotEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onFuseShotImpact(ProjectileImpactEvent event) {
		if (event.getProjectile() instanceof AbstractArrow arrow) {
			if (ModEnchantmentHelper.shooterIsLiving(arrow)) {
				LivingEntity shooter = (LivingEntity) arrow.getOwner();
				if (arrow.getTags().contains(FUSE_SHOT_TAG)) {
					if (event.getRayTraceResult() instanceof BlockHitResult) {
						BlockHitResult blockRayTraceResult = (BlockHitResult) event
								.getRayTraceResult();
						BlockPos blockPos = blockRayTraceResult.getBlockPos();
						float f = (float) arrow.getDeltaMovement().length();
						int damage = Mth.ceil(Mth.clamp((double) f * arrow.getBaseDamage(),
								0.0D, 2.147483647E9D));
						if (arrow.isCritArrow()) {
							long criticalDamageBonus = shooter.getRandom()
									.nextInt(damage / 2 + 2);
							damage = (int) Math.min(criticalDamageBonus + (long) damage,
									2147483647L);
						}
						SoundHelper.playGenericExplodeSound(arrow);
						AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
						AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos,
								damage, 3.0F);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onFuseShotDamage(LivingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		if (ArrowHelper.wasHitByArrow(damageSource)) {
			AbstractArrow arrowEntity = (AbstractArrow) damageSource.getDirectEntity();

			LivingEntity victim = event.getEntity();
			if (damageSource.getEntity() instanceof LivingEntity) {
				LivingEntity archer = (LivingEntity) damageSource.getEntity();
				if (arrowEntity.getTags().contains(FUSE_SHOT_TAG)) {
					SoundHelper.playGenericExplodeSound(arrowEntity);
					AOECloudHelper.spawnExplosionCloud(archer, victim, 3.0f);
					AreaOfEffectHelper.causeExplosionAttack(archer, victim,
							event.getAmount(), 3.0f);
				}
			}
		}
	}
}
