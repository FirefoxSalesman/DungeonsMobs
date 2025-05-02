package net.firefoxsalesman.dungeonsmobs.entity.summonables;

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

public class GeomancerBombEntity extends ConstructEntity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private final float explosionRadius = 3.0F;

	public GeomancerBombEntity(Level worldIn) {
		super(ModEntities.GEOMANCER_BOMB.get(), worldIn);
	}

	public GeomancerBombEntity(Level worldIn, double x, double y, double z, LivingEntity casterIn,
			int lifeTicksIn) {
		super(ModEntities.GEOMANCER_BOMB.get(), worldIn, x, y, z, casterIn, lifeTicksIn);
	}

	public GeomancerBombEntity(EntityType<? extends GeomancerBombEntity> explodingPillarEntityEntityType,
			Level world) {
		super(explodingPillarEntityEntityType, world);
	}

	@Override
	public void handleExpiration() {
		super.handleExpiration();
		if (!this.level().isClientSide) {
			this.explode();
		}
	}

	private void explode() {
		this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), this.explosionRadius,
				Level.ExplosionInteraction.NONE);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_DAMAGE, 0.0D);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getLifeTicks() > 75) {
			event.getController().setAnimation(
					RawAnimation.begin().then("geomancer_pillar_appear", LoopType.PLAY_ONCE));
		} else {
			event.getController().setAnimation(
					RawAnimation.begin().then("geomancer_pillar_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
