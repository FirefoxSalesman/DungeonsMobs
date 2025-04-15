package net.firefoxsalesman.dungeonsmobs.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Cow.class)
public class CowEntityMixin {
	@Inject(at = @At("RETURN"), method = "createAttributes")
	private static AttributeSupplier.Builder createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> callbackInfo) {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED,
		    (double) 0.2F).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}
}
