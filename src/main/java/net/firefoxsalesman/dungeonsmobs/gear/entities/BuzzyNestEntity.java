package net.firefoxsalesman.dungeonsmobs.gear.entities;

import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;

import net.firefoxsalesman.dungeonsmobs.lib.entities.TotemBaseEntity;
import net.firefoxsalesman.dungeonsmobs.lib.summon.SummonHelper;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BuzzyNestEntity extends TotemBaseEntity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public BuzzyNestEntity(EntityType<?> entityType, Level level) {
		super(entityType, level, 200, 20);
	}

	@Override
	protected void applyTotemEffect() {
		if (!this.level().isClientSide() && this.lifeTicks % 20 == 0 && this.getOwner() != null) {
			SummonHelper.summonEntity(this.getOwner(), this.blockPosition(), EntityType.BEE);
			this.level().playSound(null, this.blockPosition(), SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS,
					1.0F, 1.0F);
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 5, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.lifeTicks > 0 && this.lifeTicks % 20 == 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("animation.buzzy_nest.spawn",
							PLAY_ONCE));
		} else if (this.lifeTicks > 0) {
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.buzzy_nest.activate", PLAY_ONCE));
		} else {
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.buzzy_nest.deactivate", PLAY_ONCE));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
