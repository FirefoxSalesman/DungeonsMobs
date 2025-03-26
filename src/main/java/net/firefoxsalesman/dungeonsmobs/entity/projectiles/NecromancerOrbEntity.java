package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NecromancerOrbEntity extends StraightMovingProjectileEntity implements GeoEntity {

	private static final EntityDataAccessor<Boolean> DELAYED_FORM = SynchedEntityData.defineId(
			NecromancerOrbEntity.class,
			EntityDataSerializers.BOOLEAN);

	public int formAnimationTick;
	public int formAnimationLength = 20;

	public int vanishAnimationTick;

	public int textureChange = 0;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public NecromancerOrbEntity(Level worldIn) {
		super(ModEntities.NECROMANCER_ORB.get(), worldIn);
	}

	public NecromancerOrbEntity(EntityType<? extends NecromancerOrbEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public NecromancerOrbEntity(Level pLevel, LivingEntity pOwner, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(ModEntities.NECROMANCER_ORB.get(), pOwner, p_i1794_3_, p_i1794_5_, p_i1794_7_, pLevel);
	}

	public NecromancerOrbEntity(EntityType<? extends NecromancerOrbEntity> necromancerOrbEntityType,
			Level pLevel, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(necromancerOrbEntityType, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_,
				p_i1795_12_, pLevel);
	}

	public NecromancerOrbEntity(Level pLevel, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(ModEntities.NECROMANCER_ORB.get(), p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_,
				p_i1795_12_, pLevel);
	}

	public NecromancerOrbEntity(EntityType<? extends NecromancerOrbEntity> necromancerOrbEntityType,
			LivingEntity p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_,
			Level pLevel) {
		super(necromancerOrbEntityType, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_, pLevel);
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 1) {
			vanishAnimationTick = getVanishAnimationLength();
		} else if (p_28844_ == 2) {
			formAnimationTick = formAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		if (getType().equals(ModEntities.BLASTLING_BULLET.get())) {
			return null;
		}
		return ModParticleTypes.NECROMANCY.get();
	}

	@Override
	public double getSpawnParticlesY() {
		return 0.2;
	}

	@Override
	public boolean shouldSpawnParticles() {
		return vanishAnimationTick <= 0;
	}

	@Override
	protected float getInertia() {
		return 0.8F;
	}

	public void startForming() {
		if (!level().isClientSide) {
			formAnimationTick = formAnimationLength;
			level().broadcastEntityEvent(this, (byte) 2);
		}
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		tickDownAnimTimers();

		if (tickCount % 5 == 0) {
			textureChange++;
		}

		if (!level().isClientSide && lifeTime >= vanishAfterTime()
				&& vanishAnimationTick <= 0) {
			vanishAnimationTick = getVanishAnimationLength();
			level().broadcastEntityEvent(this, (byte) 1);
		}

		if (!level().isClientSide && hasDelayedForm()) {
			startForming();
			setDelayedForm(false);
		}

		if (!level().isClientSide && vanishAnimationTick > 0) {
			setDeltaMovement(0, 0, 0);
		}

		if (!level().isClientSide && vanishAnimationTick == 2) {
			remove(RemovalReason.DISCARDED);
		}
	}

	public void tickDownAnimTimers() {
		if (formAnimationTick > 0) {
			formAnimationTick--;
		}

		if (vanishAnimationTick > 0) {
			vanishAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (vanishAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("necromancer_orb_vanish", LoopType.LOOP));
		} else if (formAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("necromancer_orb_form", LoopType.LOOP));
		} else {
			event.getController()
					.setAnimation(RawAnimation.begin().then("necromancer_orb_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DELAYED_FORM, false);
	}

	public boolean hasDelayedForm() {
		return entityData.get(DELAYED_FORM);
	}

	public void setDelayedForm(boolean attached) {
		entityData.set(DELAYED_FORM, attached);
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

	public void onHitEntity(Entity entity) {
		if (entity instanceof Mob && ((Mob) entity).getMobType() == MobType.UNDEAD) {

		} else if (!level().isClientSide) {
			super.onHitEntity(entity);
			Entity entity1 = getOwner();
			boolean flag;
			if (entity1 instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity1;
				flag = entity.hurt(damageSources().indirectMagic(this, livingentity), 6.0F);
				if (flag) {
					if (entity.isAlive()) {
						doEnchantDamageEffects(livingentity, entity);
					}
				}
			} else {
				flag = entity.hurt(entity.damageSources().magic(), 6.0F);
			}

			entity.getRootVehicle().ejectPassengers();

			entity.setDeltaMovement(entity.getDeltaMovement().add(getDeltaMovement().scale(2.0D)));

			playSound(ModSoundEvents.NECROMANCER_ORB_IMPACT.get(), 1.0F, 1.0F);
			vanishAnimationTick = getVanishAnimationLength();
			level().broadcastEntityEvent(this, (byte) 1);
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
	public SoundEvent getImpactSound() {
		return ModSoundEvents.NECROMANCER_ORB_IMPACT.get();
	}

	@Override
	public int getVanishAnimationLength() {
		return 40;
	}
}
