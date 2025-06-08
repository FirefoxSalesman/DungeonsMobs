package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

import java.util.List;

public class TridentStormEntity extends Entity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int lifeTime;
	public Entity owner;

	public TridentStormEntity(EntityType<? extends TridentStormEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 1, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().then("trident_storm_strike", LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		this.refreshDimensions();

		List<Entity> list = level().getEntities(this, this.getBoundingBox(), Entity::isAlive);
		if (!list.isEmpty() && !level().isClientSide) {
			for (Entity entity : list) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (this.lifeTime >= 80 && this.lifeTime <= 90) {
						if (this.owner != null) {
							if (livingEntity != this.owner) {
								livingEntity.hurt(
										damageSources().mobProjectile(this,
												(LivingEntity) owner),
										20);
							}
						} else {
							livingEntity.hurt(damageSources().fallingStalactite(this), 20);
						}
					}
				}

			}
		}

		this.lifeTime++;

		if (this.lifeTime == 80) {
			this.playSound(ModSoundEvents.DROWNED_NECROMANCER_TRIDENT_STORM_HIT.get(), 3.0F, 1.0F);
		}

		if (this.lifeTime >= 500 && !level().isClientSide) {
			this.remove(RemovalReason.DISCARDED);
		}
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
