package net.firefoxsalesman.dungeonsmobs.entity.redstone;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RedstoneGolemEntity extends Raider implements GeoEntity {
	private int attackTimer;
	private int mineAttackCooldown;
	private int attackID;
	public static final byte MELEE_ATTACK = 1;
	public static final byte MINE_ATTACK = 2;
	private static final EntityDataAccessor<Boolean> SUMMONING_MINES = SynchedEntityData
			.defineId(RedstoneGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MELEEATTACKING = SynchedEntityData
			.defineId(RedstoneGolemEntity.class, EntityDataSerializers.BOOLEAN);

	public RedstoneGolemEntity(Level worldIn) {
		super(ModEntities.REDSTONE_GOLEM.get(), worldIn);
	}

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int soundLoopTick;

	public RedstoneGolemEntity(EntityType<? extends RedstoneGolemEntity> type, Level worldIn) {
		super(type, worldIn);
		setMaxUpStep(1.25F);
		xpReward = 40;
		mineAttackCooldown = 10 * 20;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(SUMMONING_MINES, false);
		entityData.define(MELEEATTACKING, false);
	}

	public boolean isSummoningMines() {
		return entityData.get(SUMMONING_MINES);
	}

	public void setSummoningMines(boolean summoningMines) {
		entityData.set(SUMMONING_MINES, summoningMines);
	}

	public boolean isMeleeAttacking() {
		return entityData.get(MELEEATTACKING);
	}

	public void setMeleeAttacking(boolean attacking) {
		entityData.set(MELEEATTACKING, attacking);
	}

	@Override
	public void registerControllers(ControllerRegistrar data) {
		data.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		Vec3 velocity = getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		if (isSummoningMines()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(RawAnimation.begin().then("animation.redstone_golem.summon",
					LoopType.PLAY_ONCE));
		} else if (isMeleeAttacking()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(RawAnimation.begin().then("animation.redstone_golem.attack",
					LoopType.PLAY_ONCE));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimationSpeed(groundSpeed * 10);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_golem.walk", LoopType.LOOP));
		} else {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.redstone_golem.general", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new MeleeGoal());
		goalSelector.addGoal(5, new AttackGoal(this, 1.3D));
		goalSelector.addGoal(4, new SummonRedstoneMinesGoal());
		goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.REDSTONE_GOLEM_IDLE.get();
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(ModSoundEvents.REDSTONE_GOLEM_STEP.get(), 1.0F, 1.0F);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.REDSTONE_GOLEM_HURT.get();
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.REDSTONE_GOLEM_DEATH.get();
	}

	@Override
	public void baseTick() {
		super.baseTick();

		soundLoopTick++;
		if (soundLoopTick % 30 == 0) {
			playSound(ModSoundEvents.REDSTONE_GOLEM_IDLE_PULSE_LOOP.get(), 0.75F, 1.0F);
		}

		if (!level().isClientSide && random.nextInt(100) == 0) {
			playSound(ModSoundEvents.REDSTONE_GOLEM_SPARK.get(), 0.25F, getVoicePitch());
			level().broadcastEntityEvent(this, (byte) 4);
		}
	}

	public void aiStep() {
		super.aiStep();
		if (attackID != 0) {
			++attackTimer;
		}
		if (!level().isClientSide && mineAttackCooldown > 0) {
			--mineAttackCooldown;
		}
		handleLeafCollision();
		handleSteppingOnBlocks();
	}

	private void handleSteppingOnBlocks() {
		if (getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F
				&& random.nextInt(5) == 0) {
			int i = Mth.floor(getX());
			int j = Mth.floor(getY() - (double) 0.2F);
			int k = Mth.floor(getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = level().getBlockState(pos);
			if (!blockstate.isAir()) {
				level().addParticle(
						new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos),
						getX() + ((double) random.nextFloat() - 0.5D)
								* (double) getBbWidth(),
						getY() + 0.1D,
						getZ() + ((double) random.nextFloat() - 0.5D)
								* (double) getBbWidth(),
						4.0D * ((double) random.nextFloat() - 0.5D), 0.5D,
						((double) random.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	private void handleLeafCollision() {
		if (isAlive()) {

			if (horizontalCollision && net.minecraftforge.event.ForgeEventFactory
					.getMobGriefingEvent(level(), this)) {
				boolean destroyedLeafBlock = false;
				AABB axisalignedbb = getBoundingBox().inflate(0.2D);

				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX),
						Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ),
						Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY),
						Mth.floor(axisalignedbb.maxZ))) {
					BlockState blockstate = level().getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (block instanceof LeavesBlock) {
						destroyedLeafBlock = level().destroyBlock(blockpos, true, this)
								|| destroyedLeafBlock;
					}
				}

				if (!destroyedLeafBlock && onGround()) {
					jumpFromGround();
				}
			}
		}
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 200.0D) // 2x Golem Health
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
				.add(Attributes.ATTACK_DAMAGE, 16.0D) // >= Golem Attack
				.add(Attributes.ATTACK_KNOCKBACK, 3.0D) // 2x Ravager knockback
				.add(Attributes.FOLLOW_RANGE, 25.0D);
	}

	private float getAttackKnockback() {
		return (float) getAttributeValue(Attributes.ATTACK_KNOCKBACK);
	}

	/**
	 * Decrements the entity's air supply when underwater
	 */

	protected int decreaseAirSupply(int air) {
		return air;
	}

	public boolean doHurtTarget(Entity entityIn) {
		if (!level().isClientSide && attackID == 0) {
			attackID = MELEE_ATTACK;
		}
		return true;
	}

	public boolean checkSpawnObstruction(LevelReader worldIn) {
		BlockPos golemPos = blockPosition();
		BlockPos posBeneathGolem = golemPos.below();
		BlockState blockstateBeneathGolem = worldIn.getBlockState(posBeneathGolem);
		if (!blockstateBeneathGolem.entityCanStandOn(worldIn, posBeneathGolem, this)) {
			return false;
		} else {
			for (int i = 1; i < 4; ++i) {
				BlockPos posAboveGolem = golemPos.above(i);
				BlockState blockstateAboveGolem = worldIn.getBlockState(posAboveGolem);
				if (!NaturalSpawner
						.isValidEmptySpawnBlock(worldIn,
								posAboveGolem,
								blockstateAboveGolem,
								blockstateAboveGolem.getFluidState(),
								ModEntities.REDSTONE_GOLEM.get())) {
					return false;
				}
			}

			return NaturalSpawner
					.isValidEmptySpawnBlock(worldIn,
							golemPos,
							worldIn.getBlockState(golemPos),
							Fluids.EMPTY.defaultFluidState(),
							ModEntities.REDSTONE_GOLEM.get())
					&& worldIn.isUnobstructed(this);
		}
	}

	/**
	 * Handler for {link WorldsetEntityState}
	 */
	private void setAttackID(int id) {
		attackID = id;
		attackTimer = 0;
		level().broadcastEntityEvent(this, (byte) -id);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id <= 0) {
			attackID = Math.abs(id);
			attackTimer = 0;
		} else if (id == 4) {
			for (int i = 0; i < 5; i++) {
				level().addParticle(ModParticleTypes.REDSTONE_SPARK.get(), getRandomX(1.1D),
						getRandomY(), getRandomZ(1.1D),
						-0.15D + random.nextDouble() * 0.15D,
						-0.15D + random.nextDouble() * 0.15D,
						-0.15D + random.nextDouble() * 0.15D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, 0.875F * getEyeHeight(), getBbWidth() * 0.4F);
	}

	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	// NAVIGATION

	protected PathNavigation createNavigation(Level worldIn) {
		return new Navigator(this, worldIn);
	}

	static class Navigator extends GroundPathNavigation {
		public Navigator(Mob mobEntity, Level world) {
			super(mobEntity, world);
		}

		protected PathFinder createPathFinder(int p_179679_1_) {
			nodeEvaluator = new Processor();
			return new PathFinder(nodeEvaluator, p_179679_1_);
		}
	}

	static class Processor extends WalkNodeEvaluator {
		private Processor() {
		}

		protected BlockPathTypes evaluateBlockPathType(BlockGetter blockReader, boolean canBreakDoors,
				boolean canWalkThroughDoorways, BlockPos blockPos, BlockPathTypes pathNodeType) {
			return pathNodeType == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN
					: super.evaluateBlockPathType(blockReader, blockPos, pathNodeType);
		}
	}

	// RAIDER METHODS
	@Override
	public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {

	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.IRON_GOLEM_REPAIR;
	}

	public boolean canBeLeader() {
		return false;
	}

	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getMobType() == MobType.ILLAGER
				|| entityIn instanceof Raider) {
			return getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	class AttackGoal extends MeleeAttackGoal {
		private int maxAttackTimer = 20;
		private final double moveSpeed;
		private int delayCounter;
		private int attackTimer;

		public AttackGoal(PathfinderMob creatureEntity, double moveSpeed) {
			super(creatureEntity, moveSpeed, true);
			this.moveSpeed = moveSpeed;
		}

		@Override
		public boolean canUse() {
			return getTarget() != null && getTarget().isAlive();
		}

		@Override
		public void start() {
			setAggressive(true);
			delayCounter = 0;
		}

		@Override
		public void tick() {
			LivingEntity livingentity = getTarget();
			if (livingentity == null)
				return;

			lookControl.setLookAt(livingentity, 30.0F, 30.0F);

			if (--delayCounter <= 0) {
				delayCounter = 4 + getRandom().nextInt(7);
				getNavigation().moveTo(livingentity, moveSpeed);
			}

			attackTimer = Math.max(attackTimer - 1, 0);
			checkAndPerformAttack(livingentity, distanceToSqr(livingentity.getX(),
					livingentity.getBoundingBox().minY, livingentity.getZ()));
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			if ((distToEnemySqr <= getAttackReachSqr(enemy)
					|| getBoundingBox().intersects(enemy.getBoundingBox()))
					&& attackTimer <= 0) {
				attackTimer = maxAttackTimer;
				doHurtTarget(enemy);
			}
		}

		@Override
		public void stop() {
			getNavigation().stop();
			if (getTarget() == null)
				setAggressive(false);
		}

		public AttackGoal setMaxAttackTick(int max) {
			maxAttackTimer = max;
			return this;
		}
	}

	class MeleeGoal extends Goal {
		public MeleeGoal() {
			setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return getTarget() != null && attackID == MELEE_ATTACK;
		}

		@Override
		public boolean canContinueToUse() {
			// animation tick
			return attackTimer < 15;
		}

		@Override
		public void start() {
			setAttackID(MELEE_ATTACK);
			setMeleeAttacking(true);
		}

		@Override
		public void tick() {
			if (getTarget() != null && getTarget().isAlive()) {
				getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
				if (attackTimer == 8) {
					playSound(ModSoundEvents.REDSTONE_GOLEM_ATTACK.get(),
							1, 1);
				}
				if (attackTimer == 9) {
					float attackKnockback = getAttackKnockback();
					LivingEntity attackTarget = getTarget();
					double ratioX = Mth.sin(
							getYRot() * ((float) Math.PI / 180F));
					double ratioZ = -Mth.cos(
							getYRot() * ((float) Math.PI / 180F));
					double knockbackReduction = 0.5D;
					attackTarget.hurt(damageSources().mobAttack(RedstoneGolemEntity.this),
							(float) getAttributeValue(Attributes.ATTACK_DAMAGE));
					forceKnockback(attackTarget, attackKnockback * 0.5F, ratioX, ratioZ,
							knockbackReduction);
					setDeltaMovement(getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
				}
			}

		}

		private void forceKnockback(LivingEntity attackTarget, float strength, double ratioX, double ratioZ,
				double knockbackResistanceReduction) {
			LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(attackTarget, strength, ratioX,
					ratioZ);
			if (event.isCanceled())
				return;
			strength = event.getStrength();
			ratioX = event.getRatioX();
			ratioZ = event.getRatioZ();
			strength = (float) ((double) strength
					* (1.0D - attackTarget.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)
							* knockbackResistanceReduction));
			if (!(strength <= 0.0F)) {
				attackTarget.hasImpulse = true;
				Vec3 vector3d = attackTarget.getDeltaMovement();
				Vec3 vector3d1 = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);
				attackTarget.setDeltaMovement(vector3d.x / 2.0D - vector3d1.x,
						attackTarget.onGround()
								? Math.min(0.4D, vector3d.y / 2.0D + (double) strength)
								: vector3d.y,
						vector3d.z / 2.0D - vector3d1.z);
			}
		}

		@Override
		public void stop() {
			setMeleeAttacking(false);
			setAttackID(0);
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}

	// MINES

	class SummonRedstoneMinesGoal extends Goal {
		static final int MINE_ATTACK_COOLDOWN = 10 * 20;

		SummonRedstoneMinesGoal() {
		}

		@Override
		public boolean canUse() {
			return canSummonMines();
		}

		@Override
		public boolean canContinueToUse() {
			return attackTimer < 100;
		}

		@Override
		public void start() {
			setSummoningMines(true);
			// Play the summoning sound
			setAttackID(MINE_ATTACK);
			playSound(ModSoundEvents.REDSTONE_GOLEM_SUMMON_MINES.get(), 1.5F, 1);
		}

		@Override
		public void tick() {
			getNavigation().stop();
			if (attackTimer == 12) {
				BlockPos centerPos = blockPosition();
				for (int i = 0; i < 14; i++) {
					double randomNearbyX = centerPos.getX()
							+ (random.nextGaussian() * 10.0D);
					double randomNearbyZ = centerPos.getZ()
							+ (random.nextGaussian() * 10.0D);
					int j = RedstoneMineEntity.LIFE_TIME + 4 * i;
					BlockPos randomBlockPos = new BlockPos((int) randomNearbyX, centerPos.getY(),
							(int) randomNearbyZ);
					createSpellEntity(randomBlockPos.getX(),
							randomBlockPos.getZ(), randomBlockPos.getY(),
							randomBlockPos.getY() + 1, j);

				}
			}
		}

		@Override
		public void stop() {
			setAttackID(0);
			mineAttackCooldown = MINE_ATTACK_COOLDOWN;
			setSummoningMines(false);
		}
	}

	private boolean canSummonMines() {
		return mineAttackCooldown <= 0
				&& getTarget() != null
				&& getTarget().isAlive()
				&& onGround()
				&& attackID == 0;
	}

	private void createSpellEntity(double x, double z, double minY, double maxY, int delay) {
		BlockPos blockpos = new BlockPos((int) x, (int) maxY, (int) z);
		boolean flag = false;
		double d0 = 0.0D;

		do {
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate = level().getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(level(), blockpos1, Direction.UP)) {
				if (!level().isEmptyBlock(blockpos)) {
					BlockState blockstate1 = level()
							.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1
							.getCollisionShape(level(), blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}

				flag = true;
				break;
			}

			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(minY) - 1);

		if (flag)
			level().addFreshEntity(new RedstoneMineEntity(level(), x, (double) blockpos.getY() + d0, z,
					delay, this));

	}

}
