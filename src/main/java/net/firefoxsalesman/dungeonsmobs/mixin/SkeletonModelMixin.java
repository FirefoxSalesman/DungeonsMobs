package net.firefoxsalesman.dungeonsmobs.mixin;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

@Mixin(SkeletonModel.class)
public class SkeletonModelMixin {

	@WrapOperation(method = "prepareMobModel(Lnet/minecraft/world/entity/Mob;FFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean handleIsBowPrepareMobModel(ItemStack instance, Item pItem, Operation<Boolean> original) {
		return instance.getItem() instanceof BowItem;
	}

	@WrapOperation(method = "setupAnim(Lnet/minecraft/world/entity/Mob;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean handleIsBowSetupAnim(ItemStack instance, Item pItem, Operation<Boolean> original) {
		return instance.getItem() instanceof BowItem;
	}
}
