package net.firefoxsalesman.dungeonsmobs.entity.ender;

import static net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig.COMMON;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Our attack goal borrows from Goety, because I am not smart enough to figure
 * how to synchronize it on my own
 */
public class EndersentEntity extends AbstractEndersentEntity {
	private final ServerBossEvent bossEvent = COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()
			? (ServerBossEvent) (new ServerBossEvent(getDisplayName(),
					BossEvent.BossBarColor.PURPLE,
					BossEvent.BossBarOverlay.PROGRESS)).setCreateWorldFog(true)
					.setPlayBossMusic(true)
			: null;

	public EndersentEntity(EntityType<? extends EndersentEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
				.add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.ATTACK_DAMAGE, 20.0D).add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(AttributeRegistry.SUMMON_CAP.get(), 5);
	}

	protected boolean teleport() {
		return false;
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new AbstractEndersentEntity.CreateWatchlingGoal(this));
		goalSelector.addGoal(2, new AbstractEndersentEntity.AttackGoal(EndersentEntity.this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		targetSelector.addGoal(1, new VanillaEnderlingEntity.FindPlayerGoal(this, null));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, VanillaEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(1,
				new EnderlingTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));
	}

	public void readAdditionalSaveData(CompoundTag p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (hasCustomName() && COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setName(getDisplayName());
		}

	}

	public void setCustomName(@Nullable Component p_200203_1_) {
		super.setCustomName(p_200203_1_);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setName(getDisplayName());
		}
	}

	public void baseTick() {
		super.baseTick();
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.setProgress(getHealth() / getMaxHealth());
		}
	}

	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.addPlayer(player);
		}
	}

	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		if (COMMON.ENABLE_ENDERSENT_BOSS_BAR.get()) {
			bossEvent.removePlayer(player);
		}
	}
}
