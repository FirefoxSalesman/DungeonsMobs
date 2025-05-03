package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.ConstructEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.AvoidBaseEntityGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.utils.GeomancyHelper;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;

import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class GeomancerEntity extends SpellcasterIllager implements GeoEntity, SpawnArmoredMob {

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int summonBombsAttackAnimationTick;
	public int summonBombsAttackAnimationLength = 35;
	public int summonBombsAttackAnimationActionPoint = 15;

	public int summonWallsAnimationTick;
	public int summonWallsAnimationLength = 35;
	public int summonWallsAnimationActionPoint = 20;

	public GeomancerEntity(EntityType<? extends SpellcasterIllager> type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Evoker.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FOLLOW_RANGE,
				30.0D);
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new GeomancerEntity.SummonPillarsGoal(this));
		// Custom goal to avoid Geomancer constructs
		this.goalSelector.addGoal(1, new AvoidBaseEntityGoal<>(this, ConstructEntity.class, 5.0F, 1.0D, 1.0D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, AbstractVillager.class, 3.0F, 1.2D, 1.15D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 3.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 3.0F, 1.3D, 1.15D));
		this.goalSelector.addGoal(3, new ApproachTargetGoal(this, 15, 1.0D, true));
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

	@Override
	public boolean isLeftHanded() {
		return false;
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.summonWallsAnimationTick = summonWallsAnimationLength;
		} else if (p_28844_ == 11) {
			this.summonBombsAttackAnimationTick = summonBombsAttackAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	public void baseTick() {
		super.baseTick();

		this.tickDownAnimTimers();
	}

	public void tickDownAnimTimers() {
		if (this.summonWallsAnimationTick >= 0) {
			this.summonWallsAnimationTick--;
		}

		if (this.summonBombsAttackAnimationTick >= 0) {
			this.summonBombsAttackAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.summonBombsAttackAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("geomancer_attack",
					LoopType.LOOP));
		} else if (this.summonWallsAnimationTick > 0) {
			event.getController().setAnimation(RawAnimation.begin().then("geomancer_summon",
					LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(
					RawAnimation.begin().then("geomancer_walk", LoopType.LOOP));
		} else {
			if (this.isCelebrating()) {
				event.getController().setAnimation(RawAnimation.begin()
						.then("geomancer_celebrate", LoopType.LOOP));
			} else {
				event.getController().setAnimation(RawAnimation.begin().then("geomancer_idle",
						LoopType.LOOP));
			}
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		SpawnEquipmentHelper.equipMainhand(ModItems.GEOMANCER_STAFF.get().getDefaultInstance(), this);
		equipArmorSet(ModItems.GEOMANCER_ARMOR, this);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		this.populateDefaultEquipmentSlots(this.getRandom(), difficultyIn);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), difficultyIn);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.GEOMANCER_ARMOR;
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
		return ModSoundEvents.GEOMANCER_IDLE.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.GEOMANCER_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.GEOMANCER_HURT.get();
	}

	@Override
	protected SoundEvent getCastingSoundEvent() {
		return ModSoundEvents.GEOMANCER_ATTACK.get();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return ModSoundEvents.GEOMANCER_IDLE.get();
	}

	@Override
	public IllagerArmPose getArmPose() {
		IllagerArmPose illagerArmPose = super.getArmPose();
		if (illagerArmPose == IllagerArmPose.CROSSED) {
			return IllagerArmPose.NEUTRAL;
		}
		return illagerArmPose;
	}

	class SummonPillarsGoal extends Goal {
		public GeomancerEntity mob;
		@Nullable
		public LivingEntity target;

		public int nextUseTime = 0;

		public SummonPillarsGoal(GeomancerEntity mob) {
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

			return target != null && mob.tickCount >= this.nextUseTime && mob.distanceTo(target) <= 20
					&& mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && animationsNotUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.GEOMANCER_PRE_ATTACK.get(), 1.0F, mob.getVoicePitch());
			if (mob.random.nextBoolean()) {
				mob.summonWallsAnimationTick = mob.summonWallsAnimationLength;
				mob.level().broadcastEntityEvent(mob, (byte) 4);
			} else {
				mob.summonBombsAttackAnimationTick = mob.summonBombsAttackAnimationLength;
				mob.level().broadcastEntityEvent(mob, (byte) 11);
			}
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			mob.getNavigation().stop();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.summonWallsAnimationTick == mob.summonWallsAnimationActionPoint) {
				mob.playSound(ModSoundEvents.GEOMANCER_ATTACK.get(), 1.0F, mob.getVoicePitch());
				int randomInt = mob.random.nextInt(3);

				if (randomInt == 0) {
					int[] rowToRemove = Util.getRandom(GeomancyHelper.CONFIG_1_ROWS,
							mob.getRandom());
					GeomancyHelper.summonAreaDenialTrap(mob, target,
							ModEntities.GEOMANCER_WALL.get(), rowToRemove);
				} else if (randomInt == 1) {
					GeomancyHelper.summonWallTrap(mob, target, ModEntities.GEOMANCER_WALL.get());
				} else {
					GeomancyHelper.summonRandomPillarsTrap(mob, target,
							ModEntities.GEOMANCER_WALL.get());
				}
			}

			if (target != null
					&& mob.summonBombsAttackAnimationTick == mob.summonBombsAttackAnimationActionPoint) {
				mob.playSound(ModSoundEvents.GEOMANCER_ATTACK.get(), 1.0F, mob.getVoicePitch());
				if (mob.getRandom().nextBoolean()) {
					GeomancyHelper.summonQuadOffensiveTrap(mob, target,
							ModEntities.GEOMANCER_BOMB.get());
				} else {
					boolean movingOnX = mob.random.nextBoolean();
					GeomancyHelper.summonOffensiveConstruct(mob, target,
							ModEntities.GEOMANCER_BOMB.get(),
							movingOnX ? (mob.random.nextBoolean() ? 2 : -2) : 0,
							!movingOnX ? (mob.random.nextBoolean() ? 2 : -2) : 0,
							Direction.NORTH);
				}
			}
		}

		@Override
		public void stop() {
			super.stop();
			this.nextUseTime = mob.tickCount + 100 + mob.random.nextInt(40);
		}

		public boolean animationsUseable() {
			return mob.summonWallsAnimationTick <= 0 || mob.summonBombsAttackAnimationTick <= 0;
		}

		public boolean animationsNotUseable() {
			return mob.summonWallsAnimationTick > 0 || mob.summonBombsAttackAnimationTick > 0;
		}
	}

}
