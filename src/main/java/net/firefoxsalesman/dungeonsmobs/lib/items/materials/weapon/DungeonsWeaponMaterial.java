package net.firefoxsalesman.dungeonsmobs.lib.items.materials.weapon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class DungeonsWeaponMaterial implements Tier {

    public static final Codec<Tier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(iItemTier -> ((DungeonsWeaponMaterial) iItemTier).getName()),
            SoundEvent.CODEC.fieldOf("equip_sound").forGetter(iItemTier -> ((DungeonsWeaponMaterial) iItemTier).getEquipSound()),
            Codec.INT.fieldOf("durability").forGetter(iItemTier -> iItemTier.getUses()),
            Codec.INT.fieldOf("enchantability").forGetter(iItemTier -> iItemTier.getEnchantmentValue()),
            ResourceLocation.CODEC.fieldOf("repair_item").forGetter(iItemTier -> ((DungeonsWeaponMaterial) iItemTier).getRepairItemResourceLocation()),
            Codec.FLOAT.fieldOf("attack_speed").forGetter(iItemTier -> iItemTier.getSpeed()),
            Codec.FLOAT.fieldOf("attack_damage").forGetter(iItemTier -> iItemTier.getAttackDamageBonus()),
            Codec.INT.fieldOf("level").forGetter(iItemTier -> ((DungeonsWeaponMaterial) iItemTier).getLevel())
    ).apply(instance, DungeonsWeaponMaterial::new));

    private final String name;
    private final Holder<SoundEvent> equipSound;
    private final int durability;
    private final int enchantability;
    private final ResourceLocation repairItemResourceLocation;
    private final Ingredient repairItem;
    private final float attackSpeed;
    private final float attackDamageBonus;
    private final int level;

    public DungeonsWeaponMaterial(String name, Holder<SoundEvent> equipSound, int durability, int enchantability, ResourceLocation repairItemResourceLocation, float attackSpeed, float attackDamageBonus, int level) {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItemResourceLocation = repairItemResourceLocation;
        if (ITEMS.containsKey(repairItemResourceLocation)) {
            Item item = ITEMS.getValue(repairItemResourceLocation);
            this.repairItem = Ingredient.of(item);
        } else {
            this.repairItem = Ingredient.of(Items.IRON_INGOT);
        }
        this.attackSpeed = attackSpeed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public Holder<SoundEvent> getEquipSound() {
        return equipSound;
    }

    @Override
    public int getUses() {
        return durability;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    public ResourceLocation getRepairItemResourceLocation() {
        return repairItemResourceLocation;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairItem;
    }

    @Override
    public float getSpeed() {
        return attackSpeed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }


}
