package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.lib.client.AnimationTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
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

import java.util.function.Predicate;

public class IceCloudEntity extends Entity implements GeoEntity {

	private static final Predicate<Entity> ALIVE = (p_213685_0_) -> {
		return p_213685_0_.isAlive();
	};

	public LivingEntity target;
	public Entity owner;
	private final AnimationTimer formTimer = new AnimationTimer(40);
	private final AnimationTimer fallTimer = new AnimationTimer(17);
	private final AnimationTimer landTimer = new AnimationTimer(22);
	public int lifeTime;
	public boolean falling;
	public boolean hasFormed;
	public int soundLoopTick;
	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public IceCloudEntity(Level world) {
		super(ModEntities.ICE_CLOUD.get(), world);
		this.blocksBuilding = true;
	}

	public IceCloudEntity(EntityType<? extends IceCloudEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
		this.blocksBuilding = true;
	}

	public static void spawn(Entity summoningEntity, LivingEntity target) {
		IceCloudEntity iceChunk = ModEntities.ICE_CLOUD.get().create(summoningEntity.level());
		iceChunk.moveTo(target.getX(), target.getY() + target.getBbHeight() + 3, target.getZ());
		iceChunk.target = target;
		iceChunk.owner = summoningEntity;
		iceChunk.playSound(ModSoundEvents.ICE_CHUNK_SUMMONED.get(), 1.0F, iceChunk.getRandomPitch());
		summoningEntity.level().addFreshEntity(iceChunk);
	}

	public boolean isAttackable() {
		return false;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public boolean isPickable() {
		return !this.isRemoved();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean displayFireAnimation() {
		return false;
	}

	protected float getRandomPitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
	}

	public void handleEntityEvent(byte event) {
		if (event == 1)
			formTimer.reset();
		else if (event == 2)
			fallTimer.reset();
		else if (event == 3)
			landTimer.reset();
		else if (event == 4)
			this.falling = true;
		else if (event == 5)
			for (int i = 0; i < 50; ++i) {
				double d0 = this.random.nextGaussian() * 0.3D;
				double d1 = this.random.nextGaussian() * 0.2D;
				double d2 = this.random.nextGaussian() * 0.3D;
				this.level().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), d0,
						d1, d2);
			}
		else if (event == 6)
			this.hasFormed = true;
		else if (event == 7)
			this.target = null;
		else
			super.handleEntityEvent(event);
	}

	public void moveToTarget() {
		if (this.target != null && !this.target.isDeadOrDying() && this.distanceToIgnoringY(target) > 1) {
			this.setDeltaMovement(0.0D, 0.0D, 0.0D);

			double x = this.target.getX() - this.getX();
			double y = this.target.getY() + this.target.getBbHeight() + 3 - this.getY();
			double z = this.target.getZ() - this.getZ();
			double d = Math.sqrt(x * x + y * y + z * z);
			this.setDeltaMovement(this.getDeltaMovement()
					.add(x / d * 0.20000000298023224D, y / d * 0.20000000298023224D,
							z / d * 0.20000000298023224D)
					.scale(1.0D));
			this.move(MoverType.SELF, this.getDeltaMovement());
		}
	}

	public float distanceToIgnoringY(Entity p_70032_1_) {
		float f = (float) (this.getX() - p_70032_1_.getX());
		float f2 = (float) (this.getZ() - p_70032_1_.getZ());
		return Mth.sqrt(f * f + f2 * f2);
	}

	public void baseTick() {
		super.baseTick();

		this.xOld = this.getX();
		this.yOld = this.getY();
		this.zOld = this.getZ();

		if (this.level().isClientSide && landTimer.animationsUseable()) {
			if (this.hasFormed) {
				this.level().addParticle(ModParticleTypes.SNOWFLAKE.get(), this.getRandomX(0.5D),
						this.getRandomY() - 0.25D, this.getRandomZ(0.5D),
						(this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
						(this.random.nextDouble() - 0.5D) * 2.0D);
			} else {
				for (int i = 0; i < 2; i++) {
					this.level().addParticle(ModParticleTypes.SNOWFLAKE.get(),
							this.getRandomX(0.5D), this.getRandomY() - 0.25D,
							this.getRandomZ(0.5D),
							-(this.random.nextDouble() - 0.5D) * 2.0D,
							this.random.nextDouble(),
							-(this.random.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}

		HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		boolean flag = false;
		if (raytraceresult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockpos = ((BlockHitResult) raytraceresult).getBlockPos();
			BlockState blockstate = this.level().getBlockState(blockpos);
			if (blockstate.is(Blocks.NETHER_PORTAL)) {
				this.handleInsidePortal(blockpos);
				flag = true;
			} else if (blockstate.is(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.level().getBlockEntity(blockpos);
				if (blockEntity instanceof TheEndGatewayBlockEntity
						&& TheEndGatewayBlockEntity.canEntityTeleport(this)) {
					TheEndGatewayBlockEntity.teleportEntity(this.level(), blockpos, blockstate,
							this, (TheEndGatewayBlockEntity) blockEntity);
				}

				flag = true;
			}
		}

		if (raytraceresult.getType() != HitResult.Type.MISS && !flag) {
			this.onHit(raytraceresult);
		}

		this.checkInsideBlocks();

		this.tickDownAnimTimers();

		if (this.hasFormed) {
			this.soundLoopTick++;
			this.lifeTime++;
		}

		if (this.soundLoopTick % 80 == 0 && this.hasFormed && !this.falling && fallTimer.animationsUseable()) {
			this.playSound(ModSoundEvents.ICE_CHUNK_IDLE_LOOP.get(), 0.5F, 1.0F);
		}

		if (!this.level().isClientSide) {

			if (this.target != null && !this.canHitEntity(this.target)) {
				this.target = null;
				this.level().broadcastEntityEvent(this, (byte) 7);
			}

			if (!this.hasFormed && formTimer.animationsUseable()) {
				formTimer.reset();
				level().broadcastEntityEvent(this, (byte) 1);
			}

			if (formTimer.tickEquals(1)) {
				playSound(ModSoundEvents.ICE_CHUNK_IDLE_LOOP.get(), 0.5F, 1.0F);
				hasFormed = true;
				level().broadcastEntityEvent(this, (byte) 6);
			}

			if ((this.target != null && this.lifeTime > 100 && fallTimer.animationsUseable()
					&& this.falling == false) || this.target == null
					|| this.target.isDeadOrDying()) {
				this.playSound(ModSoundEvents.ICE_CHUNK_FALL.get(), 1.0F, this.getRandomPitch());
				fallTimer.reset();
				this.level().broadcastEntityEvent(this, (byte) 2);
			}

			if (fallTimer.tickEquals(1)) {
				this.falling = true;
				this.level().broadcastEntityEvent(this, (byte) 4);
			}

			if (landTimer.tickEquals(1) || this.lifeTime > 150) {
				this.remove(RemovalReason.DISCARDED);
			}
		}

		if (this.hasFormed && landTimer.animationsUseable() && !this.falling && fallTimer.animationsUseable()) {
			this.moveToTarget();
		}

		if (this.falling && landTimer.animationsUseable()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0, -1.25, 0));
			this.move(MoverType.SELF, this.getDeltaMovement());
		}
	}

	protected boolean canHitEntity(Entity p_230298_1_) {
		return !p_230298_1_.isSpectator() && p_230298_1_.isAlive() && p_230298_1_.isPickable();
	}

	protected void onHit(HitResult p_70227_1_) {
		HitResult.Type raytraceresult$type = p_70227_1_.getType();
		if (!this.level().isClientSide) {
			if (landTimer.animationsUseable()) {
				landTimer.reset();
				this.level().broadcastEntityEvent(this, (byte) 3);
				this.land();
				this.moveTo(this.getX(), this.getY() - 1, this.getZ());
				this.playSound(ModSoundEvents.ICE_CHUNK_LAND.get(), 1.5F, this.getRandomPitch());
			}
		}
		if (raytraceresult$type == HitResult.Type.ENTITY) {
			this.onHitEntity((EntityHitResult) p_70227_1_);
		} else if (raytraceresult$type == HitResult.Type.BLOCK) {
			this.onHitBlock((BlockHitResult) p_70227_1_);
		}

	}

	protected void onHitEntity(EntityHitResult p_213868_1_) {
	}

	protected void onHitBlock(BlockHitResult p_230299_1_) {
	}

	private void land() {
		if (this.isAlive()) {
			for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class,
					this.getBoundingBox().inflate(2.5D), ALIVE)) {
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
				entity.hurt(damageSources().mobProjectile(this, (LivingEntity) this.owner), 15.0F);
				this.strongKnockback(entity);
			}

			this.level().broadcastEntityEvent(this, (byte) 5);
		}

	}

	private void strongKnockback(Entity p_213688_1_) {
		p_213688_1_.push(this.random.nextGaussian() * 1.0D, 0.1D, this.random.nextGaussian() * 1.0D);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public void tickDownAnimTimers() {
		formTimer.dec();
		fallTimer.dec();
		landTimer.dec();
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (formTimer.isRunning()) {
			event.getController().setAnimation(RawAnimation.begin().then("ice_chunk_form", LoopType.LOOP));
		} else if (landTimer.isRunning()) {
			event.getController().setAnimation(RawAnimation.begin().then("ice_chunk_land", LoopType.LOOP));
		} else if (fallTimer.isRunning()) {
			event.getController().setAnimation(RawAnimation.begin().then("ice_chunk_fall", LoopType.LOOP));
		} else {
			if (this.hasFormed) {
				event.getController().setAnimation(
						RawAnimation.begin().then("ice_chunk_idle", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("ice_chunk_idle_unformed", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {

	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
