package net.firefoxsalesman.dungeonsmobs.entity.creepers;

import java.util.Collection;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class IcyCreeperEntity extends Creeper {
	private int oldSwell;
	private int swell;
	private final int maxSwell = 30;
	private final int explosionRadius = 3;

	public IcyCreeperEntity(Level worldIn) {
		super(ModEntities.ICY_CREEPER.get(), worldIn);
	}

	public static boolean canIcyCreeperSpawn(EntityType<IcyCreeperEntity> entityType, ServerLevelAccessor iWorld,
			MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return checkMonsterSpawnRules(entityType, iWorld, spawnReason, blockPos, rand)
				&& (spawnReason == MobSpawnType.SPAWNER || iWorld.canSeeSky(blockPos));
	}

	@Override
	public void aiStep() {
		if (level().isClientSide) {
			level().addParticle(ModParticleTypes.SNOWFLAKE.get(), getRandomX(0.5D),
					getRandomY() - 0.25D, getRandomZ(0.5D),
					(random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(),
					(random.nextDouble() - 0.5D) * 2.0D);
		}
		super.aiStep();
	}

	public IcyCreeperEntity(EntityType<? extends Creeper> type, Level worldIn) {
		super(type, worldIn);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Creeper.createAttributes();
	}

	public void tick() {
		if (isAlive()) {
			oldSwell = swell;
			if (isIgnited()) {
				setSwellDir(1);
			}

			int i = getSwellDir();
			if (i > 0 && swell == 0) {
				playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.25F);
			}

			swell += i;
			if (swell < 0) {
				swell = 0;
			}

			if (swell >= maxSwell) {
				swell = maxSwell;
				explodeCreeper();
			}
		}

		super.tick();
	}

	private void explodeCreeper() {
		if (!level().isClientSide) {
			float f = isPowered() ? 2.0F : 1.0F;
			dead = true;
			level().explode(this, getX(), getY(), getZ(), (float) explosionRadius * f,
					Level.ExplosionInteraction.BLOCK);
			playSound(ModSoundEvents.ICY_CREEPER_EXPLODE.get(), 2.0F, 1.0F);
			remove(RemovalReason.DISCARDED);
			spawnLingeringCloud();
		}

		for (int i = 0; i < 75; ++i) {
			double d0 = random.nextGaussian() * 0.3D;
			double d1 = random.nextGaussian() * 0.2D;
			double d2 = random.nextGaussian() * 0.3D;
			level().addParticle(ParticleTypes.POOF, getX(), getY(), getZ(), d0, d1, d2);
		}

		for (int i = 0; i < 50; ++i) {
			double d0 = random.nextGaussian() * 0.6D;
			double d1 = random.nextGaussian() * 0.3D;
			double d2 = random.nextGaussian() * 0.6D;
			level().addParticle(ModParticleTypes.SNOWFLAKE.get(), getX(), getY(), getZ(), d0, d1, d2);
		}

	}

	private void spawnLingeringCloud() {
		addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
		Collection<MobEffectInstance> collection = getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(level(), getX(), getY(), getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius()
					/ (float) areaeffectcloudentity.getDuration());

			for (MobEffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
			}

			level().addFreshEntity(areaeffectcloudentity);
		}

	}
}
