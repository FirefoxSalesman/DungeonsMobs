package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import com.google.common.collect.Lists;

import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class AreaDamageEntity extends Entity {

	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(AreaDamageEntity.class,
			EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SIZE_TO_REACH = SynchedEntityData.defineId(
			AreaDamageEntity.class,
			EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> GROW_SPEED = SynchedEntityData.defineId(AreaDamageEntity.class,
			EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> Y_SIZE = SynchedEntityData.defineId(AreaDamageEntity.class,
			EntityDataSerializers.FLOAT);

	private static final EntityDataAccessor<Integer> PARTICLE_TYPE = SynchedEntityData.defineId(
			AreaDamageEntity.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> EXTRA_TIME = SynchedEntityData.defineId(AreaDamageEntity.class,
			EntityDataSerializers.INT);

	public float damage;
	public DamageSource damageSource = damageSources().generic();
	public LivingEntity owner;

	public boolean constantDamage;
	public List<Entity> damagedEntities = Lists.newArrayList();

	public boolean friendlyFire;

	public double knockbackAmount;
	public double knockbackAmountY;

	public boolean disableShields;
	public int disableShieldTime;

	public int extraTimeTick;

	public List<AreaDamageEntity> connectedAreaDamages = Lists.newArrayList();

	public AreaDamageEntity(Level world) {
		super(ModEntities.AREA_DAMAGE.get(), world);
	}

	public AreaDamageEntity(EntityType<? extends AreaDamageEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	public EntityDimensions getDimensions(Pose p_213305_1_) {
		return EntityDimensions.scalable(getSize(), getYSize());
	}

	public void refreshDimensions() {
		double d0 = getX();
		double d1 = getY();
		double d2 = getZ();
		super.refreshDimensions();
		setPos(d0, d1, d2);
	}

	public boolean canEntityBeDamaged(Entity entity) {
		if (entity == null) {
			return false;
		} else if (owner != null && entity == owner) {
			return false;
		} else if (owner != null && owner.isAlliedTo(entity) && !friendlyFire) {
			return false;
		} else if (!damagedEntities.isEmpty() && !constantDamage
				&& damagedEntities.contains(entity)) {
			return false;
		} else if (!connectedAreaDamages.isEmpty()) {
			boolean canConnectedAreaDamagesHarm = true;
			for (AreaDamageEntity areaDamage : connectedAreaDamages) {
				if (!areaDamage.damagedEntities.isEmpty() && !areaDamage.constantDamage
						&& areaDamage.damagedEntities.contains(entity)) {
					canConnectedAreaDamagesHarm = false;
				}
			}
			return canConnectedAreaDamagesHarm;
		} else {
			return true;
		}
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 1) {
			for (int particleAmount = 0; particleAmount < 25; particleAmount++) {
				int i = Mth.floor(getX());
				int j = Mth.floor(getY() - (double) 0.2F);
				int k = Mth.floor(getZ());
				BlockPos pos = new BlockPos(i, j, k);
				BlockState blockstate = level().getBlockState(pos);
				if (!blockstate.isAir()) {
					level().addParticle(
							new BlockParticleOption(ParticleTypes.BLOCK, blockstate)
									.setPos(pos),
							getX(), getY() + 0.1D, getZ(), 0, 0, 0);
				}
			}

			for (int i = 0; i < 60; ++i) {
				Vec3 vector3d = position();
				double d0 = random.nextGaussian() * 15.0D;
				double d1 = random.nextFloat() * 1.75D;
				double d2 = random.nextGaussian() * 15.0D;
				level().addParticle(ModParticleTypes.DUST.get(), vector3d.x, vector3d.y, vector3d.z,
						d0, d1, d2);
			}
		} else if (p_70103_1_ == 2) {
			for (int particleAmount = 0; particleAmount < 25; particleAmount++) {
				int i = Mth.floor(getX());
				int j = Mth.floor(getY() - (double) 0.2F);
				int k = Mth.floor(getZ());
				BlockPos pos = new BlockPos(i, j, k);
				BlockState blockstate = level().getBlockState(pos);
				if (!blockstate.isAir()) {
					level().addParticle(
							new BlockParticleOption(ParticleTypes.BLOCK, blockstate)
									.setPos(pos),
							getX(), getY() + 0.1D, getZ(), 0, 0, 0);
				}
			}

			for (int i = 0; i < 50; ++i) {
				Vec3 vector3d = position();
				double d0 = random.nextGaussian() * 0.5D;
				double d1 = random.nextFloat() * 2.0D;
				double d2 = random.nextGaussian() * 0.5D;
				level().addParticle(ParticleTypes.BUBBLE, vector3d.x, vector3d.y, vector3d.z, d0, d1,
						d2);
			}
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	public static AreaDamageEntity spawnAreaDamage(Level level, Vec3 pos, LivingEntity owner, float damage,
			DamageSource damageSource, float size, float sizeToReach, float growSpeed, float ySize,
			int extraTime, boolean constantDamage, boolean friendlyFire, double knockbackAmount,
			double knockbackAmountY, boolean disableShields, int disableShieldTime, int particleVariant) {
		AreaDamageEntity areaDamage = ModEntities.AREA_DAMAGE.get().create(level);
		areaDamage.moveTo(pos.x, pos.y, pos.z);
		areaDamage.owner = owner;
		areaDamage.damage = damage;
		areaDamage.damageSource = damageSource;
		areaDamage.setSize(size);
		areaDamage.setSizeToReach(sizeToReach);
		areaDamage.setGrowSpeed(growSpeed);
		areaDamage.setYSize(ySize);
		areaDamage.constantDamage = constantDamage;
		areaDamage.friendlyFire = friendlyFire;
		areaDamage.knockbackAmount = knockbackAmount;
		areaDamage.knockbackAmountY = knockbackAmountY;
		areaDamage.disableShields = disableShields;
		areaDamage.disableShieldTime = disableShieldTime;
		areaDamage.setParticleType(particleVariant);
		areaDamage.setExtraTime(extraTime);
		return areaDamage;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		refreshDimensions();

		List<Entity> list = level().getEntities(this, getBoundingBox(), Entity::isAlive);
		if (!list.isEmpty()) {
			for (Entity entity : list) {
				if (!level().isClientSide && canEntityBeDamaged(entity)) {
					entity.hurt(damageSource, damage);
					if (distanceTo(entity) >= 0.5) {
						double d0 = entity.getX() - getX();
						double d1 = entity.getZ() - getZ();
						double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
						entity.push(d0 / d2 * knockbackAmount, knockbackAmountY,
								d1 / d2 * knockbackAmount);
					}
					if (entity instanceof LivingEntity && disableShields) {
						disableShield(((LivingEntity) entity), disableShieldTime);
					}
					damagedEntities.add(entity);
				}
			}
		}

		if (getParticleType() > 0) {
			level().broadcastEntityEvent(this, (byte) getParticleType());
			setParticleType(0);
		}

		if (getSize() < getSizeToReach()) {
			setSize(getSize() + getGrowSpeed());
		}

		if (getSize() >= getSizeToReach()) {
			extraTimeTick++;
		}

		if (!level().isClientSide && ((getExtraTime() > 0 && extraTimeTick >= getExtraTime())
				|| (getExtraTime() <= 0 && getSize() >= getSizeToReach()))) {
			remove(RemovalReason.DISCARDED);
		}
	}

	public void disableShield(LivingEntity livingEntity, int ticks) {
		if (livingEntity instanceof Player && livingEntity.isBlocking()) {
			((Player) livingEntity).getCooldowns()
					.addCooldown(livingEntity.getItemInHand(livingEntity.getUsedItemHand())
							.getItem(), ticks);
			livingEntity.stopUsingItem();
			livingEntity.level().broadcastEntityEvent(livingEntity, (byte) 30);
		}
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(SIZE, 0.0F);
		entityData.define(SIZE_TO_REACH, 0.0F);
		entityData.define(Y_SIZE, 0.0F);
		entityData.define(GROW_SPEED, 0.0F);
		entityData.define(PARTICLE_TYPE, 0);
		entityData.define(EXTRA_TIME, 0);
	}

	public float getSize() {
		return entityData.get(SIZE);
	}

	public void setSize(float attached) {
		entityData.set(SIZE, attached);
	}

	public float getSizeToReach() {
		return entityData.get(SIZE_TO_REACH);
	}

	public void setSizeToReach(float attached) {
		entityData.set(SIZE_TO_REACH, attached);
	}

	public float getYSize() {
		return entityData.get(Y_SIZE);
	}

	public void setYSize(float attached) {
		entityData.set(Y_SIZE, attached);
	}

	public float getGrowSpeed() {
		return entityData.get(GROW_SPEED);
	}

	public void setGrowSpeed(float attached) {
		entityData.set(GROW_SPEED, attached);
	}

	public int getParticleType() {
		return entityData.get(PARTICLE_TYPE);
	}

	public void setParticleType(int attached) {
		entityData.set(PARTICLE_TYPE, attached);
	}

	public int getExtraTime() {
		return entityData.get(EXTRA_TIME);
	}

	public void setExtraTime(int attached) {
		entityData.set(EXTRA_TIME, attached);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
		setSize(p_70037_1_.getFloat("Size"));
		setSizeToReach(p_70037_1_.getFloat("SizeToReach"));
		setYSize(p_70037_1_.getFloat("YSize"));
		setGrowSpeed(p_70037_1_.getFloat("GrowSpeed"));
		setParticleType(p_70037_1_.getInt("ParticleType"));
		setExtraTime(p_70037_1_.getInt("ExtraTime"));
		damage = p_70037_1_.getFloat("Damage");
		constantDamage = p_70037_1_.getBoolean("ConstantDamage");
		knockbackAmount = p_70037_1_.getDouble("KnockbackAmount");
		knockbackAmountY = p_70037_1_.getDouble("KnockbackAmountY");
		disableShieldTime = p_70037_1_.getInt("DisableShieldTime");
		disableShields = p_70037_1_.getBoolean("DisableShields");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
		p_213281_1_.putFloat("Size", getSize());
		p_213281_1_.putFloat("SizeToReach", getSizeToReach());
		p_213281_1_.putFloat("YSize", getYSize());
		p_213281_1_.putFloat("GrowSpeed", getGrowSpeed());
		p_213281_1_.putInt("ParticleType", getParticleType());
		p_213281_1_.putFloat("Damage", damage);
		p_213281_1_.putBoolean("ConstantDamage", constantDamage);
		p_213281_1_.putDouble("KnockbackAmount", knockbackAmount);
		p_213281_1_.putDouble("KnockbackAmountY", knockbackAmountY);
		p_213281_1_.putInt("DisableShieldTime", disableShieldTime);
		p_213281_1_.putBoolean("DisableShields", disableShields);
		p_213281_1_.putInt("ExtraTime", getExtraTime());
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
