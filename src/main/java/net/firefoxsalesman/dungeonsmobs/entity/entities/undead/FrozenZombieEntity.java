package net.firefoxsalesman.dungeonsmobs.entity.entities.undead;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.goals.switchcombat.SwitchCombatItemGoal;
import net.firefoxsalesman.dungeonsmobs.goals.switchcombat.ThrowAndMeleeAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class FrozenZombieEntity extends Zombie implements RangedAttackMob {
	public FrozenZombieEntity(Level worldIn) {
		super(worldIn);
	}

	public FrozenZombieEntity(EntityType<? extends FrozenZombieEntity> zombie, Level worldIn) {
		super(zombie, worldIn);
	}

	public static boolean canFrozenZombieSpawn(EntityType<FrozenZombieEntity> entityType,
			ServerLevelAccessor iWorld, MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return checkMonsterSpawnRules(entityType, iWorld, spawnReason, blockPos, rand)
				&& (spawnReason == MobSpawnType.SPAWNER || iWorld.canSeeSky(blockPos));
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new FrozenZombieAttackGoal(this, 1.0D, 20, 15.0F, false));
		this.goalSelector.addGoal(3, new SwitchCombatItemGoal(this, 6.0D, 6.0D));
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false,
				Turtle.BABY_ON_LAND_SELECTOR));
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.FROZEN_ZOMBIE_IDLE.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ModSoundEvents.FROZEN_ZOMBIE_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.FROZEN_ZOMBIE_DEATH.get();
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Zombie.createAttributes();
	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity
				&& entityIn instanceof Zombie) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
		super.populateDefaultEquipmentSlots(random, difficultyInstance);
		SpawnEquipmentHelper.equipOffhand(Items.SNOWBALL.getDefaultInstance(), this);
	}

	@Override
	public void aiStep() {
		if (this.level().isClientSide) {
			this.level().addParticle(ModParticleTypes.SNOWFLAKE.get(), this.getRandomX(0.5D),
					this.getRandomY() - 0.25D, this.getRandomZ(0.5D),
					(this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
					(this.random.nextDouble() - 0.5D) * 2.0D);
		}
		super.aiStep();
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float v) {
		Snowball snowballentity = new Snowball(this.level(), this);
		double adjustedEyeY = livingEntity.getEyeY() - 1.100000023841858D;
		double xDifference = livingEntity.getX() - this.getX();
		double yDifference = adjustedEyeY - snowballentity.getY();
		double zDifference = livingEntity.getZ() - this.getZ();
		float adjustedHorizontalDistance = Mth
				.sqrt((float) (xDifference * xDifference + zDifference * zDifference)) * 0.2F;
		snowballentity.shoot(xDifference, yDifference + (double) adjustedHorizontalDistance, zDifference, 1.6F,
				7.5F);
		this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level().addFreshEntity(snowballentity);
	}

	static class FrozenZombieAttackGoal extends ThrowAndMeleeAttackGoal {
		private final FrozenZombieEntity zombie;
		private int raiseArmTicks;

		FrozenZombieAttackGoal(FrozenZombieEntity zombieEntity, double speedAmplifier, int attackInterval,
				float maxDistance, boolean useLongMemory) {
			super(zombieEntity, speedAmplifier, attackInterval, maxDistance, useLongMemory);
			this.zombie = zombieEntity;
		}

		@Override
		public boolean hasThrowableItemInMainhand() {
			return (this.zombie.getMainHandItem().getItem() instanceof SnowballItem
					| this.zombie.getMainHandItem().getItem() instanceof EggItem)
					&& zombie.getTarget() != null
					&& !zombie.getTarget().hasEffect(MobEffects.MOVEMENT_SLOWDOWN);
		}

		public void start() {
			super.start();
			this.raiseArmTicks = 0;
		}

		public void stop() {
			super.stop();
			this.zombie.setAggressive(false);
		}

		public void tick() {
			super.tick();
			++this.raiseArmTicks;
			this.zombie.setAggressive(this.raiseArmTicks >= 5
					&& this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);

		}
	}
}
