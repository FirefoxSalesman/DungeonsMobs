package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.MooshroomMonstrosityProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.utils.AreaAttackHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MooshroomMonstrosityEntity extends AbstractMonstrosityEntity {

	public MooshroomMonstrosityEntity(EntityType<? extends AbstractMonstrosityEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel, "animation.mooshroom_monstrosity.slam_attack", 46, 24);
	}

	@Override
	protected void doSpewAction(Vec3 pos, LivingEntity target) {
		double d1 = target.getX() - pos.x;
		double d2 = target.getY(0.6D) - pos.y;
		double d3 = target.getZ() - pos.z;
		for (float i = 0; i < 7; i++) {
			float rot = yHeadRot - 45F + (22.5F * i);
			Vec3 v = new Vec3(-Math.sin(Math.toRadians(rot)), 0, Math.cos(Math.toRadians(rot)));
			MooshroomMonstrosityProjectileEntity projectile = new MooshroomMonstrosityProjectileEntity(
					level(),
					this, d1 + pos.x, d2, d3 + pos.z);

			projectile.rotateToMatchMovement();
			v = v.scale(3);
			projectile.moveTo(pos.x + v.x, pos.y, pos.z + v.z);
			level().addFreshEntity(projectile);

		}

		AreaAttackHelper.areaAttack(5, 5, 5, 5, 360, 1.0F, this);
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
