package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoBlockEntity;
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
import java.util.function.Predicate;

public class WraithFireEntity extends Entity implements GeoBlockEntity {
	private static final Predicate<Entity> ALIVE = Entity::isAlive;

	public int lifeTime;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public Entity owner;

	public int textureChange = 0;

	public WraithFireEntity(EntityType<? extends WraithFireEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public void baseTick() {
		super.baseTick();

		this.lifeTime++;

		textureChange++;

		this.setYBodyRot(0);

		if (this.lifeTime == 1) {
			this.playSound(ModSoundEvents.WRAITH_FIRE.get(), 1.25F, this.random.nextFloat() * 0.7F + 0.3F);
		}

		if (this.random.nextInt(24) == 0 && !this.isSilent()) {
			level().playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D,
					SoundEvents.FIRE_AMBIENT, this.getSoundSource(), 1.0F + this.random.nextFloat(),
					this.random.nextFloat() * 0.7F + 0.3F, false);
		}

		double particleOffsetAmount = 1.25;

		if (this.isBurning()) {
			for (double x = -particleOffsetAmount; x < particleOffsetAmount * 2; x = x
					+ particleOffsetAmount) {
				for (double z = -particleOffsetAmount; z < particleOffsetAmount * 2; z = z
						+ particleOffsetAmount) {
					if (this.random.nextInt(10) == 0) {
						level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + x,
								this.getY(), this.getZ() + z,
								this.random.nextGaussian() * 0.01, 0.1,
								this.random.nextGaussian() * 0.01);
					}

					if (this.random.nextInt(5) == 0) {
						level().addParticle(ParticleTypes.SMOKE, this.getX() + x,
								this.getY(), this.getZ() + z,
								this.random.nextGaussian() * 0.01, 0.15,
								this.random.nextGaussian() * 0.01);
					}
				}
			}
		}

		if (!level().isClientSide) {

			if (this.isOnFire()) {
				this.remove(RemovalReason.DISCARDED);
				this.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
			}

			if (this.lifeTime >= 82) {
				this.remove(RemovalReason.DISCARDED);
			}

			if (this.isBurning()) {
				List<Entity> list = level().getEntities(this, this.getBoundingBox(), ALIVE);
				if (!list.isEmpty()) {
					for (Entity entity : list) {
						if (entity instanceof LivingEntity && this.canHarmEntity(entity)) {
							entity.hurt(damageSources().freeze(), 4.0F);
						}
					}
				}
			}
		}
	}

	public boolean isBurning() {
		return this.lifeTime >= 20 && this.lifeTime <= 70;
	}

	@Override
	public void registerControllers(ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(
				RawAnimation.begin().then("wraith_fire_burn", LoopType.LOOP));
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

	public boolean canHarmEntity(Entity target) {
		boolean canFreeze = target.canFreeze();
		return this.owner != null && this.owner instanceof Mob mob ? mob.getTarget() == target & canFreeze
				: canFreeze;
	}
}
