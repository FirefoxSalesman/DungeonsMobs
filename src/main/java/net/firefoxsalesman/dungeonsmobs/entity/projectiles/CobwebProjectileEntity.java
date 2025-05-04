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

	public CobwebProjectileEntity(EntityType<? extends CobwebProjectileEntity> pEntity, Level pLevel) {
		super(pEntity, pLevel);
	}

	public CobwebProjectileEntity(Level pLevel, LivingEntity pEntity) {
		this(ModEntities.COBWEB_PROJECTILE.get(), pLevel);
		super.setOwner(pEntity);
		setPos(pEntity.getX() - (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
				* (double) Mth.sin(pEntity.yBodyRot * ((float) Math.PI / 180F)),
				pEntity.getEyeY() - (double) 0.1F,
				pEntity.getZ() + (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
						* (double) Mth.cos(pEntity.yBodyRot * ((float) Math.PI / 180F)));
	}

	@OnlyIn(Dist.CLIENT)
	public CobwebProjectileEntity(Level pLevel, double p_i47274_2_, double p_i47274_4_, double p_i47274_6_,
			double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
		this(ModEntities.COBWEB_PROJECTILE.get(), pLevel);
		setPos(p_i47274_2_, p_i47274_4_, p_i47274_6_);

		for (int i = 0; i < 7; ++i) {
			double d0 = 0.4D + 0.1D * (double) i;
			pLevel.addParticle(ParticleTypes.SPIT, p_i47274_2_, p_i47274_4_, p_i47274_6_,
					p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
		}

		setDeltaMovement(p_i47274_8_, p_i47274_10_, p_i47274_12_);
	}

	public void tick() {
		super.tick();

		if (delayedSpawnParticles) {
			delayedSpawnParticles = false;
			createSpawnParticles();
		}

		Vec3 vector3d = getDeltaMovement();
		HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this,
						raytraceresult)) {
			onHit(raytraceresult);
		}

		double d0 = getX() + vector3d.x;
		double d1 = getY() + vector3d.y;
		double d2 = getZ() + vector3d.z;
		updateRotation();
		float f = 0.99F;
		float f1 = 0.06F;
		if (level().getBlockStates(getBoundingBox())
				.noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
			remove(RemovalReason.DISCARDED);
		} else if (isInWaterOrBubble()) {
			remove(RemovalReason.DISCARDED);
		} else {
			setDeltaMovement(vector3d.scale(f));
			if (!isNoGravity()) {
				setDeltaMovement(getDeltaMovement().add(0.0D, -f1, 0.0D));
			}

			setPos(d0, d1, d2);
		}
	}

	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity shooter = getOwner();
		Entity hitEnt = hitResult.getEntity();
	
		if (hitEnt instanceof LivingEntity &&
			((LivingEntity) hitEnt).getMobType() == MobType.ARTHROPOD) {
			return;
		}

		if (shooter instanceof LivingEntity) {
			hitEnt.hurt(damageSources().mobProjectile(this, (LivingEntity) shooter), 1.0F);
		}
	
		if (!level().isClientSide) {
			// Capture the spawn coordinates
			double trapX = hitEnt.getX();
			double trapY = hitEnt.getY();
			double trapZ = hitEnt.getZ();
	
			// Spawn the trap at that location
			spawnTrap(trapX, trapY, trapZ);
	
			// Immediately teleport the target into the trap center
			if (hitEnt instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
				// For players, use the connection teleport to avoid rubber-banding
				serverPlayer.connection.teleport(trapX, trapY, trapZ,
						serverPlayer.getYRot(), serverPlayer.getXRot());
			} else {
				// For mobs and other entities
				hitEnt.teleportTo(trapX, trapY, trapZ);
			}
	
			// Zero out any velocity so they don’t drift out
			hitEnt.setDeltaMovement(0, 0, 0);
	
			remove(RemovalReason.DISCARDED);
		}
	}	

	public void createSpawnParticles() {
		if (!level().isClientSide) {
			level().broadcastEntityEvent(this, (byte) 1);
		} else {
			for (int i = 0; i < 5; i++) {
				level().addParticle(ParticleTypes.POOF, getX(), getY(), getZ(),
						0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 1) {
			for (int i = 0; i < 5; i++) {
				level().addParticle(ParticleTypes.POOF, getX(), getY(), getZ(),
						0.0D,
						0.0D, 0.0D);
			}
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	protected void onHitBlock(BlockHitResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		if (!level().isClientSide) {
			spawnTrap(getX(), getY(), getZ());

			remove(RemovalReason.DISCARDED);
		}

	}

	public void spawnTrap(double x, double y, double z) {
		SimpleTrapEntity trap = ModEntities.SIMPLE_TRAP.get().create(level());
		trap.moveTo(x, y, z);
		trap.owner = getOwner();

		level().addFreshEntity(trap);

		playSound(ModSoundEvents.SPIDER_WEB_IMPACT.get(), 1.0F, 1.0F);
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
