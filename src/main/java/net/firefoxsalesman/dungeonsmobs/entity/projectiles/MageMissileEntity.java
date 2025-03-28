package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MageMissileEntity extends StraightMovingProjectileEntity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public MageMissileEntity(EntityType<? extends MageMissileEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public MageMissileEntity(Level pLevel, LivingEntity pEntity, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(ModEntities.MAGE_MISSILE.get(), pEntity, p_i1794_3_, p_i1794_5_, p_i1794_7_, pLevel);
	}

	@OnlyIn(Dist.CLIENT)
	public MageMissileEntity(Level pLevel, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(ModEntities.MAGE_MISSILE.get(), p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_,
				p_i1795_12_, pLevel);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ModParticleTypes.CORRUPTED_DUST.get();
	}

	@Override
	protected ParticleOptions getUnderWaterTrailParticle() {
		return ModParticleTypes.CORRUPTED_DUST.get();
	}

	@Override
	public double getSpawnParticlesY() {
		return 0.2;
	}

	@Override
	public boolean slowedDownInWater() {
		return false;
	}

	@Override
	protected float getInertia() {
		return 0.9F;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public boolean isOnFire() {
		return false;
	}

	protected void onHitEntity(EntityHitResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
	}

	@Override
	public void playImpactSound() {
		this.playSound(ModSoundEvents.NECROMANCER_ORB_IMPACT.get(), 0.75F, 2.0F);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (this.lifeTime >= 10 && this.lifeTime <= 20 && this.getOwner() != null
				&& this.getOwner() instanceof Mob && ((Mob) this.getOwner()).getTarget() != null) {
			Mob mob = ((Mob) this.getOwner());
			LivingEntity target = mob.getTarget();
			double d1 = target.getX() - this.getX();
			double d2 = (target.getY() - 2) - this.getY();
			double d3 = target.getZ() - this.getZ();
			double d0 = Mth.sqrt((float) (d1 * d1 + d2 * d2 + d3 * d3));
			if (d0 != 0.0D) {
				this.xPower = d1 / d0 * 0.1D;
				this.yPower = d2 / d0 * 0.1D;
				this.zPower = d3 / d0 * 0.1D;
			}
		}
	}

	public void onHitEntity(Entity entity) {
		if (!level().isClientSide) {
			super.onHitEntity(entity);
			boolean flag;
			flag = entity.hurt(damageSources().indirectMagic(this, this.getOwner()), 2.5F);
			if (entity instanceof LivingEntity) {
				int i = 0;
				if (level().getDifficulty() == Difficulty.NORMAL) {
					i = 8;
				} else if (level().getDifficulty() == Difficulty.HARD) {
					i = 16;
				}

				if (i > 0) {
					((LivingEntity) entity).addEffect(
							new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i * 20, 0));
				}
			}
			if (flag) {
				if (entity.isAlive() && this.getOwner() != null
						&& this.getOwner() instanceof LivingEntity) {
					this.doEnchantDamageEffects((LivingEntity) this.getOwner(), entity);
				}
			}

			this.remove(RemovalReason.DISCARDED);
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

	@Override
	public boolean getsStuckInBlocks() {
		return false;
	}

	@Override
	public SoundEvent getImpactSound() {
		return null;
	}
}
