package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.WindcallerBlastProjectileEntity;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WindcallerTornadoEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
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
import java.util.function.Predicate;

import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class WindcallerEntity extends AbstractIllager implements GeoEntity, SpawnArmoredMob {

	public int liftAttackAnimationTick;
	public int liftAttackAnimationLength = 25;
	public int liftAttackAnimationActionPoint = 15;

	public int blastAttackAnimationTick;
	public int blastAttackAnimationLength = 32;
	public int blastAttackAnimationActionPoint = 20;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int soundLoopTick;

	public WindcallerEntity(Level world) {
		super(ModEntities.WINDCALLER.get(), world);
	}

	public WindcallerEntity(EntityType<? extends WindcallerEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new WindcallerEntity.BlastAttackGoal(this));
		this.goalSelector.addGoal(1, new WindcallerEntity.LiftAttackGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 5.0F, 1.2D, 1.15D));
		this.goalSelector.addGoal(3, new ApproachTargetGoal(this, 6, 1.1D, true));
		this.goalSelector.addGoal(4, new LookAtTargetGoal(this));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, Player.class, true))
				.setUnseenMemoryTicks(600));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false))
				.setUnseenMemoryTicks(600));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false)
				.setUnseenMemoryTicks(600));
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.liftAttackAnimationTick = liftAttackAnimationLength;
		} else if (p_28844_ == 11) {
			this.blastAttackAnimationTick = blastAttackAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	boolean steepDropBelow() {
		boolean blockBeneath = false;

		for (int i = 0; i < 4; i++) {
			if (!this.level()
					.getBlockState(new BlockPos(this.blockPosition().getX(),
							this.blockPosition().getY() - i, this.blockPosition().getZ()))
					.isAir()) {
				blockBeneath = true;
			}
		}

		return !this.level().isClientSide && blockBeneath == false;
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		return false;
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();

		if (this.getTarget() != null && this.distanceTo(this.getTarget()) > 4
				&& ((!this.onGround() && steepDropBelow())
						|| ((this.getTarget().getY() > this.getY() + 4
								|| this.getY() < this.getTarget().getY() - 2)
								&& this.distanceTo(this.getTarget()) > 4)
						|| this.distanceTo(this.getTarget()) > 10)) {
			if (this.getY() < this.getTarget().getY() + 4) {
				this.setDeltaMovement(0.0D, 0.05D, 0.0D);
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
					.scale(1.5D));
			this.move(MoverType.SELF, this.getDeltaMovement());

			this.getNavigation().stop();
			this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getTarget().getX(),
					this.getTarget().getEyeY(), this.getTarget().getZ()));
		}

		this.soundLoopTick++;

		if (this.soundLoopTick % 40 == 0) {
			this.playSound(ModSoundEvents.WINDCALLER_FLY_LOOP.get(), 0.75F, 1.0F);
		}
	}

	public void aiStep() {

		if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
		}

		if (this.level().isClientSide) {
			this.level().addParticle(ModParticleTypes.WIND.get(), this.getRandomX(0.1D),
					this.getY() + 0.05D, this.getRandomZ(0.1D),
					(this.random.nextDouble() - 0.5D) * 1.0D, 0.0,
					(this.random.nextDouble() - 0.5D) * 1.0D);
		}

		super.aiStep();
	}

	public void tickDownAnimTimers() {
		if (this.liftAttackAnimationTick > 0) {
			this.liftAttackAnimationTick--;
		}

		if (this.blastAttackAnimationTick > 0) {
			this.blastAttackAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.liftAttackAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("windcaller_lift",
							LoopType.LOOP));
		} else if (this.blastAttackAnimationTick > 10) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("windcaller_blast",
							LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(
					RawAnimation.begin().then("windcaller_fly", LoopType.LOOP));
		} else {
			if (this.isCelebrating()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("windcaller_celebrate",
								LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("windcaller_idle", LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(random, p_180481_1_);
		SpawnEquipmentHelper.equipMainhand(ModItems.WINDCALLER_STAFF.get().getDefaultInstance(), this);
		equipArmorSet(ModItems.WINDCALLER_ARMOR, this);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_,
			@Nullable CompoundTag p_213386_5_) {
		SpawnGroupData iLivingEntityData = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_,
				p_213386_4_, p_213386_5_);
		this.populateDefaultEquipmentSlots(this.getRandom(), p_213386_2_);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), p_213386_2_);
		return iLivingEntityData;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.FOLLOW_RANGE, 20D).add(Attributes.MAX_HEALTH, 30.0D);
	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity
				&& ((LivingEntity) entityIn).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.WINDCALLER_IDLE.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.WINDCALLER_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.WINDCALLER_HURT.get();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return ModSoundEvents.WINDCALLER_BLAST_VOCAL.get();
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.WINDCALLER_ARMOR;
	}

	class LiftAttackGoal extends Goal {
		public WindcallerEntity mob;
		@Nullable
		public LivingEntity target;

		private final Predicate<Entity> TORNADO = (p_33346_) -> {
			return p_33346_ instanceof WindcallerTornadoEntity;
		};

		public LiftAttackGoal(WindcallerEntity mob) {
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

			int nearbyTornadoes = 1;

			if (target != null) {
				nearbyTornadoes = mob.level()
						.getEntities(mob, target.getBoundingBox().inflate(5.0D), TORNADO)
						.size();
			}

			return target != null && target.onGround() && mob.random.nextInt(30) == 0
					&& mob.distanceTo(target) <= 16 && mob.distanceTo(target) > 3
					&& nearbyTornadoes <= 0 && mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WINDCALLER_LIFT_VOCAL.get(), 1.0F, mob.getVoicePitch());
			mob.liftAttackAnimationTick = mob.liftAttackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.liftAttackAnimationTick == mob.liftAttackAnimationActionPoint) {
				WindcallerTornadoEntity tornado = ModEntities.TORNADO.get().create(mob.level());
				tornado.moveTo(target.blockPosition(), 0, 0);
				tornado.playSound(ModSoundEvents.WINDCALLER_LIFT_WIND.get(), 1.5F, 1.0F);
				((ServerLevel) mob.level()).addFreshEntityWithPassengers(tornado);
			}
		}

		public boolean animationsUseable() {
			return mob.liftAttackAnimationTick <= 0;
		}

	}

	class BlastAttackGoal extends Goal {
		public WindcallerEntity mob;
		@Nullable
		public LivingEntity target;

		private final Predicate<Entity> TORNADO = (p_33346_) -> {
			return p_33346_ instanceof WindcallerTornadoEntity;
		};

		public BlastAttackGoal(WindcallerEntity mob) {
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

			int nearbyTornadoes = 1;

			if (target != null) {
				nearbyTornadoes = mob.level()
						.getEntities(mob, target.getBoundingBox().inflate(5.0D), TORNADO)
						.size();
			}

			return target != null && mob.random.nextInt(5) == 0
					&& (mob.distanceTo(target) <= 4 || (!target.onGround()
							&& mob.random.nextInt(10) == 0 && mob.distanceTo(target) < 7.5))
					&& nearbyTornadoes <= 0 && mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.WINDCALLER_BLAST_VOCAL.get(), 1.0F, mob.getVoicePitch());
			mob.blastAttackAnimationTick = mob.blastAttackAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 11);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.blastAttackAnimationTick == mob.blastAttackAnimationActionPoint) {
				double d1 = target.getX() - mob.getX();
				double d2 = target.getY(0.5D) - mob.getY(0.5D);
				double d3 = target.getZ() - mob.getZ();
				WindcallerBlastProjectileEntity smallfireballentity = new WindcallerBlastProjectileEntity(
						mob.level(), mob, d1, 0, d3);
				smallfireballentity.setPos(mob.getX(), mob.getY(0.25D), mob.getZ());
				mob.level().addFreshEntity(smallfireballentity);
				WindcallerTornadoEntity tornado = ModEntities.TORNADO.get().create(mob.level());
				tornado.moveTo(mob.blockPosition(), 0, 0);
				tornado.playSound(ModSoundEvents.WINDCALLER_BLAST_WIND.get(), 1.5F, 1.0F);
				tornado.setBlast(true);
				// mob.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(target.getX(),
				// target.getY(), target.getZ()));
				tornado.setYRot(-mob.yHeadRot - 90);
				((ServerLevel) mob.level()).addFreshEntityWithPassengers(tornado);
			}
		}

		public boolean animationsUseable() {
			return mob.blastAttackAnimationTick <= 0;
		}

	}
}
