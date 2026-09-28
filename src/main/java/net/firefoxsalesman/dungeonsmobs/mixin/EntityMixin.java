package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(at = @At("RETURN"), method = "fireImmune", cancellable = true)
	private void fireImmune(CallbackInfoReturnable<Boolean> ci) {
		if ((Entity) ((Object) this) instanceof LivingEntity living)
			NewMobEnchantUtils.executeIfPresentWithLevel(living, ModMobEnchants.FIRE_TRAIL.get(),
					level -> ci.setReturnValue(true));
	}
}
