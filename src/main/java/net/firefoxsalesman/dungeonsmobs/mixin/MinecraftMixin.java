package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.lib.network.SwitchHandMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	private static boolean SHOULD_SWITCH_HAND = false;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V"), method = "Lnet/minecraft/client/Minecraft;startAttack()Z")
	private void dungeons_libraries_startAttack_onAttackEntity(CallbackInfoReturnable<Boolean> cir) {
		SHOULD_SWITCH_HAND = true;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeHooks;onEmptyLeftClick(Lnet/minecraft/world/entity/player/Player;)V", remap = false), method = "Lnet/minecraft/client/Minecraft;startAttack()Z")
	private void dungeons_libraries_startAttack_onMiss(CallbackInfoReturnable<Boolean> cir) {
		SHOULD_SWITCH_HAND = true;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/InputEvent$InteractionKeyMappingTriggered;shouldSwingHand()Z"), method = "Lnet/minecraft/client/Minecraft;startAttack()Z")
	private void dungeons_libraries_startAttack_onSwing(CallbackInfoReturnable<Boolean> cir) {
		if (SHOULD_SWITCH_HAND) {
			NetworkHandler.INSTANCE.sendToServer(new SwitchHandMessage());
			SHOULD_SWITCH_HAND = false;
		}
	}
}
