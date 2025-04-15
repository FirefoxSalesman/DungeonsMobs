package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.utils.GoalUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MushroomCow.class)
public abstract class MooshroomEntityMixin extends Cow {

	public MooshroomEntityMixin(EntityType<? extends Cow> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(CallbackInfo callbackInfo) {
		alterGoals();
	}

	private void alterGoals() {
		GoalUtils.removeGoal(goalSelector, FloatGoal.class);
		GoalUtils.removeGoal(goalSelector, PanicGoal.class);
		GoalUtils.removeGoal(goalSelector, BreedGoal.class);
		GoalUtils.removeGoal(goalSelector, TemptGoal.class);
		GoalUtils.removeGoal(goalSelector, FollowParentGoal.class);
		GoalUtils.removeGoal(goalSelector, WaterAvoidingRandomStrollGoal.class);
		GoalUtils.removeGoal(goalSelector, LookAtPlayerGoal.class);
		GoalUtils.removeGoal(goalSelector, RandomLookAroundGoal.class);
		goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.5D, false));
		goalSelector.addGoal(1, new ApproachTargetGoal(this, 0, 1.0D, true));
		goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
		goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}
    
}
