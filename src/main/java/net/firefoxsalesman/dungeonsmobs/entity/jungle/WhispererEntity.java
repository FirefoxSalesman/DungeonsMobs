package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AbstractTrapEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class WhispererEntity extends AbstractWhispererEntity {

	public WhispererEntity(EntityType<? extends AbstractWhispererEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			setClimbing(horizontalCollision);
		}
	}

	@Override
	protected AbstractTrapEntity getTrap() {
		return ModEntities.SIMPLE_TRAP.get().create(level());
	}

	@Override
	protected int getBasicAttackActionPoint() {
		return attackAnimationActionPoint;
	}

	@Override
	protected int getGrappleActionPoint() {
		return grappleAnimationActionPoint;
	}

	@Override
	protected SoundEvent getGrappleSound() {
		return ModSoundEvents.WHISPERER_GRAPPLE_VOCAL.get();
	}

	@Override
	protected SoundEvent getSummonQGSound() {
		return ModSoundEvents.WHISPERER_SUMMON_QGV_VOCAL.get();
	}

	@Override
	protected SoundEvent getSummonPoisonFoleySound() {
		return ModSoundEvents.WHISPERER_SUMMON_PQV_FOLEY.get();
	}

	@Override
	protected SoundEvent getSummonPoisonVocalSound() {
		return ModSoundEvents.WHISPERER_SUMMON_PQV_VOCAL.get();
	}

	@Override
	protected SoundEvent getAttackSound() {
		return ModSoundEvents.WHISPERER_ATTACK_VOCAL.get();
	}

	@Override
	protected EntityType<? extends AbstractVineEntity> getOffensiveVine() {
		return ModEntities.POISON_QUILL_VINE.get();
	}

	@Override
	protected EntityType<? extends AbstractVineEntity> getAreaDenialVine() {
		return ModEntities.QUICK_GROWING_VINE.get();
	}

	@Override
	public boolean isWavewhisperer() {
		return false;
	}

	@Override
	public boolean isInWrongHabitat() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WHISPERER_IDLE_VOCAL.get();
	}

	@Override
	protected SoundEvent getAmbientSoundFoley() {
		return ModSoundEvents.WHISPERER_IDLE_FOLEY.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return ModSoundEvents.WHISPERER_HURT_VOCAL.get();
	}

	@Override
	protected SoundEvent getHurtSoundFoley(DamageSource pDamageSource) {
		return ModSoundEvents.WHISPERER_HURT_FOLEY.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WHISPERER_DEATH.get();
	}

	@Override
	protected SoundEvent getSwimSound() {
		return null;
	}

	@Override
	protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (attackAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_attack", LoopType.LOOP));
		} else if (summonPQVAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_summon_pqv", LoopType.LOOP));
		} else if (summonQGVAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_summon_qgv", LoopType.LOOP));
		} else if (grappleAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_grapple", LoopType.LOOP));
		} else if (isClimbing()) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("whisperer_climb", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_walk", LoopType.LOOP));
		} else {
			event.getController().setAnimation(
					RawAnimation.begin().then("whisperer_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new WhispererEntity.BasicAttackGoal(this));
		goalSelector.addGoal(2, new WhispererEntity.GrappleGoal(this));
		goalSelector.addGoal(3, new WhispererEntity.SummonPQVAttackGoal(this));
		goalSelector.addGoal(4, new WhispererEntity.SummonQGVGoal(this));
		goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolem.class, 4.0F, 1.0D, 1.0D));
		goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.0D, 1.0D));
		goalSelector.addGoal(6, new ApproachTargetGoal(this, 7, 1.1D, true));
		goalSelector.addGoal(7, new LookAtTargetGoal(this));
		goalSelector.addGoal(8, new RandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}
}
