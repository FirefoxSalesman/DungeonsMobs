package net.firefoxsalesman.dungeonsmobs.goals;

import net.firefoxsalesman.dungeonsmobs.entity.AnimatableMeleeAttackMob;
import net.firefoxsalesman.dungeonsmobs.interfaces.IShieldUser;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BasicModdedAttackGoal<T extends Mob & GeoAnimatable & AnimatableMeleeAttackMob> extends Goal {

	public T mob;
	@Nullable
	public LivingEntity target;
	private final SoundEvent soundEvent;
	private final int cooldown;
	private long lastUseTime;

	public BasicModdedAttackGoal(T mob, SoundEvent soundEvent, int cooldown) {
		this.soundEvent = soundEvent;
		this.cooldown = cooldown;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.JUMP));
		this.mob = mob;
		target = mob.getTarget();
	}

	@Override
	public boolean isInterruptable() {
		return false;
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		long i = mob.level().getGameTime();
		if (i - lastUseTime < cooldown) {
			return false;
		} else {
			target = mob.getTarget();
			return target != null && !mob.isBlocking()
					&& mob.distanceTo(target) <= getAttackReachSqr(target) && animationsUseable()
					&& mob.hasLineOfSight(target);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return target != null && !animationsUseable();
	}

	@Override
	public void start() {
		mob.setAttackAnimationTick(mob.getAttackAnimationLength());
		lastUseTime = mob.level().getGameTime();
		mob.level().broadcastEntityEvent(mob, (byte) 4);
	}

	@Override
	public void tick() {
		target = mob.getTarget();

		if (mob.getAttackAnimationTick() == mob.getAttackAnimationActionPoint() && soundEvent != null) {
			mob.playSound(soundEvent, 1.0F, 1.0F);
		}

		if (target != null && mob.distanceTo(target) < 4
				&& mob.getAttackAnimationTick() == mob.getAttackAnimationActionPoint()) {
			mob.doHurtTarget(target);
		}
	}

	@Override
	public void stop() {
		if (target != null && !isShieldDisabled(mob) && shouldBlockForTarget(target)
				&& mob.getOffhandItem()
						.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)
				&& mob.getRandom().nextInt(4) == 0) {
			mob.startUsingItem(InteractionHand.OFF_HAND);
		}
	}

	public boolean isShieldDisabled(Mob shieldUser) {
		return shieldUser instanceof IShieldUser && ((IShieldUser) shieldUser).isShieldDisabled();
	}

	public boolean shouldBlockForTarget(LivingEntity target) {
		return !(target instanceof Mob) || ((Mob) target).getTarget() == mob;
	}

	public boolean animationsUseable() {
		return mob.getAttackAnimationTick() <= 0;
	}

	public double getAttackReachSqr(LivingEntity p_179512_1_) {
		return mob.getBbWidth() * 2.0F * mob.getBbWidth() * 2.0F + p_179512_1_.getBbWidth();
	}

}
