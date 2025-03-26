package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BlastlingBulletEntity extends NecromancerOrbEntity {

	public BlastlingBulletEntity(EntityType<? extends BlastlingBulletEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public BlastlingBulletEntity(Level pLevel, LivingEntity owner, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(ModEntities.BLASTLING_BULLET.get(), owner, p_i1794_3_, p_i1794_5_, p_i1794_7_, pLevel);
	}

	public BlastlingBulletEntity(Level pLevel, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(ModEntities.BLASTLING_BULLET.get(), pLevel, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_,
				p_i1795_10_, p_i1795_12_);
	}

	protected float getInertia() {
		return super.getInertia() * 1.1F;
	}

	public boolean isOnFire() {
		return false;
	}

	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		if (!level().isClientSide) {
			Entity entity = pResult.getEntity();
			Entity entity1 = getOwner();
			if (entity1 instanceof LivingEntity && !(entity instanceof AbstractEnderlingEntity)) {
				LivingEntity livingentity = (LivingEntity) entity1;
				entity.hurt(level().damageSources().mobProjectile(this, livingentity), 4.0F);
			}

		}
	}

	protected void onHit(HitResult p_70227_1_) {
		super.onHit(p_70227_1_);
		playSound(ModSoundEvents.BLASTLING_BULLET_LAND.get(), 1.0F, 1.0F);
		for (int i = 0; i < random.nextInt(35) + 20; ++i) {
			level().addParticle(ParticleTypes.WITCH, getX() + random.nextGaussian(),
					getY() + 0.5D + random.nextGaussian() * (double) 0.13F,
					getZ() + random.nextGaussian(), 0.0D, 0.0D, 0.0D);
		}
		if (!level().isClientSide) {
			remove(RemovalReason.DISCARDED);
		}

	}

	public boolean isPickable() {
		return false;
	}

	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		return false;
	}

	protected boolean shouldBurn() {
		return false;
	}

}
