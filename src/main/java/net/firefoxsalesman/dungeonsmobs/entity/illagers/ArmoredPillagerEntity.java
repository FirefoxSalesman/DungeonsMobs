package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import java.util.HashMap;
import java.util.Map;

import net.firefoxsalesman.dungeonslibs.client.KeyframeEntity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.level.Level;

public class ArmoredPillagerEntity extends Pillager implements KeyframeEntity {
	public ArmoredPillagerEntity(EntityType<? extends Pillager> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Pillager.createAttributes().add(Attributes.ARMOR, 12);
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
