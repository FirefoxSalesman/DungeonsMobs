package net.firefoxsalesman.dungeonsmobs.entity.golem;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SquallGolemEntity extends Raider implements GeoEntity {
	private int attackTimer;
	private int attackID;
	public int cd;
	public static final byte STOMP_ATTACK = 1;
	public static final byte GOLEM_ACTIVATE = 2;
	public static final byte GOLEM_DEACTIVATE = 3;
	private int timeWithoutTarget;
	private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.defineId(SquallGolemEntity.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MELEEATTACKING = SynchedEntityData
			.defineId(SquallGolemEntity.class, EntityDataSerializers.BOOLEAN);

	public SquallGolemEntity(Level world) {
		super(ModEntities.SQUALL_GOLEM.get(), world);
	}

	public SquallGolemEntity(EntityType<? extends Raider> type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 90.0D) // >= Golem Health
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
				.add(Attributes.ATTACK_DAMAGE, 15.0D) // 1x Golem Attack
				.add(Attributes.ATTACK_KNOCKBACK, 1.25D); // 1x Ravager knockback
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	@Override
	public void registerControllers(ControllerRegistrar data) {
		data.add(
				new AnimationController<GeoAnimatable>(this, "controller", 10, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		Vec3 velocity = getDeltaMovement();
		float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		if (attackID == GOLEM_ACTIVATE) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(RawAnimation.begin().then("animation.squall_golem.activate",
					LoopType.PLAY_ONCE));
			return PlayState.CONTINUE;

		} else if (attackID == GOLEM_DEACTIVATE) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(RawAnimation.begin()
					.then("animation.squall_golem.deactivate", LoopType.PLAY_ONCE));
			return PlayState.CONTINUE;

		} else if (!getActivate()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.squall_golem.deactivated", LoopType.LOOP));
			return PlayState.CONTINUE;

		} else if (isMeleeAttacking() && isAlive()) {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.squall_golem.attack", LoopType.PLAY_ONCE));
			return PlayState.CONTINUE;

		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimationSpeed(groundSpeed * 25);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.squall_golem.walk", LoopType.LOOP));
			return PlayState.CONTINUE;

		} else {
			event.getController().setAnimationSpeed(1.0D);
			event.getController().setAnimation(
					RawAnimation.begin().then("animation.squall_golem.idle", LoopType.LOOP));
			return PlayState.CONTINUE;
		}
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(4, new SquallGolemEntity.AttackGoal());
		goalSelector.addGoal(0, new SquallGolemEntity.MeleeGoal());
		goalSelector.addGoal(1, new SquallGolemEntity.DoNothingGoal());
		goalSelector.addGoal(0, new SquallGolemEntity.Deactivate());
		goalSelector.addGoal(0, new SquallGolemEntity.Activate());
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6D));

		targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ACTIVATE, false);
		entityData.define(MELEEATTACKING, false);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("activate", getActivate());
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setActivate(compound.getBoolean("activate"));
	}

	public void setActivate(boolean isActivate) {
		entityData.set(ACTIVATE, isActivate);
	}

	public boolean getActivate() {
		return entityData.get(ACTIVATE);
	}

	public boolean isMeleeAttacking() {
		return entityData.get(MELEEATTACKING);
	}

	public void setMeleeAttacking(boolean attacking) {
		entityData.set(MELEEATTACKING, attacking);
	}

	public void aiStep() {
		super.aiStep();
		if (attackID != 0) {
			++attackTimer;
		}
		setDeltaMovement(SquallGolemEntity.this.getDeltaMovement().x,
				getDeltaMovement().y - 2.5,
				getDeltaMovement().z);
		handleLeafCollision();
		handleSteppingOnBlocks();
	}

	public void tick() {
		super.tick();

		if (attackID == STOMP_ATTACK) {
			if (attackTimer == 30) {
				Attackparticle(40, 0.5f, 2.6f, 0.5f);
				Attackparticle(40, 0.5f, 2.4f, -1f);
			}
		}
		LivingEntity target = getTarget();
		if (!level().isClientSide) {
			timeWithoutTarget++;
			if (target != null) {
				timeWithoutTarget = 0;
				if (!getActivate()) {
					setActivate(true);
					attackID = GOLEM_ACTIVATE;
				}
			}
			if (getCurrentRaid() != null && getCurrentRaid().isActive()) {
				timeWithoutTarget = 0;
				if (!getActivate()) {
					setActivate(true);
					attackID = GOLEM_ACTIVATE;
				}
			}

			if (timeWithoutTarget > 200 && getActivate() && target == null) {
				timeWithoutTarget = 0;
				setActivate(false);
				attackID = GOLEM_DEACTIVATE;

			}
		}

		if (cd > 0) {
			cd--;
		}

	}

	private void Attackparticle(int paticle, float circle, float vec, float math) {
		if (level().isClientSide) {
			for (int i1 = 0; i1 < paticle; i1++) {
				double DeltaMovementX = getRandom().nextGaussian() * 0.07D;
				double DeltaMovementY = getRandom().nextGaussian() * 0.07D;
				double DeltaMovementZ = getRandom().nextGaussian() * 0.07D;
				float angle = (0.01745329251F * yBodyRot) + i1;
				float f = Mth.cos(getYRot() * ((float) Math.PI / 180F));
				float f1 = Mth.sin(getYRot() * ((float) Math.PI / 180F));
				double extraX = circle * Mth.sin((float) (Math.PI + angle));
				double extraY = 0.3F;
				double extraZ = circle * Mth.cos(angle);
				double theta = (yBodyRot) * (Math.PI / 180);
				theta += Math.PI / 2;
				double vecX = Math.cos(theta);
				double vecZ = Math.sin(theta);
				int hitX = Mth.floor(getX() + vec * vecX + extraX);
				int hitY = Mth.floor(getY());
				int hitZ = Mth.floor(getZ() + vec * vecZ + extraZ);
				BlockPos hit = new BlockPos(hitX, hitY, hitZ);
				BlockState block = level().getBlockState(hit.below());
				level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block),
						getX() + vec * vecX + extraX + f * math, getY() + extraY,
						getZ() + vec * vecZ + extraZ + f1 * math, DeltaMovementX,
						DeltaMovementY, DeltaMovementZ);

			}
		}
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

	protected int decreaseAirSupply(int air) {
		return air;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		if (entityData.get(ACTIVATE) && noActionTime > 5) {
			return ModSoundEvents.SQUALL_GOLEM_IDLE.get();
		} else {
			return null;
		}
	}

	public boolean doHurtTarget(Entity entityIn) {
		if (!level().isClientSide && attackID == 0) {
			attackID = STOMP_ATTACK;
		}
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean flag = false;
		if (!getActivate() && source != damageSources().fellOutOfWorld()) {
			playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.4F);
			if (source.getEntity() instanceof LivingEntity && source.getEntity().isInvulnerable()) {
				setTarget((LivingEntity) source.getEntity());
			}
			flag = false;
		} else {
			flag = super.hurt(source, amount);
		}
		return flag;
	}

	public boolean checkSpawnObstruction(LevelReader worldIn) {
		BlockPos golemPos = blockPosition();
		BlockPos posBeneathGolem = golemPos.below();
		BlockState blockstateBeneathGolem = worldIn.getBlockState(posBeneathGolem);
		if (!blockstateBeneathGolem.entityCanStandOn(worldIn, posBeneathGolem, this)) {
			return false;
		} else {
			for (int i = 1; i < 3; ++i) {
				BlockPos posAboveGolem = golemPos.above(i);
				BlockState blockstateAboveGolem = worldIn.getBlockState(posAboveGolem);
				if (!NaturalSpawner
						.isValidEmptySpawnBlock(worldIn,
								posAboveGolem,
								blockstateAboveGolem,
								blockstateAboveGolem.getFluidState(),
								ModEntities.SQUALL_GOLEM.get())) {
					return false;
				}
			}

			return NaturalSpawner
					.isValidEmptySpawnBlock(worldIn,
							golemPos,
							worldIn.getBlockState(golemPos),
							Fluids.EMPTY.defaultFluidState(),
							ModEntities.SQUALL_GOLEM.get())
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

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(ModSoundEvents.SQUALL_GOLEM_WALK.get(), 1.12F, 1.0F);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.SQUALL_GOLEM_HURT.get();
	}

	@Override
	protected float getSoundVolume() {
		return 2.5F;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.SQUALL_GOLEM_DEATH.get();
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
		return ModSoundEvents.SQUALL_GOLEM_IDLE.get();
	}

	public boolean canBeLeader() {
		return false;
	}

	class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(SquallGolemEntity.this, 1.25D, false);
		}

		protected double getAttackReachSqr(LivingEntity p_179512_1_) {
			float f = getBbWidth() - 0.1F;
			return f * 1.8F * f * 1.8F + p_179512_1_.getBbWidth();
		}
	}

	class DoNothingGoal extends Goal {
		public DoNothingGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return !getActivate();
		}

		@Override
		public void tick() {
			setDeltaMovement(0, getDeltaMovement().y, 0);
		}
	}

	class Activate extends Goal {
		public Activate() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return attackID == GOLEM_ACTIVATE;
		}

		@Override
		public boolean canContinueToUse() {
			// animation tick
			return attackTimer < 60;
		}

		@Override
		public void start() {
			setAttackID(GOLEM_ACTIVATE);
			playSound(ModSoundEvents.SQUALL_GOLEM_OPEN.get(), 1.0F, 1F);
		}

		@Override
		public void tick() {
			setDeltaMovement(0, getDeltaMovement().y, 0);
		}

		@Override
		public void stop() {
			setAttackID(0);
		}
	}

	class Deactivate extends Goal {
		public Deactivate() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return attackID == GOLEM_DEACTIVATE;
		}

		@Override
		public boolean canContinueToUse() {
			// animation tick
			return attackTimer < 80;
		}

		@Override
		public void start() {
			setAttackID(GOLEM_DEACTIVATE);
			playSound(ModSoundEvents.SQUALL_GOLEM_OFF.get(), 1.0F, 1F);
		}

		@Override
		public void tick() {
			setDeltaMovement(0, getDeltaMovement().y, 0);
		}

		@Override
		public void stop() {
			setAttackID(0);
		}
	}

	class MeleeGoal extends Goal {
		public MeleeGoal() {
			setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return attackID == STOMP_ATTACK && (cd <= 0);
		}

		@Override
		public boolean canContinueToUse() {
			// animation tick
			return attackTimer < 34;
		}

		@Override
		public void start() {
			if (getTarget() != null)
				getLookControl().setLookAt(getTarget(),
						30, 30);
			setMeleeAttacking(true);
			setAttackID(STOMP_ATTACK);
		}

		@Override
		public void tick() {
			setDeltaMovement(0, getDeltaMovement().y, 0);
			LivingEntity target = getTarget();
			if (attackTimer < 15 && target != null) {
				lookAt(target, 15.0F, 15.0F);
			} else {
				setYRot(yRotO);
			}
			if (attackTimer == 12) {
				playSound(ModSoundEvents.SQUALL_GOLEM_ATTACK.get(), 2.0F, 1F);
			}
			if (attackTimer == 30) {
				AreaAttack(5, 5, 5, 5, 60, 1.0F);
			}
		}

		private void AreaAttack(float range, float X, float Y, float Z, float arc, float damage) {
			for (LivingEntity entityHit : level().getEntitiesOfClass(LivingEntity.class,
					getBoundingBox().inflate(X, Y, Z))) {
				float entityHitAngle = (float) ((Math.atan2(
						entityHit.getZ() - getZ(),
						entityHit.getX() - getX()) * (180 / Math.PI)
						- 90) % 360);
				float entityAttackingAngle = yBodyRot % 360;
				if (entityHitAngle < 0) {
					entityHitAngle += 360;
				}
				if (entityAttackingAngle < 0) {
					entityAttackingAngle += 360;
				}
				float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
				float entityHitDistance = (float) Math.sqrt((entityHit.getZ()
						- getZ())
						* (entityHit.getZ() - getZ())
						+ (entityHit.getX() - getX())
								* (entityHit.getX() - getX()));
				if (entityHitDistance <= range
						&& (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2)
						|| (entityRelativeAngle >= 360 - arc / 2
								|| entityRelativeAngle <= -360 + arc / 2)) {
					if (!isAlliedTo(entityHit) && !(entityHit == SquallGolemEntity.this)) {
						entityHit.hurt(damageSources().mobAttack(SquallGolemEntity.this),
								(float) SquallGolemEntity.this.getAttributeValue(
										Attributes.ATTACK_DAMAGE) * damage);

						SquallGolemEntity v = SquallGolemEntity.this;
						float attackKnockback = (float) getAttributeValue(Attributes.ATTACK_KNOCKBACK);
						double ratioX = Mth.sin(v.getYRot() * ((float) Math.PI / 180F));
						double ratioZ = -Mth.cos(v.getYRot() * ((float) Math.PI / 180F));
						double knockbackReduction = 0.35D;
						entityHit.hurt(damageSources().mobAttack(SquallGolemEntity.this), damage);
						forceKnockback(entityHit, attackKnockback * 0.8F, ratioX, ratioZ,
								knockbackReduction);
						entityHit.setDeltaMovement(
								entityHit.getDeltaMovement().add(0, 0.3333333, 0));
					}
				}
			}
		}

		@Override
		public void stop() {
			setMeleeAttacking(false);
			setAttackID(0);
			cd = 25;
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

	}

}
