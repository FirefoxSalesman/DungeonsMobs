package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MooshroomMonstrosityProjectileEntity extends StraightMovingProjectileEntity implements GeoEntity {
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public MooshroomMonstrosityProjectileEntity(
			EntityType<? extends MooshroomMonstrosityProjectileEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	public MooshroomMonstrosityProjectileEntity(
			EntityType<? extends MooshroomMonstrosityProjectileEntity> pEntityType,
			double startX, double startY, double startZ, double pX, double pY, double pZ,
			Level pLevel) {
		super(pEntityType, startX, startY, startZ, pX, pY, pZ, pLevel);
	}

	public MooshroomMonstrosityProjectileEntity(
			EntityType<? extends MooshroomMonstrosityProjectileEntity> pEntityType,
			LivingEntity owner, double pX, double pY, double pZ, Level pLevel) {
		super(pEntityType, owner, pX, pY, pZ, pLevel);
	}

	public MooshroomMonstrosityProjectileEntity(
			Level pLevel, LivingEntity owner, double pX, double pY, double pZ) {
		super(ModEntities.MOOSHROOM_MONSTROSITY_PROJECTILE.get(), owner, pX, pY, pZ, pLevel);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, event -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		explode();
	}

	@Override
	public void tick() {
		if (level().getBlockStates(getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)
				|| isInWaterOrBubble())
			explode();
		else
			super.tick();
	}

	@Override
	protected void onHitBlock(BlockHitResult pResult) {
		super.onHitBlock(pResult);
		explode();
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public SoundEvent getImpactSound() {
		return null;
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
}
