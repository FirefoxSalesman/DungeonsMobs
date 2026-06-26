package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.RedstoneMonstrosityProjectileEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RedstoneMonstrosityEntity extends AbstractMonstrosityEntity {
	public RedstoneMonstrosityEntity(EntityType<? extends AbstractMonstrosityEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel, "animation.redstone_monstrosity.spit", 48, 18);
	}

	@Override
	protected void doSpewAction(Vec3 pos, LivingEntity target) {
		for (int i = 0; i < 8; i++) {
			RedstoneMonstrosityProjectileEntity projectile = new RedstoneMonstrosityProjectileEntity(
					level(), this);
			projectile.setPos(pos.x, getEyeY(), pos.z);
			double aimX = target.getX() - pos.x;
			double aimY = target.getY(0.3333333333333333D) - pos.y;
			double aimZ = target.getZ() - pos.z;
			if (i > 0) {
				aimX += getRandom().nextInt(-5, 5);
				aimY += getRandom().nextInt(-5, 5);
				aimZ += getRandom().nextInt(-5, 5);
			}
			float f = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ)) * 0.2F;

			float horizontalDistance = Mth.sqrt((float) (aimX * aimX + aimZ * aimZ));
			float velocity = Mth.clamp(horizontalDistance * 0.25F, 1.0F, 1.5F); // Clamp velocity for longer
			// shots
			float inaccuracy = 2.0F;
			projectile.shoot(aimX, aimY + (double) f, aimZ, velocity, inaccuracy);

			level().addFreshEntity(projectile);
		}
	}

	@Override
	protected EntityType<? extends Mob> getSummonType() {
		return ModEntities.REDSTONE_CUBE.get();
	}

	@Override
	protected List<? extends String> getSummonConfig() {
		return DungeonsMobsConfig.Common.REDSTONE_MONSTROSITY_MOB_SUMMONS.get();
	}
}
