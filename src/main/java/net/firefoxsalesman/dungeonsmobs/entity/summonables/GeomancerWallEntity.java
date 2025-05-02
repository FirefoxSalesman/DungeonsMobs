package net.firefoxsalesman.dungeonsmobs.entity.summonables;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeomancerWallEntity extends ConstructEntity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public GeomancerWallEntity(Level world) {
		super(ModEntities.GEOMANCER_WALL.get(), world);
	}

	public GeomancerWallEntity(EntityType<? extends GeomancerWallEntity> entityType, Level world) {
		super(entityType, world);
	}

	public GeomancerWallEntity(Level worldIn, double x, double y, double z, LivingEntity casterIn,
			int lifeTicksIn) {
		super(ModEntities.GEOMANCER_WALL.get(), worldIn, x, y, z, casterIn, lifeTicksIn);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_DAMAGE, 0.0D);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 20, this::predicate));
	}

	public void baseTick() {
		super.baseTick();

		if (this.getLifeTicks() == 100) {
			this.playSound(ModSoundEvents.GEOMANCER_WALL_SPAWN.get(), 1.0F, 1.0F);
		}

		if (this.getLifeTicks() == 40) {
			this.playSound(ModSoundEvents.GEOMANCER_WALL_DESPAWN.get(), 1.0F, 1.0F);
		}
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getLifeTicks() <= 100) {
			if (this.getLifeTicks() < 40) {
				event.getController().setAnimation(RawAnimation.begin()
						.then("geomancer_pillar_disappear", LoopType.HOLD_ON_LAST_FRAME));
			} else if (this.getLifeTicks() > 75) {
				event.getController().setAnimation(RawAnimation.begin().then("geomancer_pillar_appear",
						LoopType.PLAY_ONCE));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("geomancer_pillar_idle", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
