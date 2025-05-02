package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import com.google.common.collect.Lists;

import net.firefoxsalesman.dungeonsmobs.interfaces.ITrapsTarget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.List;

public abstract class AbstractTrapEntity extends Entity implements GeoEntity {

	public int spawnAnimationTick = getSpawnAnimationLength();
	public int decayAnimationTick;

	public int lifeTime;

	public Entity owner;

	public boolean isTrappingMob;

	public List<Entity> trappedEntities = Lists.newArrayList();
	public List<Vec3> trappedEntityPositions = Lists.newArrayList();

	public AbstractTrapEntity(EntityType<? extends AbstractTrapEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		tickDownAnimTimers();
		increaseLifeTime();
		performBasicTrapFunctions();
	}

	public void increaseLifeTime() {
		lifeTime++;

		if (!level().isClientSide) {
			if (lifeTime == timeToDecay()) {
				decayAnimationTick = getDecayAnimationLength();
				level().broadcastEntityEvent(this, (byte) 2);
			}

			if (decayAnimationTick == 2) {
				remove(RemovalReason.DISCARDED);
			}
		}
	}

	public void performBasicTrapFunctions() {
		if (!level().isClientSide) {
			boolean isTrapping = false;
			if (canTrap()) {
				List<Entity> list = level().getEntities(this, getBoundingBox(),
						Entity::isAlive);
				if (!list.isEmpty()) {
					for (Entity entity : list) {
						if (entity instanceof LivingEntity
								&& canTrapEntity(((LivingEntity) entity))) {
							if (!trappedEntities.contains(entity)) {
								trappedEntities.add(entity);
								trappedEntityPositions.add(entity.position());
							}

							if (owner != null && owner instanceof Mob
									&& ((Mob) owner).getTarget() != null
									&& ((Mob) owner).getTarget().isAlive()
									&& ((Mob) owner).getTarget() == entity
									&& owner instanceof ITrapsTarget) {
								((ITrapsTarget) owner).setTargetTrapped(true,
										true);
							}

							isTrapping = true;
						}
					}
				}
			}

			for (int i = 0; i < trappedEntities.size(); i++) {
				Entity entity = trappedEntities.get(i);
				Vec3 trappedPos = trappedEntityPositions.get(i);

				if (!entity.isAlive()) {
					trappedEntities.remove(i);
					trappedEntityPositions.remove(i);
					i--; // Adjust index due to removal
					continue;
				}

				Vec3 currentPos = entity.position();
				Vec3 toTarget = trappedPos.subtract(currentPos);
				double distance = toTarget.length();

				// Threshold: If they escape too far (e.g., Ender Pearl), stop trapping
				if (distance > 1.0) {
					trappedEntities.remove(i);
					trappedEntityPositions.remove(i);
					i--;
					continue;
				}

				entity.fallDistance = 0;

				if (distance > 0.1) {
					Vec3 pullVec = toTarget.normalize().scale(0.1);
					entity.setDeltaMovement(pullVec);
					entity.hurtMarked = true; // Ensure motion is synced to client
				} else {
					entity.setDeltaMovement(Vec3.ZERO);
				}
			}

			isTrappingMob = isTrapping;
		}
	}

	public float distanceBetweenVector3ds(Vec3 position, Vec3 positionToGetDistanceTo) {
		float f = (float) (position.x - positionToGetDistanceTo.x);
		float f1 = (float) (position.y - positionToGetDistanceTo.y);
		float f2 = (float) (position.z - positionToGetDistanceTo.z);
		return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
	}

	public void tickDownAnimTimers() {
		if (spawnAnimationTick > 0) {
			spawnAnimationTick--;
		}

		if (decayAnimationTick > 0) {
			decayAnimationTick--;
		}
	}

	public abstract int getSpawnAnimationLength();

	public abstract int getDecayAnimationLength();

	public int timeToDecay() {
		return 80;
	}

	public boolean canTrap() {
		return spawnAnimationTick <= 0;
	}

	public boolean canTrapEntity(LivingEntity entity) {
		return !entity.isSpectator();
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 1) {
			spawnAnimationTick = getSpawnAnimationLength();
		} else if (p_70103_1_ == 2) {
			decayAnimationTick = getDecayAnimationLength();
		} else {
			super.handleEntityEvent(p_70103_1_);
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

	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
