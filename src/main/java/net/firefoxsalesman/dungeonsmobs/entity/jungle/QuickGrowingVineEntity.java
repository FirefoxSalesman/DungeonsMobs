package net.firefoxsalesman.dungeonsmobs.entity.jungle;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class QuickGrowingVineEntity extends AbstractVineEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public QuickGrowingVineEntity(EntityType<? extends QuickGrowingVineEntity> pEntity, Level pLevel) {
		super(pEntity, pLevel);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 15.0D);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller",
				getAnimationTransitionTime(), this::predicate));
	}

	@Override
	public int getAnimationTransitionTime() {
		return 2;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (deathTime > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("quick_growing_vine_retract", LoopType.LOOP));
		} else if (burstAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("quick_growing_vine_burst", LoopType.LOOP));
		} else if (retractAnimationTick > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("quick_growing_vine_retract", LoopType.LOOP));
		} else {
			if (isOut() || burstAnimationTick > 0) {
				event.getController().setAnimation(
						RawAnimation.begin().then("quick_growing_vine_idle", LoopType.LOOP));
			} else {
				event.getController().setAnimation(RawAnimation.begin()
						.then("quick_growing_vine_idle_underground", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public int getBurstAnimationLength() {
		return 10;
	}

	@Override
	public int getRetractAnimationLength() {
		return 10;
	}

	@Override
	protected SoundEvent getAmbientSoundFoley() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.QUICK_GROWING_VINE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.QUICK_GROWING_VINE_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSoundFoley(DamageSource p_184601_1_) {
		return null;
	}

	@Override
	public SoundEvent getBurstSound() {
		return ModSoundEvents.QUICK_GROWING_VINE_BURST.get();
	}

	@Override
	public SoundEvent getRetractSound() {
		return ModSoundEvents.QUICK_GROWING_VINE_BURST_DOWN.get();
	}

	@Override
	public SoundEvent getBurstSoundFoley() {
		return null;
	}

	@Override
	public SoundEvent getRetractSoundFoley() {
		return null;
	}

	@Override
	public boolean isKelp() {
		return false;
	}

	@Override
	public boolean shouldDieInWrongHabitat() {
		return true;
	}

	@Override
	public int wrongHabitatDieChance() {
		return 50;
	}

	@Override
	public void spawnAreaDamage() {
		AreaDamageEntity areaDamage = AreaDamageEntity.spawnAreaDamage(level(), position(), this,
				2.5F,
				damageSources().mobAttack(this), 0.0F, 1.25F, 0.25F, 0.25F, 5, false, false, 0.75, 0.25,
				false, 0, 1);
		level().addFreshEntity(areaDamage);
	}

	@Override
	public void setDefaultFeatures() {
		setLengthInSegments(2 + random.nextInt(3));
		setVanishes(false);
		setAlwaysOut(false);
		setShouldRetract(true);
		setDetectionDistance(5);
	}

}
