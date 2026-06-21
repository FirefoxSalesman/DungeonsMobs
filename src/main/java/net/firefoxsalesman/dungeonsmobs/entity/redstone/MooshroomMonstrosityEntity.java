package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MooshroomMonstrosityProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MooshroomMonstrosityEntity extends AbstractMonstrosityEntity {

	public MooshroomMonstrosityEntity(EntityType<? extends AbstractMonstrosityEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	protected void doSpewAction(Vec3 pos, LivingEntity target) {
		double d1 = target.getX() - pos.x;
		double d2 = target.getY(0.6D) - pos.y;
		double d3 = target.getZ() - pos.z;
		MooshroomMonstrosityProjectileEntity projectile = new MooshroomMonstrosityProjectileEntity(level(),
				this, d1, d2, d3);

		projectile.rotateToMatchMovement();
		projectile.moveTo(pos.x, pos.y, pos.z);
		level().addFreshEntity(projectile);
	}

	@Override
	protected List<? extends String> getSummonConfig() {
		return DungeonsMobsConfig.Common.MOOSHROOM_MONSTROSITY_MOB_SUMMONS.get();
	}

	@Override
	protected EntityType<? extends Mob> getSummonType() {
		return EntityType.MOOSHROOM;
	}

}
