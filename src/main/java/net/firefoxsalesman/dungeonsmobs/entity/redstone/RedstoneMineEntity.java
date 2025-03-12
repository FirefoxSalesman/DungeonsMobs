package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.ConstructEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class RedstoneMineEntity extends Entity implements GeoEntity {
	public static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(ConstructEntity.class,
			EntityDataSerializers.INT);

	private LivingEntity caster;
	private UUID casterUuid;

	// nerf
	private final float explosionRadius = 1.0F;
	public static final int LIFE_TIME = 250;

	public RedstoneMineEntity(Level worldIn) {
		super(ModEntities.REDSTONE_MINE.get(), worldIn);
	}

	public RedstoneMineEntity(EntityType<? extends RedstoneMineEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	public RedstoneMineEntity(Level worldIn, double x, double y, double z, int delay, LivingEntity casterIn) {
		this(ModEntities.REDSTONE_MINE.get(), worldIn);
		this.setLifeTicks(delay);
		this.setCaster(casterIn);
		this.setPos(x, y, z);
	}

	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getLifeTicks() == 6) {
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_mine.deactive", LoopType.LOOP));
		}
		if (this.getLifeTicks() == LIFE_TIME) {
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_mine.activate", LoopType.LOOP));
		}
		if (this.getLifeTicks() < LIFE_TIME - 3 && this.getLifeTicks() > 4) {
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_mine.idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	public void setCaster(@Nullable LivingEntity livingEntity) {
		this.caster = livingEntity;
		this.casterUuid = livingEntity == null ? null : livingEntity.getUUID();
	}

	@Nullable
	public LivingEntity getCaster() {
		if (this.caster == null && this.casterUuid != null && level() instanceof ServerLevel) {
			Entity entity = ((ServerLevel) this.level()).getEntity(this.casterUuid);
			if (entity instanceof LivingEntity) {
				this.caster = (LivingEntity) entity;
			}
		}

		return this.caster;
	}

	protected float getRandomPitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 4) {
			for (int i = 0; i < 2; i++) {
				level().addParticle(ModParticleTypes.REDSTONE_SPARK.get(), this.getRandomX(1.1D),
						this.getRandomY(), this.getRandomZ(1.1D),
						-0.05D + this.random.nextDouble() * 0.05D,
						-0.05D + this.random.nextDouble() * 0.05D,
						-0.05D + this.random.nextDouble() * 0.05D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */

	public void tick() {
		super.tick();

		if (!level().isClientSide && this.random.nextInt(20) == 0) {
			this.playSound(ModSoundEvents.REDSTONE_GOLEM_SPARK.get(), 0.1F, this.getRandomPitch());
			level().broadcastEntityEvent(this, (byte) 4);
		}

		this.setLifeTicks(this.getLifeTicks() - 1);
		if (!level().isClientSide) {
			if (this.getLifeTicks() <= 0) {
				this.remove(RemovalReason.DISCARDED);
			} else if (this.getLifeTicks() < LIFE_TIME - 3 && this.getLifeTicks() > 6) {
				for (LivingEntity livingentity : level().getEntitiesOfClass(LivingEntity.class,
						this.getBoundingBox().inflate(0.3D, 0.3D, 0.3D))) {
					this.explode(livingentity);
				}
			}
		}

	}

	private void explode(LivingEntity livingentity) {
		LivingEntity Caster = this.getCaster();

		if (livingentity.isAlive() && !livingentity.isInvulnerable()) {
			if (Caster != null) {
				if (!Caster.isAlliedTo(livingentity) || livingentity != Caster) {
					level().explode(Caster, this.getX(), this.getY(0.0625D), this.getZ(),
							this.explosionRadius, Level.ExplosionInteraction.NONE);
					this.remove(RemovalReason.DISCARDED);
				}
			} else {
				level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(),
						this.explosionRadius, Level.ExplosionInteraction.NONE);
				this.remove(RemovalReason.DISCARDED);
			}

		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(LIFE_TICKS, 0);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		this.setLifeTicks(compound.getInt("LifeTicks"));
		if (compound.hasUUID("Owner")) {
			this.casterUuid = compound.getUUID("Owner");
		}

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("LifeTicks", this.getLifeTicks());
		if (this.casterUuid != null) {
			compound.putUUID("Owner", this.casterUuid);
		}
	}

	public int getLifeTicks() {
		return this.entityData.get(LIFE_TICKS);
	}

	public void setLifeTicks(int p_189794_1_) {
		this.entityData.set(LIFE_TICKS, p_189794_1_);
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
