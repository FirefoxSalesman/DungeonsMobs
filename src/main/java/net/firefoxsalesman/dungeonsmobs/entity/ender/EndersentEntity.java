package net.firefoxsalesman.dungeonsmobs.entity.ender;

import net.firefoxsalesman.dungeonslibs.attribute.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EndersentEntity extends AbstractEndersentEntity {

	public EndersentEntity(EntityType<? extends AbstractEndersentEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
				.add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(AttributeRegistry.SUMMON_CAP.get(), 5);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new AbstractEndersentEntity.CreateWatchlingGoal(this));
		goalSelector.addGoal(2, new AbstractEndersentEntity.AttackGoal(EndersentEntity.this, 1.0D));
		goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		targetSelector.addGoal(1, new VanillaEnderlingEntity.FindPlayerGoal(this, null));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, VanillaEnderlingEntity.class)
				.setAlertOthers().setUnseenMemoryTicks(500));
		targetSelector.addGoal(1,
				new EnderlingTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(500));
	}

}
