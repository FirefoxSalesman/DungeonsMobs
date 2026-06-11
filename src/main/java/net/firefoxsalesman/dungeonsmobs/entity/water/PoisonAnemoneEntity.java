package net.firefoxsalesman.dungeonsmobs.entity.water;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.PoisonQuillVineEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.AreaDamageEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PoisonAnemoneEntity extends PoisonQuillVineEntity {

	public PoisonAnemoneEntity(EntityType<? extends PoisonAnemoneEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.POISON_ANEMONE_IDLE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.POISON_ANEMONE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.POISON_ANEMONE_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSoundFoley(DamageSource source) {
		return null;
	}

	@Override
	public SoundEvent getBurstSound() {
		return ModSoundEvents.POISON_ANEMONE_BURST.get();
	}

	@Override
	public SoundEvent getRetractSound() {
		return ModSoundEvents.POISON_ANEMONE_BURST.get();
	}

	@Override
	public SoundEvent getCloseSound() {
		return ModSoundEvents.POISON_ANEMONE_CLOSE.get();
	}

	@Override
	public SoundEvent getShootSound() {
		return ModSoundEvents.POISON_ANEMONE_SHOOT.get();
	}

	@Override
	public boolean isKelp() {
		return true;
	}

	@Override
	public int wrongHabitatDieChance() {
		return 75;
	}

	@Override
	public void spawnAreaDamage() {
		AreaDamageEntity areaDamage = AreaDamageEntity.spawnAreaDamage(level(), position(), this,
				5.0F, damageSources().mobAttack(this), 0.0F, 1.5F, 0.25F, 0.25F, 5, false, false, 0.75,
				0.25, false, 0, 2);
		level().addFreshEntity(areaDamage);
	}

	@Override
	public void setDefaultFeatures() {
		super.setDefaultFeatures();
		setLengthInSegments(4 + random.nextInt(9));
	}
}
