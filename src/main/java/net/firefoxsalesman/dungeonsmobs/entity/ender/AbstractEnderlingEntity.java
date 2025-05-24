package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public abstract class AbstractEnderlingEntity extends VanillaEnderlingEntity implements GeoEntity {
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	protected AbstractEnderlingEntity(EntityType<? extends AbstractEnderlingEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	abstract protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event);

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
	}
}
