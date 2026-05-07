package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.gear.registry.ItemInit;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DiamondArmouredVindicatorEntity extends AbstractArmouredVindicatorEntity {

	public DiamondArmouredVindicatorEntity(EntityType<? extends DiamondArmouredVindicatorEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Vindicator.createAttributes().add(Attributes.ARMOR, 20).add(Attributes.ARMOR_TOUGHNESS, 8);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemInit.WHIRLWIND.get()));
	}
}
