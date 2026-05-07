package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import java.util.HashMap;
import java.util.Map;

import net.firefoxsalesman.dungeonsmobs.lib.client.KeyframeEntity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.level.Level;

public class AbstractArmouredVindicatorEntity extends Vindicator implements KeyframeEntity {

	public AbstractArmouredVindicatorEntity(EntityType<? extends AbstractArmouredVindicatorEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	public Map<String, AnimationState> getStates() {
		return new HashMap<>();
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}
}
