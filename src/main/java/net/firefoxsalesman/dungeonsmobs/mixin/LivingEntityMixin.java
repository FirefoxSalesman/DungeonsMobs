package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Follower;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "dropFromLootTable", at = @At("HEAD"), cancellable = true)
	private void cancelDropFromLootTableIfSummoned(DamageSource source, boolean hurtByPlayer, CallbackInfo ci) {
		Follower cap = FollowerLeaderHelper.getFollowerCapability(this);
		if (cap.isSummon()) {
			ci.cancel();
		}
	}
}
