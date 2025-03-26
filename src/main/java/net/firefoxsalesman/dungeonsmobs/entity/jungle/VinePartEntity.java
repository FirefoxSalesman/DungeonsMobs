package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.entity.PartEntity;

public class VinePartEntity extends PartEntity<AbstractVineEntity> {
	public final AbstractVineEntity parentMob;

	public int segmentNumber;

	public VinePartEntity(AbstractVineEntity pEntity, int segmentNumber) {
		super(pEntity);
		parentMob = pEntity;
		this.segmentNumber = segmentNumber;
		refreshDimensions();
	}

	protected void defineSynchedData() {
	}

	protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
	}

	protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
	}

	public boolean isPickable() {
		return parentMob.isOut();
	}

	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.getEntity() != null && damageSource.getEntity().getType().is(EntityTags.PLANT_MOBS)) {
			return false;
		} else {
			return !isInvulnerableTo(damageSource) && parentMob.hurt(damageSource, amount);
		}
	}

	public void refreshDimensions() {
		double d0 = getX();
		double d1 = getY();
		double d2 = getZ();
		super.refreshDimensions();
		setPos(d0, d1, d2);
	}

	public boolean isPoisonQuill() {
		return parentMob instanceof PoisonQuillVineEntity;
	}

	@Override
	public boolean canBeCollidedWith() {
		return parentMob.isOut();
	}

	public EntityDimensions getSizeForSegment() {
		EntityDimensions size = EntityDimensions.scalable(0, 0);

		if (segmentNumber < 27 - parentMob.getLengthInSegments()) {
			return size;
		}

		if (segmentNumber == 26) {
			size = EntityDimensions.scalable(0.25F, isPoisonQuill() ? 1.625F : 1.375F);
		} else if (segmentNumber == 25) {
			size = EntityDimensions.scalable(0.5F, isPoisonQuill() ? 2.0F : 1.375F);
		} else if (segmentNumber >= 23 && segmentNumber <= 24) {
			size = EntityDimensions.scalable(0.75F,
					isPoisonQuill() && segmentNumber == 24 ? 2.0F : 1.375F);
		} else if (segmentNumber >= 20 && segmentNumber <= 22) {
			size = EntityDimensions.scalable(1.0F, 1.375F);
		} else if (segmentNumber >= 16 && segmentNumber <= 19) {
			size = EntityDimensions.scalable(1.25F, 1.375F);
		} else if (segmentNumber >= 11 && segmentNumber <= 15) {
			size = EntityDimensions.scalable(1.5F, 1.375F);
		} else if (segmentNumber >= 5 && segmentNumber <= 10) {
			size = EntityDimensions.scalable(1.75F, 1.375F);
		} else if (segmentNumber >= 1 && segmentNumber <= 4) {
			size = EntityDimensions.scalable(2.0F, 1.375F);
		}
		return size;
	}

	public float getYOffsetForSegment() {
		float extraHeight = isPoisonQuill() && segmentNumber > 24
				? (0.625F * (-24 + segmentNumber))
				: 0.0F;
		return ((1.375F * (segmentNumber - 1)) - (1.375F * 26) + parentMob.getLengthInBlocks())
				+ extraHeight;
	}

	public EntityDimensions getDimensions(Pose pose) {
		return getSizeForSegment();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		throw new UnsupportedOperationException();
	}
}
