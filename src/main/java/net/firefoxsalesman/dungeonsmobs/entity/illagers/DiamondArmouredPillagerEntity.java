package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DiamondArmouredPillagerEntity extends AbstractArmouredPillagerEntity {
	public DiamondArmouredPillagerEntity(EntityType<? extends AbstractArmouredPillagerEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Pillager.createAttributes().add(Attributes.ARMOR, 20).add(Attributes.ARMOR_TOUGHNESS, 8);
	}
}
