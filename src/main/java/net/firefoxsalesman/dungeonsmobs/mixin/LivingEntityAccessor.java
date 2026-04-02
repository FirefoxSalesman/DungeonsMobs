package net.firefoxsalesman.dungeonsmobs.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

	@Accessor
	int getAttackStrengthTicker();

	@Accessor
	@Mutable
	void setAttackStrengthTicker(int tssl);
}
