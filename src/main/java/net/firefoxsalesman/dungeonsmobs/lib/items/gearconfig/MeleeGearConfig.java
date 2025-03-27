package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.firefoxsalesman.dungeonsmobs.lib.items.materials.weapon.WeaponMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.ArrayList;
import java.util.List;

import static net.firefoxsalesman.dungeonsmobs.lib.data.Codecs.ENCHANTMENT_DATA_CODEC;
import static net.firefoxsalesman.dungeonsmobs.lib.data.Codecs.ITEM_RARITY_CODEC;

public class MeleeGearConfig {

    public static final MeleeGearConfig DEFAULT = new MeleeGearConfig(new ArrayList<>(), new ArrayList<>(), new ResourceLocation("minecraft:iron"), false, false, false, false, Rarity.COMMON, 1);

    public static final Codec<MeleeGearConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GearConfigAttributeModifier.CODEC.listOf().optionalFieldOf("attributes", new ArrayList<>()).forGetter(MeleeGearConfig::getAttributes),
            ENCHANTMENT_DATA_CODEC.listOf().optionalFieldOf("built_in_enchantments", new ArrayList<>()).forGetter(MeleeGearConfig::getBuiltInEnchantments),
            ResourceLocation.CODEC.fieldOf("material").forGetter(armorGearConfig -> armorGearConfig.materialResource),
            Codec.BOOL.optionalFieldOf("disables_shield", false).forGetter(MeleeGearConfig::isDisablesShield),
            Codec.BOOL.optionalFieldOf("light", false).forGetter(MeleeGearConfig::isLight),
            Codec.BOOL.optionalFieldOf("two_handed", false).forGetter(MeleeGearConfig::isTwoHanded),
            Codec.BOOL.optionalFieldOf("unique", false).forGetter(MeleeGearConfig::isUnique),
            ITEM_RARITY_CODEC.fieldOf("rarity").forGetter(MeleeGearConfig::getRarity),
            Codec.INT.optionalFieldOf("combo", 1).forGetter(MeleeGearConfig::getComboLength)
    ).apply(instance, MeleeGearConfig::new));

    private final List<GearConfigAttributeModifier> attributes;
    private final List<EnchantmentInstance> builtInEnchantments;
    private final ResourceLocation materialResource;
    private final boolean disablesShield;
    private final boolean light;
    private final boolean twoHanded;
    private final boolean unique;
    private final Rarity rarity;
    private final int comboLength;

    public MeleeGearConfig(List<GearConfigAttributeModifier> attributes, List<EnchantmentInstance> builtInEnchantments, ResourceLocation materialResource, boolean disablesShield, boolean light, boolean twoHanded, boolean unique, Rarity rarity, int comboLength) {
        this.attributes = attributes;
        this.builtInEnchantments = builtInEnchantments;
        this.materialResource = materialResource;
        this.disablesShield = disablesShield;
        this.light = light;
        this.twoHanded = twoHanded;
        this.unique = unique;
        this.rarity = rarity;
        this.comboLength = comboLength;
    }

    public List<GearConfigAttributeModifier> getAttributes() {
        return attributes;
    }

    public List<EnchantmentInstance> getBuiltInEnchantments() {
        return builtInEnchantments;
    }

    public ResourceLocation getMaterialResource() {
        return materialResource;
    }

    public Tier getWeaponMaterial() {
        return WeaponMaterials.getWeaponMaterial(materialResource);
    }

    public boolean isDisablesShield() {
        return disablesShield;
    }

    public boolean isLight() {
        return light;
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }

    public boolean isUnique() {
        return unique;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getComboLength() {
        return comboLength;
    }
}
