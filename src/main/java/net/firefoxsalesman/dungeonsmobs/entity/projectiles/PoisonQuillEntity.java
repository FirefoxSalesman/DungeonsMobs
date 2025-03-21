package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import com.google.common.base.MoreObjects;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public class PoisonQuillEntity extends StraightMovingProjectileEntity implements GeoEntity {

	private static final EntityDataAccessor<Boolean> KELP = SynchedEntityData.defineId(PoisonQuillEntity.class,
			EntityDataSerializers.BOOLEAN);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public PoisonQuillEntity(Level worldIn) {
		super(ModEntities.POISON_QUILL.get(), worldIn);
	}

	public PoisonQuillEntity(EntityType<? extends PoisonQuillEntity> p_i50147_1_, Level p_i50147_2_) {
		super(p_i50147_1_, p_i50147_2_);
	}

	public PoisonQuillEntity(Level p_i1794_1_, LivingEntity p_i1794_2_, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(ModEntities.POISON_QUILL.get(), p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_, p_i1794_1_);
	}

	@OnlyIn(Dist.CLIENT)
	public PoisonQuillEntity(Level p_i1795_1_, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(ModEntities.POISON_QUILL.get(), p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_,
				p_i1795_12_, p_i1795_1_);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return null;
	}

	@Override
	public double getSpawnParticlesY() {
		return 0.2;
	}

	@Override
	public boolean slowedDownInWater() {
		return !this.isKelp();
	}

	@Override
	protected float getInertia() {
		return 1.0F;
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
		this.playSound(ModSoundEvents.JUNGLE_ZOMBIE_STEP.get(), 0.75F, 1.0F + (this.random.nextFloat() * 0.5F));
	}

	public void onHitEntity(Entity entity) {
		if (!this.level().isClientSide) {
			super.onHitEntity(entity);
			boolean flag;
			// flag = entity.hurt(ModDamageSources.poisonQuill(this,
			// MoreObjects.firstNonNull(this.getOwner(), this)), 5.0F);
			flag = entity.hurt(damageSources().mobProjectile(this,
					(LivingEntity) MoreObjects.firstNonNull(this.getOwner(), this)), 5.0F);
			if (entity instanceof LivingEntity) {
				int i = 0;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					i = 8;
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					i = 16;
				}

				if (i > 0) {
					((LivingEntity) entity)
							.addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0));
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

	protected void defineSynchedData() {
		this.entityData.define(KELP, false);
	}

	public boolean isKelp() {
		return this.entityData.get(KELP);
	}

	public void setKelp(boolean p_82343_1_) {
		this.entityData.set(KELP, p_82343_1_);
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
	public boolean getsStuckInBlocks() {
		return true;
	}

	@Override
	public SoundEvent getImpactSound() {
		return null;
	}
}
