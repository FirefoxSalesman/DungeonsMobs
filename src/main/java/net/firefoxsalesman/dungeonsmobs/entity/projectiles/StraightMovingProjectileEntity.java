package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;
import java.util.function.Predicate;

public abstract class StraightMovingProjectileEntity extends Projectile {
    public double xPower;
    public double yPower;
    public double zPower;

    public boolean stuckInBlock;
    private final Predicate<Entity> CAN_HIT = (entity) -> {
        return canHitEntity(entity);
    };
    public int lifeTime;
    public float lockedXRot;
    public float lockedYRot;

    protected StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> pEntityType, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        moveTo(p_i50174_2_, p_i50174_4_, p_i50174_6_, getYRot(), getXRot());
        reapplyPosition();
        double d0 = Mth.sqrt((float) (pX * pX + pY * pY + pZ * pZ));
        if (d0 != 0.0D) {
            xPower = pX / d0 * 0.1D;
            yPower = pY / d0 * 0.1D;
            zPower = pZ / d0 * 0.1D;
        }
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize();
        double d1 = vec3.horizontalDistance();
        setYRot((float)(Mth.atan2(vec3.x, vec3.z) * Mth.DEG_TO_RAD));
        setXRot((float)(Mth.atan2(vec3.y, d1) * Mth.DEG_TO_RAD));
        yRotO = getYRot();
        xRotO = getXRot();

    }

    public StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> pEntityType, LivingEntity owner, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level pLevel) {
        this(pEntityType, owner.getX(), owner.getY(), owner.getZ(), p_i50175_3_, p_i50175_5_, p_i50175_7_, pLevel);
        setOwner(owner);
        setRot(owner.getYRot(), owner.getXRot());
    }

    protected void defineSynchedData() {
    }

    public void setPower(double powerX, double powerY, double powerZ) {
        double d0 = Mth.sqrt((float) (powerX * powerX + powerY * powerY + powerZ * powerZ));
        if (d0 != 0.0D) {
            xPower = powerX / d0 * 0.1D;
            yPower = powerY / d0 * 0.1D;
            zPower = powerZ / d0 * 0.1D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        double d0 = getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return p_70112_1_ < d0 * d0;
    }

    public void tryToDealDamage() {
        List<Entity> list = level().getEntities(this, getBoundingBox(), CAN_HIT);
        if (!list.isEmpty() && !level().isClientSide) {
            for (Entity entity : list) {
                onHitEntity(entity);
            }
        }
    }

    public void rotateToMatchMovement() {
        updateRotation();
    }

    public boolean shouldUpdateRotation() {
        return true;
    }

    @Override
    protected void updateRotation() {
        if (stuckInBlock) {
            setXRot(lockedXRot);
            setYRot(lockedYRot);
        } else {
            super.updateRotation();
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (shouldUpdateRotation()) {
            updateRotation();
        }

        if (!level().isClientSide && vanishesAfterTime()) {
            if (lifeTime < vanishAfterTime() + getVanishAnimationLength()) {
                lifeTime++;
            } else {
                remove(RemovalReason.DISCARDED);
            }
        }

        if (level().isClientSide) {
            if (lifeTime < vanishAfterTime() + getVanishAnimationLength()) {
                lifeTime++;
            }
        }

        tryToDealDamage();
    }

    public void tick() {
        Entity entity = getOwner();

        if (stuckInBlock) {
            noPhysics = false;
        }

        if (level().isClientSide || (entity == null || !entity.isRemoved()) && level().hasChunkAt(blockPosition())) {
            super.tick();
            if (shouldBurn()) {
                setSecondsOnFire(1);
            }

            HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                onHit(raytraceresult);
            }

            checkInsideBlocks();
            Vec3 vector3d = getDeltaMovement();
            double d0 = getX() + vector3d.x;
            double d1 = getY() + vector3d.y;
            double d2 = getZ() + vector3d.z;
            float f = getInertia();
            if (isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    if (getUnderWaterTrailParticle() != null && shouldSpawnParticles()) {
                        spawnUnderWaterTrailParticle();
                    }
                }

                if (slowedDownInWater()) {
                    f = 0.8F;
                }
            }

            setDeltaMovement(vector3d.add(xPower, yPower, zPower).scale(f));
            if (getTrailParticle() != null && shouldSpawnParticles()) {
                spawnTrailParticle();
            }
            setPos(d0, d1, d2);
        } else {
            remove(RemovalReason.DISCARDED);
        }
    }

    public boolean vanishesAfterTime() {
        return true;
    }

    public int vanishAfterTime() {
        return 200;
    }

    public int getVanishAnimationLength() {
        return 0;
    }

    public void onHitEntity(Entity entity) {
        playImpactSound();
    }

    public boolean getsStuckInBlocks() {
        return false;
    }

    public boolean keepsHittingAfterStuck() {
        return false;
    }

    public abstract SoundEvent getImpactSound();

    public void playImpactSound() {
        if (getImpactSound() != null) {
            playSound(getImpactSound(), 1.0F, 1.0F);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!stuckInBlock) {
            playImpactSound();
        }
        if (!getsStuckInBlocks()) {
            if (!level().isClientSide) {
                remove(RemovalReason.DISCARDED);
            }
        } else {
            lockedXRot = getXRot();
            lockedYRot = getYRot();
            stuckInBlock = true;
            setPower(0, 0, 0);
            setDeltaMovement(0, 0, 0);
        }
    }

    public boolean slowedDownInWater() {
        return true;
    }

    public void spawnTrailParticle() {
        level().addParticle(getTrailParticle(), getX(), getY() + getSpawnParticlesY(), getZ(), 0, 0, 0);
    }

    public void spawnUnderWaterTrailParticle() {
        level().addParticle(getUnderWaterTrailParticle(), getX(), getY() + getSpawnParticlesY(), getZ(), 0, 0, 0);
    }

    public double getSpawnParticlesY() {
        return 0.5D;
    }

    public boolean shouldSpawnParticles() {
        return true;
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        boolean foundOwnerPartInEntity = false;
        if (getOwner() != null && getOwner().isMultipartEntity()) {
            for (PartEntity<?> entity : getOwner().getParts()) {
                if (p_230298_1_ == entity) {
                    foundOwnerPartInEntity = true;
                }
            }
        }
        return super.canHitEntity(p_230298_1_) && (keepsHittingAfterStuck() || !stuckInBlock) && !p_230298_1_.noPhysics && foundOwnerPartInEntity == false;
    }

    protected boolean shouldBurn() {
        return true;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
    }

    protected ParticleOptions getUnderWaterTrailParticle() {
        return ParticleTypes.BUBBLE;
    }

    protected float getInertia() {
        return 0.95F;
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.put("power", newDoubleList(xPower, yPower, zPower));
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (p_70037_1_.contains("power", 9)) {
            ListTag listnbt = p_70037_1_.getList("power", 6);
            if (listnbt.size() == 3) {
                xPower = listnbt.getDouble(0);
                yPower = listnbt.getDouble(1);
                zPower = listnbt.getDouble(2);
            }
        }

    }

    public boolean isPickable() {
        return true;
    }

    public float getPickRadius() {
        return 1.0F;
    }

    public boolean hurt(DamageSource pSource, float p_70097_2_) {
        if (isInvulnerableTo(pSource)) {
            return false;
        } else {
            markHurt();
            Entity entity = pSource.getEntity();
            if (entity != null) {
                Vec3 vector3d = entity.getLookAngle();
                setDeltaMovement(vector3d);
                xPower = vector3d.x * 0.1D;
                yPower = vector3d.y * 0.1D;
                zPower = vector3d.z * 0.1D;
                setOwner(entity);
                return true;
            } else {
                return false;
            }
        }
    }

    public float getBrightness() {
        return 1.0F;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
