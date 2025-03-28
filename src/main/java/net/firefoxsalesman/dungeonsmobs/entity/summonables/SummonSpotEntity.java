package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
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

public class SummonSpotEntity extends Entity implements GeoEntity {

	private static final EntityDataAccessor<Integer> SUMMON_TYPE = SynchedEntityData.defineId(
			SummonSpotEntity.class,
			EntityDataSerializers.INT);

	public int lifeTime = 0;

	public Entity summonedEntity = null;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int mobSpawnRotation;

	public SummonSpotEntity(Level worldIn) {
		super(ModEntities.SUMMON_SPOT.get(), worldIn);
	}

	public SummonSpotEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
		super(p_i48580_1_, p_i48580_2_);
	}

	@Override
	public void baseTick() {
		super.baseTick();

		this.lifeTime++;

		if (!level().isClientSide && this.lifeTime == this.getSummonTime() && this.summonedEntity != null) {
			summonedEntity.moveTo(this.blockPosition(), 0.0F, 0.0F);
			summonedEntity.setYBodyRot(this.random.nextInt(360));
			((ServerLevel) level()).addFreshEntityWithPassengers(summonedEntity);
		}

		if (!level().isClientSide && this.lifeTime >= this.getDespawnTime()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(SUMMON_TYPE, 0);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
		this.setSummonType(p_70037_1_.getInt("SummonType"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
		p_213281_1_.putInt("SummonType", this.getSummonType());
	}

	public int getSummonType() {
		return Mth.clamp(this.entityData.get(SUMMON_TYPE), 0, 3);
	}

	public void setSummonType(int attached) {
		this.entityData.set(SUMMON_TYPE, attached);
	}

	public int getDespawnTime() {
		if (this.getSummonType() == 0) {
			return 18;
		} else if (this.getSummonType() == 1) {
			return 18;
		} else if (this.getSummonType() == 2) {
			return 18;
		} else if (this.getSummonType() == 3) {
			return 18;
		} else {
			return 2;
		}
	}

	public int getSummonTime() {
		if (this.getSummonType() == 0) {
			return 10;
		} else if (this.getSummonType() == 1) {
			return 10;
		} else if (this.getSummonType() == 2) {
			return 10;
		} else if (this.getSummonType() == 3) {
			return 10;
		} else {
			return 1;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 1, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getSummonType() == 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("illusioner_summon_spot_summon", LoopType.LOOP));
		} else if (this.getSummonType() == 1) {
			event.getController().setAnimation(
					RawAnimation.begin().then("wildfire_summon_spot_summon", LoopType.LOOP));
		} else if (this.getSummonType() == 2) {
			event.getController().setAnimation(
					RawAnimation.begin().then("illusioner_summon_spot_summon", LoopType.LOOP));
		} else if (this.getSummonType() == 3) {
			event.getController().setAnimation(
					RawAnimation.begin().then("illusioner_summon_spot_summon", LoopType.LOOP));
		} else {

		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
