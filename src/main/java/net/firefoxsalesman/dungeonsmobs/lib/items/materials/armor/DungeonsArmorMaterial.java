package net.firefoxsalesman.dungeonsmobs.lib.items.materials.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class DungeonsArmorMaterial implements ArmorMaterial {

	public static final Codec<ArmorMaterial> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(
					iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).getName()),
			Codec.INT.fieldOf("durability").forGetter(
					iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).durability),
			Codec.INT.listOf().fieldOf("damage_reduction_amounts").forGetter(
					iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).damageReductionAmounts),
			Codec.INT.fieldOf("enchantability")
					.forGetter(iArmorMaterial -> iArmorMaterial.getEnchantmentValue()),
			ResourceLocation.CODEC.fieldOf("repair_item").forGetter(
					iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).repairItemResourceLocation),
			ForgeRegistries.SOUND_EVENTS.getCodec().fieldOf("equip_sound").forGetter(
					iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).getEquipSound()),
			Codec.FLOAT.fieldOf("toughness").forGetter(iArmorMaterial -> iArmorMaterial.getToughness()),
			Codec.FLOAT.fieldOf("knockback_resistance")
					.forGetter(iArmorMaterial -> iArmorMaterial.getKnockbackResistance()),
			ArmorMaterialBaseType.CODEC.fieldOf("base_type")
					.forGetter(iArmorMaterial -> ((DungeonsArmorMaterial) iArmorMaterial).baseType))
			.apply(instance, DungeonsArmorMaterial::new));

	// Armor order: boots, leggings, chestplate, helmet
	private static final int[] BASE_DURABILITY_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final SoundEvent equipSound;
	private final int durability;
	private final int enchantability;
	private final ResourceLocation repairItemResourceLocation;
	private final LazyLoadedValue<Ingredient> repairItem;
	private final List<Integer> damageReductionAmounts;
	private final float toughness;
	private final float knockbackResistance;
	private final ArmorMaterialBaseType baseType;

	private DungeonsArmorMaterial(String name, int durability, List<Integer> damageReductionAmounts,
			int enchantability, ResourceLocation repairItemResourceLocation, SoundEvent equipSound,
			float toughness, float knockbackResistance, ArmorMaterialBaseType baseType) {
		this.name = name;
		this.equipSound = equipSound;
		this.durability = durability;
		this.enchantability = enchantability;
		this.repairItemResourceLocation = repairItemResourceLocation;
		if (ITEMS.containsKey(repairItemResourceLocation)) {
			Item item = ITEMS.getValue(repairItemResourceLocation);
			repairItem = new LazyLoadedValue<>(() -> Ingredient.of(item));
		} else {
			repairItem = new LazyLoadedValue<>(() -> Ingredient.of(Items.IRON_INGOT));
		}
		this.damageReductionAmounts = damageReductionAmounts;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.baseType = baseType;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairItem.get();
	}

	@Override
	public SoundEvent getEquipSound() {
		return equipSound;
	}

	@Override
	public float getToughness() {
		return toughness;
	}

	// getKnockbackResistance
	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}

	public ArmorMaterialBaseType getBaseType() {
		return baseType;
	}

	@Override
	public int getDurabilityForType(Type pType) {
		return BASE_DURABILITY_ARRAY[pType.getSlot().getIndex()] * durability;
	}

	@Override
	public int getDefenseForType(Type pType) {
		return damageReductionAmounts.get(pType.getSlot().getIndex());
	}
}
