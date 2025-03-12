package net.firefoxsalesman.dungeonsmobs.entity.projectiles;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.ender.AbstractEnderlingEntity;
import net.firefoxsalesman.dungeonsmobs.mod.ModEffects;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class SnarelingGlobEntity extends ThrowableItemProjectile {

    public SnarelingGlobEntity(EntityType<? extends SnarelingGlobEntity> p_i50159_1_, Level p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public SnarelingGlobEntity(Level p_i1774_1_, LivingEntity p_i1774_2_) {
        super(ModEntities.SNARELING_GLOB.get(), p_i1774_2_, p_i1774_1_);
    }

    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @OnlyIn(Dist.CLIENT)
    private ParticleOptions getParticle() {
        ItemStack itemstack = getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 3) {
            ParticleOptions iparticledata = getParticle();

            for (int i = 0; i < 8; ++i) {
                level().addParticle(iparticledata, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        Entity entity = p_213868_1_.getEntity();
        if (entity instanceof LivingEntity && !(entity instanceof AbstractEnderlingEntity)) {
            entity.hurt(level().damageSources().thrown(this, getOwner()), 3);
        }

        if (entity instanceof LivingEntity && !entity.level().isClientSide) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(ModEffects.ENSNARED.get(), 100));
        }
    }

    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte) 3);
            remove(RemovalReason.DISCARDED);
        }

        playSound(ModSoundEvents.SNARELING_GLOB_LAND.get(), 2.0F, 1.0F);

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
