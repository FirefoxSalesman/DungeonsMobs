package net.firefoxsalesman.dungeonsmobs.gear.entities;

import net.firefoxsalesman.dungeonsmobs.lib.entities.TotemBaseEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.getCanHealPredicate;
import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;

public class TotemOfRegenerationEntity extends TotemBaseEntity implements GeoEntity {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public TotemOfRegenerationEntity(EntityType<?> pEntity, Level pLevel) {
		super(pEntity, pLevel, 240, 2);
	}

	@Override
	protected void applyTotemEffect() {
		LivingEntity owner = getOwner();
		if (owner == null)
			return;
		if (this.lifeTicks % 20 == 0) {
			applyToNearbyEntities(owner, 8,
					getCanHealPredicate(owner), (LivingEntity nearbyEntity) -> {
						if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
							nearbyEntity.heal(1F);
							PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
						}
					});
			if (owner.getHealth() < owner.getMaxHealth()) {
				owner.heal(1F);
				PROXY.spawnParticles(owner, ParticleTypes.HEART);
			}
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
		event.getController().setAnimation(RawAnimation.begin()
				.then("animation.totem_of_regeneration.idle", LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
}
