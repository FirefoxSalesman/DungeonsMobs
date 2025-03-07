package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WatchlingEntity extends AbstractEnderlingEntity {

    AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public WatchlingEntity(EntityType<? extends WatchlingEntity> p_i50210_1_, Level p_i50210_2_) {
	super(p_i50210_1_, p_i50210_2_);
    }

    protected void registerGoals() {
	this.goalSelector.addGoal(0, new FloatGoal(this));
	this.goalSelector.addGoal(2, new AbstractEnderlingEntity.AttackGoal(1.5D));
	this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
	this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
	this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	this.targetSelector.addGoal(1, new AbstractEnderlingEntity.FindPlayerGoal(this, null));
	this.targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractEnderlingEntity.class)
	    .setAlertOthers().setUnseenMemoryTicks(500));
	this.targetSelector.addGoal(1,
	    new EnderlingTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));

	// this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this,
	// AbstractEndermanVariant.class, true, false));
    }

    public MobType getMobType() {
	return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
	return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30.0D)
	    .add(Attributes.MOVEMENT_SPEED, 0.225F).add(Attributes.ATTACK_DAMAGE, 7.0D)
	    .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected SoundEvent getAmbientSound() {
	return ModSoundEvents.WATCHLING_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
	return ModSoundEvents.WATCHLING_HURT.get();
    }

    protected SoundEvent getDeathSound() {
	return ModSoundEvents.WATCHLING_DEATH.get();
    }

    public boolean doHurtTarget(Entity p_70652_1_) {
	this.playSound(ModSoundEvents.WATCHLING_ATTACK.get(), 1.0F, 1.0F);
	return super.doHurtTarget(p_70652_1_);
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	this.playSound(this.getStepSound(), 0.75F, 1.0F);
    }

    protected SoundEvent getStepSound() {
	return ModSoundEvents.WATCHLING_STEP.get();
    }

    public void setTarget(LivingEntity p_70624_1_) {
	if (p_70624_1_ != null && p_70624_1_ != this.getTarget()) {
	    this.teleport(p_70624_1_.getX() - 3 + this.random.nextInt(6), p_70624_1_.getY(),
		p_70624_1_.getZ() - 3 + this.random.nextInt(6));
	}
	super.setTarget(p_70624_1_);
    }

    public void baseTick() {
	super.baseTick();
    }

    private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
	if (this.isAttacking() > 0) {
	    event.getController()
		.setAnimation(RawAnimation.begin().then("watchling_attack", LoopType.LOOP));
	} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
	    if (this.isRunning() > 0) {
		event.getController().setAnimation(
		    RawAnimation.begin().then("watchling_run", LoopType.LOOP));
	    } else {
		event.getController().setAnimation(
		    RawAnimation.begin().then("watchling_walk", LoopType.LOOP));
	    }
	} else {
	    event.getController().setAnimation(RawAnimation.begin().then("watchling_idle", LoopType.LOOP));
	}
	return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
	controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
	return factory;
    }
}
