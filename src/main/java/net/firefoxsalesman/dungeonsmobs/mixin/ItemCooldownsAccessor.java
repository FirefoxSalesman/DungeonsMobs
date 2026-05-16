package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.item.ItemCooldowns;

@Mixin(ItemCooldowns.class)
public interface ItemCooldownsAccessor {
	@Accessor
	int getTickCount();
}
