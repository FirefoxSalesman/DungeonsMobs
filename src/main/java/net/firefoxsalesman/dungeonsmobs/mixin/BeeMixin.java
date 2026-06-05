package net.firefoxsalesman.dungeonsmobs.mixin;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal {

	protected BeeMixin(EntityType<? extends Animal> entityType, Level world) {
		super(entityType, world);
	}

	@Shadow
	public abstract void setFlag(int pFlagId, boolean p_226404_2_);

	@Inject(method = "Lnet/minecraft/world/entity/animal/Bee;setHasStung(Z)V", at = @At(value = "TAIL"))
	private void dungeonslibraries_canReplaceCurrentItem(boolean p_226449_1_, CallbackInfo ci) {
		if (FollowerLeaderHelper.isFollower(this)) {
			this.setFlag(4, false);
		}
	}

}
