package net.firefoxsalesman.dungeonsmobs.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public enum CustomArmorMaterial implements ArmorMaterial {
	PURE_NETHERITE("pure_netherite", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ARMOR_EQUIP_NETHERITE, 0.0F, 0.0F,
			() -> {
				return Ingredient.of(Tags.Items.INGOTS_NETHERITE);
			});

	private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] slotProtections;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	CustomArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
			SoundEvent sound, float toughness, float knockbackResistance,
			Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.slotProtections = slotProtections;
		this.enchantmentValue = enchantmentValue;
		this.sound = sound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
	}

	@Override
	public int getDurabilityForType(Type pType) {
		return HEALTH_PER_SLOT[pType.getSlot().getIndex()] * durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(Type pType) {
		return slotProtections[pType.getSlot().getIndex()];
	}

	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	public SoundEvent getEquipSound() {
		return sound;
	}

	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return name;
	}

	public float getToughness() {
		return toughness;
	}

	public float getKnockbackResistance() {
		return knockbackResistance;
	}
}
