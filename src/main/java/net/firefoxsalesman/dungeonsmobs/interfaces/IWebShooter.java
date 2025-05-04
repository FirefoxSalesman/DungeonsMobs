package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.CobwebProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public interface IWebShooter extends ITrapsTarget {

	boolean isWebShooting();

	void setWebShooting(boolean webShooting);

	// Web Shooting has been modified for more control over range
	static void shootWeb(Mob webShooter, LivingEntity target) {
		Vec3 pos = PositionUtils.getOffsetPos(webShooter, 0.0, 1.0, -0.75, webShooter.yBodyRot);

		CobwebProjectileEntity projectile = new CobwebProjectileEntity(webShooter.level(), webShooter);
		projectile.setPos(pos.x, pos.y, pos.z);
		double aimX = target.getX() - pos.x;
		double aimY = target.getY(0.3333333333333333D) - pos.y;
		double aimZ = target.getZ() - pos.z;
		float f = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ)) * 0.2F;

		float horizontalDistance = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ));
		float velocity = Mth.clamp(horizontalDistance * 0.25F, 1.0F, 1.5F);  // Clamp velocity for longer shots
		float inaccuracy = 2.0F;
		projectile.shoot(aimX, aimY + (double) f, aimZ, velocity, inaccuracy);

		if (!webShooter.isSilent()) {
			webShooter.playSound(ModSoundEvents.SPIDER_SHOOT.get(), 1.0F, 1.0F);
		}

		webShooter.level().addFreshEntity(projectile);
		projectile.delayedSpawnParticles = true;
	}
}
