package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RedstoneMonstrosityProjectileEntity extends Projectile implements GeoEntity {
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public RedstoneMonstrosityProjectileEntity(
			EntityType<? extends RedstoneMonstrosityProjectileEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public RedstoneMonstrosityProjectileEntity(Level pLevel, LivingEntity pEntity) {
		this(ModEntities.REDSTONE_MONSTROSITY_PROJECTILE.get(), pLevel);
		super.setOwner(pEntity);
		setPos(pEntity.getX() - (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
				* (double) Mth.sin(pEntity.yBodyRot * ((float) Math.PI / 180F)),
				pEntity.getEyeY() - (double) 0.1F,
				pEntity.getZ() + (double) (pEntity.getBbWidth() + 1.0F) * 0.5D
						* (double) Mth.cos(pEntity.yBodyRot * ((float) Math.PI / 180F)));
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		explode();
	}

	@Override
	protected void onHitBlock(BlockHitResult pResult) {
		super.onHitBlock(pResult);
		explode();
	}

	/**
	 * Create a small explosion around the projectile, then discard it.
	 */
	private void explode() {
		if (!level().isClientSide) {
			level().explode(this, getX(), getY(0.0625D), getZ(), 1.0F,
					Level.ExplosionInteraction.NONE);
			remove(RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, event -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		super.tick();

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
			explode();
		} else if (isInWaterOrBubble()) {
			explode();
		} else {
			setDeltaMovement(vector3d.scale(f));
			if (!isNoGravity()) {
				setDeltaMovement(getDeltaMovement().add(0.0D, -f1, 0.0D));
			}

			setPos(d0, d1, d2);
		}
	}
}
