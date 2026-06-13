package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RedstoneMonstrosityProjectileEntity extends Projectile implements GeoEntity {
	public RedstoneMonstrosityProjectileEntity(
			EntityType<? extends RedstoneMonstrosityProjectileEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

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
}
