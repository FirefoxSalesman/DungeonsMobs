package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
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

public class SimpleTrapEntity extends AbstractTrapEntity {

	private static final EntityDataAccessor<Integer> TRAP_TYPE = SynchedEntityData.defineId(SimpleTrapEntity.class,
			EntityDataSerializers.INT);

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public SimpleTrapEntity(EntityType<? extends SimpleTrapEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(TRAP_TYPE, 0);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
		this.setTrapType(p_70037_1_.getInt("TrapType"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
		p_213281_1_.putInt("TrapType", this.getTrapType());
	}

	public int getTrapType() {
		return Mth.clamp(this.entityData.get(TRAP_TYPE), 0, 1);
	}

	public void setTrapType(int attached) {
		this.entityData.set(TRAP_TYPE, attached);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getTrapType() == 0) {
			if (this.spawnAnimationTick > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("web_trap_spawn", LoopType.LOOP));
			} else if (this.decayAnimationTick > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_decay", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_idle", LoopType.LOOP));
			}
		} else if (this.getTrapType() == 1) {
			if (this.spawnAnimationTick > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_spawn", LoopType.LOOP));
			} else if (this.decayAnimationTick > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_decay", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("vine_trap_idle", LoopType.LOOP));
			}
		} else {

		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public int getSpawnAnimationLength() {
		return this.getTrapType() == 1 ? 8 : 7;
	}

	@Override
	public int getDecayAnimationLength() {
		return 25;
	}

	@Override
	public boolean canTrapEntity(LivingEntity entity) {
		if (this.getTrapType() == 0) {
			return super.canTrapEntity(entity) && entity.getMobType() != MobType.ARTHROPOD;
		} else if (this.getTrapType() == 1) {
			return super.canTrapEntity(entity) && !entity.getType().is(EntityTags.PLANT_MOBS);
		} else {
			return super.canTrapEntity(entity);
		}
	}
}
