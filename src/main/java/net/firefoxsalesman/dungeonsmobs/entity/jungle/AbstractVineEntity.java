package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.tags.EntityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.function.Predicate;

public abstract class AbstractVineEntity extends PathfinderMob implements Enemy, GeoEntity {

    public static final EntityDataAccessor<Integer> LENGTH = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> VANISHES = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> STAY_TIME = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> ALWAYS_OUT = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SHOULD_RETRACT = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Float> DETECTION_DISTANCE = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.FLOAT);

    public static final EntityDataAccessor<Boolean> OUT = SynchedEntityData.defineId(AbstractVineEntity.class, EntityDataSerializers.BOOLEAN);

    public int lifeTime;

    public int burstAnimationTick;
    public int retractAnimationTick;

    private final Predicate<Entity> SHOULD_BURST_FOR = (entity) -> {
        return shouldBurstFor(entity);
    };

    public VinePartEntity[] subEntities = new VinePartEntity[27];

    protected AbstractVineEntity(EntityType<? extends AbstractVineEntity> entityType, Level level) {
        super(entityType, level);
        int adjustedLength = 27;

        for (int i = 0; i < adjustedLength; i++) {
            VinePartEntity newPart = new VinePartEntity(this, 26 - i);
            subEntities[i] = newPart;
        }
    }

    protected void updateParts() {
        for (VinePartEntity part : subEntities) {
            part.refreshDimensions();
            movePart(part, 0, part.getYOffsetForSegment(), 0);
        }
    }

    protected void movePart(VinePartEntity part, double dX, double dY, double dZ) {
        Vec3 lastPos = new Vec3(part.getX(), part.getY(), part.getZ());

        part.setPos(getX() + dX, getY() + dY, getZ() + dZ);

        part.xo = lastPos.x;
        part.yo = lastPos.y;
        part.zo = lastPos.z;
        part.xOld = lastPos.x;
        part.yOld = lastPos.y;
        part.zOld = lastPos.z;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        updateParts();
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return subEntities;
    }

    protected int decreaseAirSupply(int p_70682_1_) {
        return p_70682_1_;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }


    public boolean isAlliedTo(Entity p_184191_1_) {
        if (super.isAlliedTo(p_184191_1_)) {
            return true;
        } else if (p_184191_1_ instanceof LivingEntity && p_184191_1_.getType().is(EntityTags.PLANT_MOBS)) {
            return getTeam() == null && p_184191_1_.getTeam() == null;
        } else {
            return false;
        }
    }

    protected void tickDeath() {
        ++deathTime;
        if (deathTime == getRetractAnimationLength()) {
            remove(RemovalReason.KILLED);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LENGTH, 0);
        entityData.define(VANISHES, false);
        entityData.define(STAY_TIME, 0);
        entityData.define(ALWAYS_OUT, false);
        entityData.define(SHOULD_RETRACT, false);
        entityData.define(DETECTION_DISTANCE, 0.0F);
        entityData.define(OUT, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setLengthInSegments(compound.getInt("Length"));
        setVanishes(compound.getBoolean("Vanishes"));
        setStayTime(compound.getInt("StayTime"));
        setAlwaysOut(compound.getBoolean("AlwaysOut"));
        setShouldRetract(compound.getBoolean("ShouldRetract"));
        setDetectionDistance(compound.getFloat("DetectionDistance"));
        setOut(compound.getBoolean("Out"));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Length", getLengthInSegments());
        compound.putBoolean("Vanishes", getVanishes());
        compound.putInt("StayTime", getStayTime());
        compound.putBoolean("AlwaysOut", getAlwaysOut());
        compound.putBoolean("ShouldRetract", getShouldRetract());
        compound.putFloat("DetectionDistance", getDetectionDistance());
        compound.putBoolean("Out", isOut());
    }

    public int getLengthInSegments() {
        return Mth.clamp(entityData.get(LENGTH), 1, 26);
    }

    public float getLengthInPixels() {
        return getLengthInSegments() * 22;
    }

    public float getLengthInBlocks() {
        return getLengthInPixels() / 16;
    }

    public void setLengthInSegments(int setTo) {
        entityData.set(LENGTH, setTo);
    }

    public void setLengthInPixels(int setTo) {
        setLengthInSegments(setTo / 22);
    }

    public void setLengthInBlocks(float setTo) {
        setLengthInPixels(Math.round(setTo * 16));
    }

    public boolean getVanishes() {
        return entityData.get(VANISHES);
    }

    public void setVanishes(boolean setTo) {
        entityData.set(VANISHES, setTo);
    }

    public int getStayTime() {
        return entityData.get(STAY_TIME);
    }

    public void setStayTime(int setTo) {
        entityData.set(STAY_TIME, setTo);
    }

    public boolean getAlwaysOut() {
        return entityData.get(ALWAYS_OUT);
    }

    public void setAlwaysOut(boolean setTo) {
        entityData.set(ALWAYS_OUT, setTo);
    }

    public boolean getShouldRetract() {
        return entityData.get(SHOULD_RETRACT);
    }

    public void setShouldRetract(boolean setTo) {
        entityData.set(SHOULD_RETRACT, setTo);
    }

    public float getDetectionDistance() {
        return entityData.get(DETECTION_DISTANCE);
    }

    public void setDetectionDistance(float setTo) {
        entityData.set(DETECTION_DISTANCE, setTo);
    }

    public boolean getOut() {
        return entityData.get(OUT);
    }

    public void setOut(boolean setTo) {
        entityData.set(OUT, setTo);
    }

    public abstract int getBurstAnimationLength();

    public abstract int getRetractAnimationLength();

    public float getExtraHitboxY() {
        return 0;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundeventVocal = getAmbientSound();
        SoundEvent soundeventFoley = getAmbientSoundFoley();
        playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(), getSoundVolume(), getVoicePitch());
    }

    @Override
    protected void playHurtSound(DamageSource pDamageSource) {
        ambientSoundTime = -getAmbientSoundInterval();
        SoundEvent soundeventVocal = getHurtSound(pDamageSource);
        SoundEvent soundeventFoley = getHurtSoundFoley(pDamageSource);
        playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(), getSoundVolume(), getVoicePitch());
    }

    public void playBurstSound() {
        SoundEvent soundeventVocal = getBurstSound();
        SoundEvent soundeventFoley = getBurstSoundFoley();
        playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(), getSoundVolume(), getVoicePitch());
    }

    public void playRetractSound() {
        SoundEvent soundeventVocal = getRetractSound();
        SoundEvent soundeventFoley = getRetractSoundFoley();
        playSound(soundeventVocal, soundeventFoley, getSoundVolume(), getVoicePitch(), getSoundVolume(), getVoicePitch());
    }

    public void playSound(SoundEvent vocalSound, SoundEvent foleySound, float vocalVolume, float vocalPitch, float foleyVolume, float foleyPitch) {
        if (!isSilent()) {
            if (vocalSound != null) {
                level().playSound(null, getX(), getY(), getZ(), vocalSound, getSoundSource(), vocalVolume, vocalPitch);
            }
            if (foleySound != null) {
                level().playSound(null, getX(), getY(), getZ(), foleySound, getSoundSource(), foleyVolume, foleyPitch);
            }
        }
    }

    protected abstract SoundEvent getAmbientSoundFoley();

    protected abstract SoundEvent getHurtSoundFoley(DamageSource p_184601_1_);

    public abstract SoundEvent getBurstSound();

    public abstract SoundEvent getRetractSound();

    public abstract SoundEvent getBurstSoundFoley();

    public abstract SoundEvent getRetractSoundFoley();

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() + ((float) (getLengthInSegments() * 0.5));
    }

    public abstract int getAnimationTransitionTime();

    public boolean isOut() {
        return getOut();
    }

    public boolean canBurst() {
        return !isOut() && retractAnimationTick <= 0 && burstAnimationTick <= 0;
    }

    public boolean canRetract() {
        return isOut() && !getAlwaysOut() && retractAnimationTick <= 0 && burstAnimationTick <= 0;
    }

    public boolean shouldBurstFor(Entity entity) {
        return entity instanceof LivingEntity && !entity.getType().is(EntityTags.PLANT_MOBS) && entity.isAlive() && !entity.isSpectator() && !(entity instanceof Player && ((Player) entity).isCreative());
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPushable() {
        return false;
    }

    public boolean hurt(DamageSource pDamageSource, float p_70097_2_) {
        if (pDamageSource == damageSources().fellOutOfWorld() || (isOut() && pDamageSource != damageSources().inWall())) {
            return super.hurt(pDamageSource, p_70097_2_);
        } else {
            return false;
        }
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {
    }

    @Override
    public void push(Entity p_70108_1_) {

    }

    @Override
    public void push(double p_70024_1_, double p_70024_3_, double p_70024_5_) {

    }

    @Override
    protected void pushEntities() {

    }

    @Override
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 4) {
            burstAnimationTick = getBurstAnimationLength();
        } else if (p_70103_1_ == 11) {
            retractAnimationTick = getRetractAnimationLength();
        } else {
            super.handleEntityEvent(p_70103_1_);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose p_213305_1_) {
        return !isOut() ? EntityDimensions.scalable(1.0F, 0.1F) : EntityDimensions.scalable(1.5F, getLengthInBlocks() + getExtraHitboxY());
    }

    public void refreshDimensions() {
        double d0 = getX();
        double d1 = getY();
        double d2 = getZ();
        super.refreshDimensions();
        setPos(d0, d1, d2);
    }

    public abstract void spawnAreaDamage();

    public abstract void setDefaultFeatures();

    public abstract boolean isKelp();

    public abstract boolean shouldDieInWrongHabitat();

    public abstract int wrongHabitatDieChance();

    public boolean isInWrongHabitat() {
        return ((isKelp() && !isInWaterOrBubble()) || (!isKelp() && isInWaterOrBubble()));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
                                        MobSpawnType p_213386_3_, SpawnGroupData p_213386_4_, CompoundTag p_213386_5_) {
        setDefaultFeatures();
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        refreshDimensions();

        tickDownAnimTimers();

        lifeTime++;

        int nearbyEntities = level().getEntities(this, getBoundingBox().inflate(getDetectionDistance()), SHOULD_BURST_FOR).size();

        if (!level().isClientSide) {

            if (isInWrongHabitat() && random.nextInt(wrongHabitatDieChance()) == 0 && shouldDieInWrongHabitat() && isOut()) {
                kill();
            }

            if (burstAnimationTick == 1) {
                setOut(true);
            } else if (retractAnimationTick == 1) {
                setOut(false);
            }

            if (getAlwaysOut() && canBurst()) {
                burst();
            }

            if (nearbyEntities > 0) {
                if (canBurst()) {
                    burst();
                }
            } else {
                if (canRetract() && getShouldRetract()) {
                    retract();
                }
            }

            if (getVanishes() && lifeTime > getStayTime()) {
                if (retractAnimationTick <= 0) {
                    retract();
                }
                if (retractAnimationTick == 1) {
                    remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    public void burst() {
        spawnAreaDamage();
        playBurstSound();
        burstAnimationTick = getBurstAnimationLength();
        level().broadcastEntityEvent(this, (byte) 4);
    }

    public void retract() {
        spawnAreaDamage();
        playRetractSound();
        retractAnimationTick = getRetractAnimationLength();
        level().broadcastEntityEvent(this, (byte) 11);
    }

    public void tickDownAnimTimers() {
        if (burstAnimationTick > 0) {
            burstAnimationTick--;
        }

        if (retractAnimationTick > 0) {
            retractAnimationTick--;
        }
    }

    @Override
    public void setId(int p_145769_1_) {
        super.setId(p_145769_1_);
        for (int i = 0; i < subEntities.length; ++i) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            subEntities[i].setId(p_145769_1_ + i + 1);
    }

}
