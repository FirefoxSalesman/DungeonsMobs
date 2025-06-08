package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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

public class DrownedNecromancerOrbEntity extends StraightMovingProjectileEntity implements GeoEntity {

	public int textureChange = 0;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public DrownedNecromancerOrbEntity(Level worldIn) {
		super(ModEntities.DROWNED_NECROMANCER_ORB.get(), worldIn);
	}

	public DrownedNecromancerOrbEntity(EntityType<? extends DrownedNecromancerOrbEntity> pEntityType, Level level) {
		super(pEntityType, level);
	}

	public DrownedNecromancerOrbEntity(Level level, LivingEntity entity, double p_i1794_3_, double p_i1794_5_,
			double p_i1794_7_) {
		super(ModEntities.DROWNED_NECROMANCER_ORB.get(), entity, p_i1794_3_, p_i1794_5_, p_i1794_7_, level);
	}

	@OnlyIn(Dist.CLIENT)
	public DrownedNecromancerOrbEntity(Level level, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_,
			double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
		super(ModEntities.DROWNED_NECROMANCER_ORB.get(), p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_,
				p_i1795_10_,
				p_i1795_12_, level);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ModParticleTypes.NECROMANCY.get();
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
		return 0.8F;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (this.tickCount % 5 == 0) {
			textureChange++;
		}

		if (!level().isClientSide && !this.isInWaterRainOrBubble()) {
			this.playSound(ModSoundEvents.DROWNED_NECROMANCER_STEAM_MISSILE_IMPACT.get(), 1.0F, 1.0F);
			this.remove(RemovalReason.DISCARDED);
		}
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

	public void onHitEntity(Entity entity) {
		if (entity instanceof Mob && ((Mob) entity).getMobType() == MobType.UNDEAD) {

		} else if (!level().isClientSide) {
			super.onHitEntity(entity);
			Entity entity1 = this.getOwner();
			boolean flag;
			if (entity1 instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity1;
				flag = entity.hurt(damageSources().mobProjectile(this, livingentity), 8.0F);
				if (flag) {
					if (entity.isAlive()) {
						this.doEnchantDamageEffects(livingentity, entity);
					}
				}
			} else {
				flag = entity.hurt(damageSources().magic(), 6.0F);
			}

			entity.getRootVehicle().ejectPassengers();

			entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().scale(2.0D)));

			this.playSound(ModSoundEvents.DROWNED_NECROMANCER_STEAM_MISSILE_IMPACT.get(), 1.0F, 1.0F);
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
	public SoundEvent getImpactSound() {
		return ModSoundEvents.DROWNED_NECROMANCER_STEAM_MISSILE_IMPACT.get();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
