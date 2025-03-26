package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class KelpTrapEntity extends AbstractTrapEntity {

	private static final EntityDataAccessor<Boolean> PULLING = SynchedEntityData.defineId(KelpTrapEntity.class,
			EntityDataSerializers.BOOLEAN);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int ensnareAnimationTick;
	public int ensnareAnimationLength = 5;

	public int trappedMobTime;

	public int bubbleAudioInterval;

	public KelpTrapEntity(EntityType<? extends KelpTrapEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (ensnareAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("kelp_trap_ensnare", LoopType.LOOP));
		} else if (spawnAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("kelp_trap_spawn", LoopType.LOOP));
		} else if (decayAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("vine_trap_decay", LoopType.LOOP));
		} else {
			if (isPulling()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("kelp_trap_idle_pulling", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_idle", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public int getSpawnAnimationLength() {
		return 10;
	}

	@Override
	public int getDecayAnimationLength() {
		return 25;
	}

	@Override
	public boolean canTrapEntity(LivingEntity entity) {
		return super.canTrapEntity(entity) && !entity.getType().is(EntityTags.PLANT_MOBS);
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(PULLING, true);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
		setPulling(p_70037_1_.getBoolean("Pulling"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
		p_213281_1_.putBoolean("Pulling", isPulling());
	}

	public boolean isPulling() {
		return entityData.get(PULLING);
	}

	public void setPulling(boolean attached) {
		entityData.set(PULLING, attached);
	}

	@Override
	public void baseTick() {
		if (!level().isClientSide && lifeTime == 0) {
			setPulling(true);
		}

		if (bubbleAudioInterval > 0) {
			bubbleAudioInterval--;
		}

		if (!level().isClientSide && isPulling()) {
			List<Entity> list = level().getEntities(this,
					getBoundingBox().inflate(0, waterBlocksAbove(), 0), Entity::isAlive);
			for (int i = 0; i < waterBlocksAbove(); i++) {
				((ServerLevel) level()).sendParticles(ParticleTypes.CURRENT_DOWN,
						getRandomX(0.25), getY() + i + 0.8D,
						getRandomZ(0.25) - 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
			}
			if (!list.isEmpty()) {
				for (Entity entity : list) {
					if (entity.getY() > getY() && (!(entity instanceof LivingEntity)
							|| canTrapEntity(((LivingEntity) entity)))) {
						if (bubbleAudioInterval <= 0) {
							playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE,
									1.25F, 1.0F);
							bubbleAudioInterval = 40;
						}
						entity.push(0, -0.1, 0);
						entity.hurtMarked = true;
					}
				}
			}
		}

		super.baseTick();
	}

	@Override
	public void tickDownAnimTimers() {
		super.tickDownAnimTimers();
		if (ensnareAnimationTick > 0) {
			ensnareAnimationTick--;
		}
	}

	public double waterBlocksAbove() {
		double waterBlocksAbove = 0;
		for (int i = 0; i < 256; i++) {
			waterBlocksAbove = i;
			if (!level().getFluidState(blockPosition().above(i)).is(FluidTags.WATER)) {
				return waterBlocksAbove;
			}
		}
		return waterBlocksAbove;
	}

	@Override
	public void increaseLifeTime() {
		if (!level().isClientSide) {
			if (isTrappingMob) {
				trappedMobTime++;

				if (isPulling()) {
					if (ensnareAnimationTick <= 0) {
						setPulling(false);
						ensnareAnimationTick = ensnareAnimationLength;
						level().broadcastEntityEvent(this, (byte) 3);
					}
				}
			} else {
				lifeTime++;
			}

			if (trappedMobTime == timeToDecay()) {
				decayAnimationTick = getDecayAnimationLength();
				level().broadcastEntityEvent(this, (byte) 2);
			}

			if (isPulling()) {
				if (lifeTime == 200) {
					ensnareAnimationTick = ensnareAnimationLength;
					level().broadcastEntityEvent(this, (byte) 3);
				}

				if (lifeTime == 200 + ensnareAnimationLength) {
					decayAnimationTick = getDecayAnimationLength();
					level().broadcastEntityEvent(this, (byte) 2);
				}
			} else {
				if (trappedMobTime == 100) {
					decayAnimationTick = getDecayAnimationLength();
					level().broadcastEntityEvent(this, (byte) 2);
				}
			}

			if (decayAnimationTick == 2) {
				remove(RemovalReason.DISCARDED);
			}
		}
	}

	public boolean canTrap() {
		return true;
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 3) {
			ensnareAnimationTick = ensnareAnimationLength;
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}
}
