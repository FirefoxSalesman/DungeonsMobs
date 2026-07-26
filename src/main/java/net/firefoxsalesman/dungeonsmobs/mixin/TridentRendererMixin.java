package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.interfaces.IHasItemStackData;
import net.firefoxsalesman.dungeonsmobs.items.ColoredTridentItem;
import net.firefoxsalesman.dungeonsmobs.items.shield.CustomISTER;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownTridentRenderer.class)
public abstract class TridentRendererMixin extends EntityRenderer<ThrownTrident> {
	protected TridentRendererMixin(EntityRendererProvider.Context p_i46179_1_) {
		super(p_i46179_1_);
	}

	@Inject(at = @At("RETURN"), method = "getTextureLocation", cancellable = true)
	private void checkColoredTexture(ThrownTrident tridentEntity, CallbackInfoReturnable<ResourceLocation> cir) {
		if (tridentEntity instanceof IHasItemStackData) {
			ItemStack dataItem = ((IHasItemStackData) tridentEntity).getDataItem();
			if (dataItem.getItem() instanceof ColoredTridentItem) {
				cir.setReturnValue(CustomISTER.getTridentTexture(
						((ColoredTridentItem) dataItem.getItem()).getTridentColor()));
			}
		}
	}
}
