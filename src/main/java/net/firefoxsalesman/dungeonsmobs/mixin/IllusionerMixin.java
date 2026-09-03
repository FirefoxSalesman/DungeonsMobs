package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.firefoxsalesman.dungeonsmobs.utils.ModProjectileHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Illusioner.class)
public class IllusionerMixin extends SpellcasterIllager {

	protected IllusionerMixin(EntityType<? extends SpellcasterIllager> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	protected SoundEvent getCastingSoundEvent() {
		return SoundEvents.ILLUSIONER_CAST_SPELL;
	}

	@Override
	public void applyRaidBuffs(int pWave, boolean pUnusedFalse) {
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Inject(method = "performRangedAttack", at = @At("INVOKE"), cancellable = true)
	public void shootFireworks(LivingEntity pTarget, float pDistanceFactor, CallbackInfo ci) {
		int explosionsByDifficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getDifficulty()
				.getId();

		if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem) {
			ItemStack fireworkRocket = ModProjectileHelper.createRocket(explosionsByDifficulty * 2,
					DyeColor.PINK, DyeColor.PURPLE);
			FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(this.level(),
					fireworkRocket,
					this, this.getX(), this.getEyeY() - (double) 0.15F, this.getZ(), true);
			double xDifference = pTarget.getX() - this.getX();
			double yDifference = pTarget.getY(0.3333333333333333D) - fireworkrocketentity.getY();
			double zDifference = pTarget.getZ() - this.getZ();
			fireworkrocketentity.shoot(xDifference, yDifference, zDifference, 1.0F,
					(float) (18 - this.level().getDifficulty().getId() * 7.5));
			this.playSound(SoundEvents.FIREWORK_ROCKET_SHOOT, 1.0F,
					1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level().addFreshEntity(fireworkrocketentity);
		}
		ci.cancel();
	}

}
