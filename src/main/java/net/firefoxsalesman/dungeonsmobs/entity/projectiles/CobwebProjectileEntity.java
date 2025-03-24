package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SimpleTrapEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CobwebProjectileEntity extends Projectile implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public boolean delayedSpawnParticles;

	public CobwebProjectileEntity(EntityType<? extends CobwebProjectileEntity> p_i50162_1_, Level p_i50162_2_) {
		super(p_i50162_1_, p_i50162_2_);
	}

	public CobwebProjectileEntity(Level pLevel, LivingEntity pEntity) {
		this(ModEntities.COBWEB_PROJECTILE.get(), pLevel);
		super.setOwner(pEntity);
		this.setPos(pEntity.getX() - (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
				* (double) Mth.sin(pEntity.yBodyRot * ((float) Math.PI / 180F)),
				pEntity.getEyeY() - (double) 0.1F,
				pEntity.getZ() + (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
						* (double) Mth.cos(pEntity.yBodyRot * ((float) Math.PI / 180F)));
	}

	@OnlyIn(Dist.CLIENT)
	public CobwebProjectileEntity(Level pLevel, double p_i47274_2_, double p_i47274_4_, double p_i47274_6_,
			double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
		this(ModEntities.COBWEB_PROJECTILE.get(), pLevel);
		this.setPos(p_i47274_2_, p_i47274_4_, p_i47274_6_);

		for (int i = 0; i < 7; ++i) {
			double d0 = 0.4D + 0.1D * (double) i;
			pLevel.addParticle(ParticleTypes.SPIT, p_i47274_2_, p_i47274_4_, p_i47274_6_,
					p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
		}

		this.setDeltaMovement(p_i47274_8_, p_i47274_10_, p_i47274_12_);
	}

	public void tick() {
		super.tick();

		if (this.delayedSpawnParticles) {
			this.delayedSpawnParticles = false;
			this.createSpawnParticles();
		}

		Vec3 vector3d = this.getDeltaMovement();
		HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this,
						raytraceresult)) {
			this.onHit(raytraceresult);
		}

		double d0 = this.getX() + vector3d.x;
		double d1 = this.getY() + vector3d.y;
		double d2 = this.getZ() + vector3d.z;
		this.updateRotation();
		float f = 0.99F;
		float f1 = 0.06F;
		if (this.level().getBlockStates(this.getBoundingBox())
				.noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
			this.remove(RemovalReason.DISCARDED);
		} else if (this.isInWaterOrBubble()) {
			this.remove(RemovalReason.DISCARDED);
		} else {
			this.setDeltaMovement(vector3d.scale(f));
			if (!this.isNoGravity()) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -f1, 0.0D));
			}

			this.setPos(d0, d1, d2);
		}
	}

	protected void onHitEntity(EntityHitResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		Entity entity = this.getOwner();
		if (p_213868_1_.getEntity() instanceof LivingEntity
				&& ((LivingEntity) p_213868_1_.getEntity()).getMobType() == MobType.ARTHROPOD) {

		} else {
			if (entity instanceof LivingEntity) {
				p_213868_1_.getEntity()
						.hurt(damageSources().mobProjectile(this, (LivingEntity) entity), 1.0F);
			}

			if (!this.level().isClientSide) {
				this.spawnTrap(p_213868_1_.getEntity().getX(), p_213868_1_.getEntity().getY(),
						p_213868_1_.getEntity().getZ());

				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	public void createSpawnParticles() {
		if (!this.level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 1);
		} else {
			for (int i = 0; i < 5; i++) {
				this.level().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(),
						0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 1) {
			for (int i = 0; i < 5; i++) {
				this.level().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(),
						0.0D,
						0.0D, 0.0D);
			}
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	protected void onHitBlock(BlockHitResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		if (!this.level().isClientSide) {
			this.spawnTrap(this.getX(), this.getY(), this.getZ());

			this.remove(RemovalReason.DISCARDED);
		}

	}

	public void spawnTrap(double x, double y, double z) {
		SimpleTrapEntity trap = ModEntities.SIMPLE_TRAP.get().create(this.level());
		trap.moveTo(x, y, z);
		trap.owner = this.getOwner();

		this.level().addFreshEntity(trap);

		this.playSound(ModSoundEvents.SPIDER_WEB_IMPACT.get(), 1.0F, 1.0F);
	}

	protected void defineSynchedData() {

	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().then("web_projectile_idle", LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
