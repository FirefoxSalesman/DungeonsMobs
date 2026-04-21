package net.firefoxsalesman.dungeonsmobs.goals;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.SummonSpotEntity;
import net.firefoxsalesman.dungeonsmobs.lib.summon.SummonHelper;
import net.firefoxsalesman.dungeonsmobs.utils.PositionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.phys.HitResult;

public abstract class AbstractSummonGoal<T extends Mob> extends Goal {

	public T mob;
	@Nullable
	public LivingEntity target;

	public int nextUseTime;

	public AbstractSummonGoal(T mob) {
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
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
		return target != null && mob.tickCount >= this.nextUseTime && animationsUseable()
				&& mob.hasLineOfSight(target);
	}

	@Override
	public boolean canContinueToUse() {
		return target != null && !animationsUseable();
	}

	@Override
	public void start() {
		if (getSummonSound().isPresent())
			mob.playSound(getSummonSound().get(), 1.0F, 1.0F);
		setSummonTick(getSummonLength());
		mob.level().broadcastEntityEvent(mob, (byte) 9);
	}

	@Override
	public void tick() {
		target = mob.getTarget();

		this.mob.getNavigation().stop();

		if (target != null && tickCondition()) {
			SummonSpotEntity mobSummonSpot = ModEntities.SUMMON_SPOT.get().create(mob.level());
			mobSummonSpot.mobSpawnRotation = mob.getRandom().nextInt(360);
			mobSummonSpot.setSummonType(2);
			BlockPos summonPos = mob.blockPosition().offset(
					-mobSummonRange() + mob.getRandom().nextInt((mobSummonRange() * 2) + 1), 0,
					-mobSummonRange() + mob.getRandom().nextInt((mobSummonRange() * 2) + 1));
			mobSummonSpot.moveTo(summonPos, 0.0F, 0.0F);

			// RELOCATES SUMMONED MOB CLOSER TO NECROMANCER IF SPAWNED IN A POSITION THAT
			// MAY HINDER ITS ABILITY TO JOIN IN THE BATTLE
			if (mobSummonSpot.isInWall() || !canSee(mobSummonSpot, target)) {
				summonPos = mob.blockPosition().offset(
						-closeMobSummonRange() + mob.getRandom()
								.nextInt((closeMobSummonRange() * 2) + 1),
						0, -closeMobSummonRange() + mob.getRandom()
								.nextInt((closeMobSummonRange() * 2) + 1));
			}

			// RELOCATES SUMMONED MOB TO NECROMANCER'S POSITION IF STILL IN A POSITION THAT
			// MAY HINDER ITS ABILITY TO JOIN IN THE BATTLE
			if (mobSummonSpot.isInWall() || !canSee(mobSummonSpot, target)) {
				summonPos = mob.blockPosition();
			}
			((ServerLevel) mob.level()).addFreshEntityWithPassengers(mobSummonSpot);
			PositionUtils.moveToCorrectHeight(mobSummonSpot);

			EntityType<?> entityType = getEntityType();

			Mob summonedMob = null;

			Entity entity = SummonHelper.summonEntity(mob, mobSummonSpot.blockPosition(),
					entityType);

			if (entity == null) {
				mobSummonSpot.remove(RemovalReason.DISCARDED);
				return;
			}

			if (entity instanceof Mob) {
				summonedMob = ((Mob) entity);
			}

			summonedMob.setTarget(target);
			summonedMob.finalizeSpawn(((ServerLevel) mob.level()),
					mob.level().getCurrentDifficultyAt(summonPos),
					MobSpawnType.MOB_SUMMONED, null, null);
			if (getSummonSound().isPresent())
				mobSummonSpot.playSound(getSummonSound().get(), 1.0F, 1.0F);
			if (mob.getTeam() != null) {
				Scoreboard scoreboard = mob.level().getScoreboard();
				scoreboard.addPlayerToTeam(summonedMob.getScoreboardName(),
						scoreboard.getPlayerTeam(mob.getTeam().getName()));
			}
			mobSummonSpot.summonedEntity = summonedMob;
		}
	}

	private EntityType<?> getEntityType() {
		EntityType<?> entityType = null;
		List<String> necromancerMobSummons = getSummonList();
		if (!necromancerMobSummons.isEmpty()) {
			Collections.shuffle(necromancerMobSummons);

			int randomIndex = mob.getRandom().nextInt(necromancerMobSummons.size());
			String randomMobID = necromancerMobSummons.get(randomIndex);
			entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(randomMobID));
		}
		if (entityType == null) {
			entityType = getBackupEntityType();
		}
		return entityType;
	}

	@Override
	public void stop() {
		this.nextUseTime = mob.tickCount + (200 + mob.getRandom().nextInt(200));
	}

	public boolean animationsUseable() {
		return getSummonTick() <= 0;
	}

	private boolean canSee(Entity entitySeeing, Entity pEntity) {
		Vec3 vector3d = new Vec3(entitySeeing.getX(), entitySeeing.getEyeY(), entitySeeing.getZ());
		Vec3 vector3d1 = new Vec3(pEntity.getX(), pEntity.getEyeY(), pEntity.getZ());
		if (pEntity.level() != entitySeeing.level()
				|| vector3d1.distanceToSqr(vector3d) > 128.0D * 128.0D)
			return false; // Forge Backport MC-209819
		return entitySeeing.level()
				.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER,
						ClipContext.Fluid.NONE, entitySeeing))
				.getType() == HitResult.Type.MISS;
	}

	protected abstract int closeMobSummonRange();

	protected abstract int mobSummonRange();

	protected abstract Optional<SoundEvent> getSummonSound();

	protected abstract void setSummonTick(int tick);

	protected abstract int getSummonTick();

	protected abstract int getSummonLength();

	protected abstract boolean tickCondition();

	protected abstract List<String> getSummonList();

	protected abstract EntityType<?> getBackupEntityType();
}
