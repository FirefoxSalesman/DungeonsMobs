package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowEntityMixin extends Projectile {

    protected AbstractArrowEntityMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(at = @At("RETURN"), method = "getWaterInertia", cancellable = true)
    private void checkShotByAquaticMob(CallbackInfoReturnable<Float> cir) {
        Entity owner = getOwner();
        if (owner instanceof IAquaticMob) {
            cir.setReturnValue(0.99F);
        }
    }

}
