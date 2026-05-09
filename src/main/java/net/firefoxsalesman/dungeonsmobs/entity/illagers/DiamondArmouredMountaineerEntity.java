package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class DiamondArmouredMountaineerEntity extends MountaineerEntity {
	public DiamondArmouredMountaineerEntity(EntityType<? extends DiamondArmouredMountaineerEntity> entityType,
			Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return MountaineerEntity.setCustomAttributes().add(Attributes.ARMOR, 20).add(Attributes.ARMOR_TOUGHNESS,
				8);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		SpawnEquipmentHelper.equipMainhand(ModItems.DIAMOND_MOUNTAINEER_AXE.get().getDefaultInstance(), this);
	}
}
