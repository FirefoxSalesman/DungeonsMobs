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

		lifeTime++;

		textureChange++;

		setYBodyRot(0);

		if (lifeTime == 1) {
			playSound(ModSoundEvents.WRAITH_FIRE.get(), 1.25F, random.nextFloat() * 0.7F + 0.3F);
		}

		if (random.nextInt(24) == 0 && !isSilent()) {
			level().playLocalSound(getX() + 0.5D, getY() + 0.5D, getZ() + 0.5D,
					SoundEvents.FIRE_AMBIENT, getSoundSource(), 1.0F + random.nextFloat(),
					random.nextFloat() * 0.7F + 0.3F, false);
		}

		double particleOffsetAmount = 1.25;

		if (isBurning()) {
			for (double x = -particleOffsetAmount; x < particleOffsetAmount * 2; x = x
					+ particleOffsetAmount) {
				for (double z = -particleOffsetAmount; z < particleOffsetAmount * 2; z = z
						+ particleOffsetAmount) {
					if (random.nextInt(10) == 0) {
						level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, getX() + x,
								getY(), getZ() + z,
								random.nextGaussian() * 0.01, 0.1,
								random.nextGaussian() * 0.01);
					}

					if (random.nextInt(5) == 0) {
						level().addParticle(ParticleTypes.SMOKE, getX() + x,
								getY(), getZ() + z,
								random.nextGaussian() * 0.01, 0.15,
								random.nextGaussian() * 0.01);
					}
				}
			}
		}

		if (!level().isClientSide) {

			if (isOnFire()) {
				remove(RemovalReason.DISCARDED);
				playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
			}

			if (lifeTime >= 82) {
				remove(RemovalReason.DISCARDED);
			}

			if (isBurning()) {
				List<Entity> list = level().getEntities(this, getBoundingBox(), ALIVE);
				if (!list.isEmpty()) {
					for (Entity entity : list) {
						if (entity instanceof LivingEntity && canHarmEntity(entity)) {
							entity.hurt(damageSources().freeze(), 4.0F);
						}
					}
				}
			}
		}
	}

	public boolean isBurning() {
		return lifeTime >= 20 && lifeTime <= 70;
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
		return owner != null && owner instanceof Mob mob ? mob.getTarget() == target & canFreeze
				: canFreeze;
	}
}
