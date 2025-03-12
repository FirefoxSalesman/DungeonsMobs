package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class AquaticMoveHelperController<T extends Mob & IAquaticMob> extends MoveControl {
    private final T aquaticMob;

    public AquaticMoveHelperController(T aquaticMob) {
        super(aquaticMob);
        this.aquaticMob = aquaticMob;
    }

    public void tick() {
        LivingEntity target = aquaticMob.getTarget();
        if (aquaticMob.wantsToSwim(aquaticMob) && aquaticMob.isInWater()) {
            if (target != null && target.getY() > aquaticMob.getY() || aquaticMob.isSearchingForLand()) {
                aquaticMob.setDeltaMovement(aquaticMob.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
            }

            if (operation != Operation.MOVE_TO || aquaticMob.getNavigation().isDone()) {
                aquaticMob.setSpeed(0.0F);
                return;
            }

            double d0 = wantedX - aquaticMob.getX();
            double d1 = wantedY - aquaticMob.getY();
            double d2 = wantedZ - aquaticMob.getZ();
            double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
            d1 = d1 / d3;
            float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            aquaticMob.setYRot(rotlerp(aquaticMob.getYRot(), f, 90.0F));
            aquaticMob.yBodyRot = aquaticMob.getYRot();
            float f1 = (float) (speedModifier * aquaticMob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float f2 = Mth.lerp(0.125F, aquaticMob.getSpeed(), f1);
            aquaticMob.setSpeed(f2);
            aquaticMob.setDeltaMovement(aquaticMob.getDeltaMovement().add((double) f2 * d0 * 0.005D, (double) f2 * d1 * 0.1D, (double) f2 * d2 * 0.005D));
        } else {
            if (!aquaticMob.onGround()) {
                aquaticMob.setDeltaMovement(aquaticMob.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
            }

            super.tick();
        }

    }
}
