package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.ModSoundEvents;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.firefoxsalesman.dungeonsmobs.goals.ApproachTargetGoal;
import net.firefoxsalesman.dungeonsmobs.goals.LookAtTargetGoal;
import net.firefoxsalesman.dungeonsmobs.lib.entities.SpawnArmoredMob;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
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
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

import static net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper.equipArmorSet;

public class IceologerEntity extends AbstractIllager implements GeoEntity, SpawnArmoredMob {

	public int summonAnimationTick;
	public int summonAnimationLength = 60;
	public int summonAnimationActionPoint = 40;

	AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public IceologerEntity(Level world) {
		super(ModEntities.ICEOLOGER.get(), world);
	}

	public IceologerEntity(EntityType<? extends IceologerEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new IceologerEntity.SummonIceChunkGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractVillager.class, 3.0F, 1.2D, 1.15D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 3.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, IronGolem.class, 3.0F, 1.3D, 1.15D));
		this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 10, 1.0D, true));
		this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
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
			this.summonAnimationTick = summonAnimationLength;
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}

	public void baseTick() {
		super.baseTick();
		this.tickDownAnimTimers();
	}

	public void tickDownAnimTimers() {
		if (this.summonAnimationTick > 0) {
			this.summonAnimationTick--;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 2, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.summonAnimationTick > 0) {
			event.getController()
					.setAnimation(RawAnimation.begin().then("iceologer_summon", LoopType.LOOP));
		} else if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
			event.getController().setAnimation(RawAnimation.begin().then("iceologer_walk", LoopType.LOOP));
		} else {
			if (this.isCelebrating()) {
				event.getController().setAnimation(
						RawAnimation.begin().then("iceologer_celebrate", LoopType.LOOP));
			} else {
				event.getController().setAnimation(
						RawAnimation.begin().then("iceologer_idle", LoopType.LOOP));
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
		equipArmorSet(ModItems.ICEOLOGER_ARMOR, this);
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
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 18D).add(Attributes.MAX_HEALTH, 20.0D);
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
		return ModSoundEvents.ICEOLOGER_IDLE.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ICEOLOGER_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ICEOLOGER_HURT.get();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return ModSoundEvents.ICEOLOGER_ATTACK.get();
	}

	@Override
	public ArmorSet getArmorSet() {
		return ModItems.ICEOLOGER_ARMOR;
	}

	class SummonIceChunkGoal extends Goal {
		public IceologerEntity mob;
		@Nullable
		public LivingEntity target;

		private final Predicate<Entity> ICE_CHUNK = (p_33346_) -> {
			return p_33346_ instanceof IceCloudEntity && ((IceCloudEntity) p_33346_).owner != null
					&& ((IceCloudEntity) p_33346_).owner == mob;
		};

		public SummonIceChunkGoal(IceologerEntity mob) {
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
			int nearbyChunks = mob.level().getEntities(mob, mob.getBoundingBox().inflate(20.0D), ICE_CHUNK)
					.size();

			return target != null && mob.random.nextInt(20) == 0 && mob.distanceTo(target) <= 12
					&& nearbyChunks <= 0 && mob.hasLineOfSight(target) && animationsUseable();
		}

		@Override
		public boolean canContinueToUse() {
			return target != null && !animationsUseable();
		}

		@Override
		public void start() {
			mob.playSound(ModSoundEvents.ICEOLOGER_ATTACK.get(), 1.0F, mob.getVoicePitch());
			mob.summonAnimationTick = mob.summonAnimationLength;
			mob.level().broadcastEntityEvent(mob, (byte) 4);
		}

		@Override
		public void tick() {
			target = mob.getTarget();

			if (target != null) {
				mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			}

			if (target != null && mob.summonAnimationTick == mob.summonAnimationActionPoint) {
				IceCloudEntity.spawn(mob, target);
			}
		}

		public boolean animationsUseable() {
			return mob.summonAnimationTick <= 0;
		}

	}
}
