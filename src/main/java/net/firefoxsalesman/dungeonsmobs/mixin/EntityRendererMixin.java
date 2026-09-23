package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.interfaces.NametagHaver;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
	@Inject(at = @At(value = "HEAD"), method = "renderNameTag", cancellable = true)
	private void renderNameTag(Entity entity, Component pDisplayName, PoseStack pMatrixStack,
			MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
		if (!((NametagHaver) entity).getShowNametag())
			ci.cancel();
	}
}
