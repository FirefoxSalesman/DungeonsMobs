package net.firefoxsalesman.dungeonsmobs.gear.entities;

import net.firefoxsalesman.dungeonsmobs.entity.projectiles.StraightMovingProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EntityTypeInit;
import net.firefoxsalesman.dungeonsmobs.gear.registry.ParticleInit;
import net.firefoxsalesman.dungeonsmobs.gear.registry.SoundEventInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SoulWizardOrbEntity extends StraightMovingProjectileEntity implements GeoEntity {

	public int textureChange = 0;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public SoulWizardOrbEntity(Level worldIn) {
		super(EntityTypeInit.SOUL_WIZARD_ORB.get(), worldIn);
	}

	public SoulWizardOrbEntity(EntityType<? extends SoulWizardOrbEntity> type, Level level) {
		super(type, level);
	}

	public SoulWizardOrbEntity(Level level, LivingEntity entity, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(EntityTypeInit.SOUL_WIZARD_ORB.get(), entity, p_i1794_3_, p_i1794_5_, p_i1794_7_, level);
	}

	@OnlyIn(Dist.CLIENT)
	public SoulWizardOrbEntity(Level level, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(EntityTypeInit.SOUL_WIZARD_ORB.get(), p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_,
				p_i1795_12_, level);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleInit.SOUL_DUST.get();
	}

	@Override
	public double getSpawnParticlesY() {
		return 0.2;
	}

	@Override
	protected float getInertia() {
		return 1.0F;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (this.tickCount % 2 == 0) {
			textureChange++;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		return PlayState.CONTINUE;
	}

	public boolean isOnFire() {
		return false;
	}

	protected void onHitEntity(EntityHitResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
	}

	public void onHitEntity(Entity entity) {
		if (!this.level().isClientSide) {
			super.onHitEntity(entity);
			Entity entity1 = this.getOwner();
			boolean flag;
			if (entity1 instanceof LivingEntity livingentity) {
				flag = entity.hurt(damageSources().magic(), 6.0F);
				if (flag) {
					if (entity.isAlive()) {
						this.doEnchantDamageEffects(livingentity, entity);
					}
				}
			} else {
				flag = entity.hurt(damageSources().magic(), 6.0F);
			}

			entity.getRootVehicle().ejectPassengers();

			entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().scale(0.1D)));

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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public SoundEvent getImpactSound() {
		return SoundEventInit.SOUL_WIZARD_PROJECTILE_IMPACT.get();
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
