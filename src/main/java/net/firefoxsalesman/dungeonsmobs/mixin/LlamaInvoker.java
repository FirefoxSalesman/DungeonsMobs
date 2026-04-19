package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.animal.horse.Llama;

@Mixin(Llama.class)
public interface LlamaInvoker {

	@Invoker("setStrength")
	public void invokeSetStrength(int strength);
}
