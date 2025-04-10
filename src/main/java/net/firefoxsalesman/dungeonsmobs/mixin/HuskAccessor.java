package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.monster.Husk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Husk.class)
public class HuskAccessor {
	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(CallbackInfo callbackInfo) {
		if (DungeonsMobsConfig.COMMON.ENABLE_STRONGER_HUSKS.get()) {
			((EntityAccessor) this).setDimensions(new EntityDimensions(0.6F * 1.2F, 1.95F * 1.2F, false));
		}
	}

}
