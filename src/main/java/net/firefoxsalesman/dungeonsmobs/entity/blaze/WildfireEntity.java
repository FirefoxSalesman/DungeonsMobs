package net.firefoxsalesman.dungeonsmobs.entity.blaze;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Master;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.MinionMasterHelper;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.lib.summon.SummonHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Scoreboard;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SUMMON_CAP;
import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class WildfireEntity extends Monster implements GeoEntity, SpawnArmoredMob {

	private static final EntityDataAccessor<Integer> SHIELDS = SynchedEntityData.defineId(WildfireEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SHIELD_HEALTH = SynchedEntityData.defineId(WildfireEntity.class,
			EntityDataSerializers.FLOAT);
	private static final Predicate<Entity> NO_BLAZE_AND_ALIVE = (p_213685_0_) -> {
		return p_213685_0_.isAlive() && !(p_213685_0_ instanceof Blaze)
				&& !(p_213685_0_ instanceof WildfireEntity);
	};
	public int shootAnimationTick;
	public int shootAnimationLength = 12;
	public int shootAnimationActionPoint = 5;
	public int shockwaveAnimationTick;
	public int shockwaveAnimationLength = 27;
	public int shockwaveAnimationActionPoint = 9;
	public int summonAnimationTick;
	public int summonAnimationLength = 47;
	public int summonAnimationActionPoint = 15;
	public int soundLoopTick;
	public int regenerateShieldTick;
	public int regenerateShieldTime = 150;
	public float individualShieldHealth = 15.0F;
	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public WildfireEntity(EntityType<? extends WildfireEntity> type, Level world) {
		super(type, world);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.xpReward = 25;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 24D).add(Attributes.MAX_HEALTH, 50.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(SUMMON_CAP.get(), 6D);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new WildfireEntity.ShockwaveAttackGoal(this));
		this.goalSelector.addGoal(2, new WildfireEntity.SummonBlazesGoal(this));
		this.goalSelector.addGoal(3, new WildfireEntity.ShootAttackGoal(this));
		this.goalSelector.addGoal(4, new ApproachTargetGoal(this, 10, 1.2D, true));
		this.goalSelector.addGoal(5, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, Player.class, true))
				.setUnseenMemoryTicks(600));
	}

	private void shockwave() {
		if (this.isAlive()) {
			for (Entity entity : level().getEntitiesOfClass(LivingEntity.class,
					this.getBoundingBox().inflate(5.0D), NO_BLAZE_AND_ALIVE)) {
				entity.hurt(damageSources().mobAttack(this), 7.0F);
				entity.setSecondsOnFire(3);

				this.strongKnockback(entity);
			}
		}

	}

	private void strongKnockback(Entity p_213688_1_) {
		double d0 = p_213688_1_.getX() - this.getX();
		double d1 = p_213688_1_.getZ() - this.getZ();
		double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
		p_213688_1_.push(d0 / d2 * 3.0D, 0.2D, d1 / d2 * 3.0D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, SpawnGroupData p_213386_4_, CompoundTag p_213386_5_) {
		this.setShieldHealth(individualShieldHealth * 4);
		this.setShields(4);
		this.populateDefaultEquipmentSlots(this.getRandom(), p_213386_2_);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), p_213386_2_);
		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(random, p_180481_1_);
		equipArmorSet(ModItems.NETHERPLATE_ARMOR, this);
	}

	public int getShields() {
		return Mth.clamp(this.entityData.get(SHIELDS), 0, 4);
	}

	public void setShields(int p_191997_1_) {
		this.entityData.set(SHIELDS, p_191997_1_);
	}

	public float getShieldHealth() {
		return this.entityData.get(SHIELD_HEALTH);
	}

	public void setShieldHealth(float p_191997_1_) {
		this.entityData.set(SHIELD_HEALTH, p_191997_1_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHIELDS, 0);
		this.entityData.define(SHIELD_HEALTH, 0.0F);
	}

	public void addAdditionalSaveData(CompoundTag p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putInt("Shields", this.getShields());
		p_213281_1_.putFloat("ShieldHealth", this.getShieldHealth());
	}

	public void readAdditionalSaveData(CompoundTag p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		this.setShields(p_70037_1_.getInt("Shields"));
		this.setShieldHealth(p_70037_1_.getFloat("ShieldHealth"));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WILDFIRE_IDLE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return this.getShields() > 0 && p_184601_1_ != damageSources().outOfBorder()
				&& p_184601_1_ != damageSources().drown() ? SoundEvents.BLAZE_HURT
						: ModSoundEvents.WILDFIRE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WILDFIRE_DEATH.get();
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(ModSoundEvents.WILDFIRE_MOVE.get(), 0.15F, 1.0F);
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.shootAnimationTick = shootAnimationLength;
		} else if (p_28844_ == 11) {
			this.shockwaveAnimationTick = shockwaveAnimationLength;
		} else if (p_28844_ == 9) {
			this.summonAnimationTick = summonAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	@Override
	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (this.getShields() > 0 && p_70097_1_ != damageSources().outOfBorder()
				&& p_70097_1_ != damageSources().drown()) {

			if (p_70097_1_.getEntity() != null && p_70097_1_.getEntity() instanceof LivingEntity) {
				this.setLastHurtByMob(((LivingEntity) p_70097_1_.getEntity()));
			}

			this.setShieldHealth(this.getShieldHealth() - p_70097_2_);

			if (this.getShieldHealth() < individualShieldHealth * 3 && this.getShields() > 3 ||
					this.getShieldHealth() < individualShieldHealth * 2 && this.getShields() > 2 ||
					this.getShieldHealth() < individualShieldHealth * 1 && this.getShields() > 1 ||
					this.getShieldHealth() <= individualShieldHealth * 0 && this.getShields() > 0) {
				this.breakShield();
			} else {
				this.playHurtSound(p_70097_1_);
			}

			return false;
		} else {
			return super.hurt(p_70097_1_, p_70097_2_);
		}
	}

	boolean steepDropBelow() {
		boolean blockBeneath = false;

		for (int i = 0; i < 8; i++) {
			if (!level().getBlockState(new BlockPos(this.blockPosition().getX(),
					this.blockPosition().getY() - i, this.blockPosition().getZ())).isAir()) {
				blockBeneath = true;
			}
		}

		return !level().isClientSide && blockBeneath == false;
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();

		if (this.getShields() < 4 && this.regenerateShieldTick > 0) {
			this.regenerateShieldTick--;
			if (this.regenerateShieldTick == 0) {
				this.regenerateShield();
			}
		}

		if (this.getShields() > 0 && this.getShieldHealth() <= 0) {
			this.breakShield();
		}

		if (this.getTarget() != null && ((!this.onGround() && steepDropBelow())
				|| this.getTarget().getY() > this.getY() + 3 || this.getY() < this.getTarget().getY()
				|| this.distanceTo(this.getTarget()) > 15)) {
			if (this.getY() < this.getTarget().getY() + 5) {
				this.setDeltaMovement(0.0D, 0.04D, 0.0D);
			} else {
				this.setDeltaMovement(0.0D, -0.01D, 0.0D);
			}

			double x = this.getTarget().getX() - this.getX();
			double y = this.getTarget().getY() - this.getY();
			double z = this.getTarget().getZ() - this.getZ();
			double d = Math.sqrt(x * x + y * y + z * z);
			this.setDeltaMovement(this.getDeltaMovement()
					.add(x / d * 0.20000000298023224D, y / d * 0.20000000298023224D,
							z / d * 0.20000000298023224D)
					.scale(0.4D));
			this.move(MoverType.SELF, this.getDeltaMovement());

			this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getTarget().getX(),
					this.getTarget().getEyeY(), this.getTarget().getZ()));
		}

		this.soundLoopTick++;

		if (this.soundLoopTick % 100 == 0) {
			this.playSound(ModSoundEvents.WILDFIRE_IDLE_LOOP.get(), 0.5F, 1.0F);
		}
	}

	public void aiStep() {

		if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.3D, 1.0D));
		}

		if (level().isClientSide) {
			for (int i = 0; i < 2; ++i) {
				level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(),
						this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
			}
		}

		super.aiStep();
	}

	public void breakShield() {
		this.regenerateShieldTick = this.regenerateShieldTime;
		this.setShields(this.getShields() - 1);
		this.playSound(ModSoundEvents.WILDFIRE_SHIELD_BREAK.get(), 1.0F, 1.0F);
	}

	public void regenerateShield() {
		this.setShields(this.getShields() + 1);
		this.setShieldHealth(this.getShieldHealth() + individualShieldHealth);
		this.playSound(ModSoundEvents.WILDFIRE_SHOOT.get(), 1.0F, 1.0F);
		this.regenerateShieldTick = this.regenerateShieldTime;
	}

	public void tickDownAnimTimers() {
		if (this.shootAnimationTick > 0) {
			this.shootAnimationTick--;
		}

		if (this.shockwaveAnimationTick > 0) {
			this.shockwaveAnimationTick--;
		}

		if (this.summonAnimationTick > 0) {
			this.summonAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.shockwaveAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("wildfire_shockwave", LoopType.LOOP));
		} else if (this.summonAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("wildfire_summon", LoopType.LOOP));
		} else if (this.shootAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("wildfire_shoot", LoopType.LOOP));
		} else {
			event.getController().setAnimation(RawAnimation.begin().then("wildfire_idle", LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.NETHERPLATE_ARMOR;
	}

	class SummonBlazesGoal extends Goal {
		public WildfireEntity mob;
		@Nullable
		public LivingEntity target;

		public int blazeSummonRange = 5;
		public int closeBlazeSummonRange = 2;

		public SummonBlazesGoal(WildfireEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
			this.mob = mob;
			this.target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();
			Master master = MinionMasterHelper.getMasterCapability(mob);
			List<Entity> summons = master.getSummonedMobs();
			AttributeInstance attribute = mob.getAttribute(SUMMON_CAP.get());

			return target != null && mob.random.nextInt((80 * (summons.size() + 1))) == 0
					&& attribute != null && master.getSummonedMobsCost() < attribute.getValue()
					&& mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WILDFIRE_MOVE.get(), 1.0F, mob.getVoicePitch());
			mob.summonAnimationTick = mob.summonAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 9);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.summonAnimationTick == mob.summonAnimationActionPoint) {
				for (int i = 0; i < 1 + mob.random.nextInt(1); i++) {
					SummonSpotEntity blazeSummonSpot = ModEntities.SUMMON_SPOT.get()
							.create(mob.level());
					blazeSummonSpot.mobSpawnRotation = mob.random.nextInt(360);
					blazeSummonSpot.setSummonType(1);
					BlockPos summonPos = mob.blockPosition().offset(
							-blazeSummonRange + mob.random
									.nextInt((blazeSummonRange * 2) + 1),
							0, -blazeSummonRange + mob.random
									.nextInt((blazeSummonRange * 2) + 1));
					blazeSummonSpot.moveTo(summonPos, 0.0F, 0.0F);

					// RELOCATES BLAZE CLOSER TO WILDFIRE IF SPAWNED IN A POSITION THAT MAY HINDER
					// ITS ABILITY TO JOIN IN THE BATTLE
					if (blazeSummonSpot.isInWall() || !canSee(blazeSummonSpot, target)) {
						summonPos = mob.blockPosition().offset(
								-closeBlazeSummonRange + mob.random.nextInt(
										(closeBlazeSummonRange * 2) + 1),
								0, -closeBlazeSummonRange + mob.random.nextInt(
										(closeBlazeSummonRange * 2) + 1));
					}

					// RELOCATES BLAZE TO WILDFIRE'S POSITION IF STILL IN A POSITION THAT MAY HINDER
					// ITS ABILITY TO JOIN IN THE BATTLE
					if (blazeSummonSpot.isInWall() || !canSee(blazeSummonSpot, target)) {
						summonPos = mob.blockPosition();
					}
					((ServerLevel) mob.level()).addFreshEntityWithPassengers(blazeSummonSpot);
					PositionUtils.moveToCorrectHeight(blazeSummonSpot);

					EntityType<?> entityType = EntityType.BLAZE;

					Mob summonedMob = null;

					Entity entity = SummonHelper.summonEntity(mob, blazeSummonSpot.blockPosition(),
							entityType);

					if (entity == null) {
						blazeSummonSpot.remove(RemovalReason.DISCARDED);
						return;
					}

					if (entity instanceof Mob) {
						summonedMob = ((Mob) entity);
					}

					summonedMob.setTarget(target);
					summonedMob.finalizeSpawn(((ServerLevel) mob.level()),
							mob.level().getCurrentDifficultyAt(summonPos),
							MobSpawnType.MOB_SUMMONED, null, null);
					blazeSummonSpot.playSound(ModSoundEvents.NECROMANCER_SUMMON.get(), 1.0F, 1.0F);
					if (mob.getTeam() != null) {
						Scoreboard scoreboard = mob.level().getScoreboard();
						scoreboard.addPlayerToTeam(summonedMob.getScoreboardName(),
								scoreboard.getPlayerTeam(mob.getTeam().getName()));
					}
					blazeSummonSpot.summonedEntity = summonedMob;
				}
			}
		}

		public boolean animationsUseable() {
			return mob.summonAnimationTick <= 0;
		}

		public boolean canSee(Entity entitySeeing, Entity p_70685_1_) {
			Vec3 vector3d = new Vec3(entitySeeing.getX(), entitySeeing.getEyeY(), entitySeeing.getZ());
			Vec3 vector3d1 = new Vec3(p_70685_1_.getX(), p_70685_1_.getEyeY(), p_70685_1_.getZ());
			if (p_70685_1_.level() != entitySeeing.level()
					|| vector3d1.distanceToSqr(vector3d) > 128.0D * 128.0D)
				return false; // Forge Backport MC-209819
			return entitySeeing.level()
					.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER,
							ClipContext.Fluid.NONE, entitySeeing))
					.getType() == HitResult.Type.MISS;
		}

	}

	class ShockwaveAttackGoal extends Goal {
		public WildfireEntity mob;
		@Nullable
		public LivingEntity target;

		public ShockwaveAttackGoal(WildfireEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
			this.mob = mob;
			this.target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();
			return target != null && mob.onGround() && mob.random.nextInt(20) == 0
					&& mob.distanceTo(target) < 4 && mob.hasLineOfSight(target)
					&& animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WILDFIRE_SHOCKWAVE.get(), 1.0F, mob.getVoicePitch());
			mob.shockwaveAnimationTick = mob.shockwaveAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 11);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.shockwaveAnimationTick == mob.shockwaveAnimationActionPoint) {
				mob.shockwave();
			}
		}

		public boolean animationsUseable() {
			return mob.shockwaveAnimationTick <= 0;
		}

	}

	class ShootAttackGoal extends Goal {
		public WildfireEntity mob;
		@Nullable
		public LivingEntity target;

		public ShootAttackGoal(WildfireEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
			this.mob = mob;
			this.target = mob.getTarget();
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			target = mob.getTarget();
			return target != null && mob.random.nextInt(15) == 0 && mob.distanceTo(target) > 4
					&& mob.distanceTo(target) < 13 && mob.hasLineOfSight(target)
					&& animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.shootAnimationTick = mob.shootAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.shootAnimationTick == mob.shootAnimationActionPoint) {
				double d1 = target.getX() - mob.getX();
				double d2 = target.getY(0.5D) - mob.getY(0.75D);
				double d3 = target.getZ() - mob.getZ();
				SmallFireball smallfireballentity = new SmallFireball(mob.level(), mob, d1, d2, d3);
				smallfireballentity.setPos(smallfireballentity.getX(), mob.getY(0.5D) + 0.5D,
						smallfireballentity.getZ());
				mob.level().addFreshEntity(smallfireballentity);
				mob.playSound(ModSoundEvents.WILDFIRE_SHOOT.get(), 1.0F, 1.0F);
			}
		}

		public boolean animationsUseable() {
			return mob.shootAnimationTick <= 0;
		}

	}

}
