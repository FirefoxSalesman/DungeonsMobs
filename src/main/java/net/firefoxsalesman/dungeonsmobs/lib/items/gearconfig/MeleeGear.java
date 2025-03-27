package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IComboWeapon;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IMeleeWeapon;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IReloadableGear;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IUniqueGear;
import net.firefoxsalesman.dungeonsmobs.lib.utils.DescriptionHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.MojankHelper;
import net.firefoxsalesman.dungeonsmobs.mixin.ItemAccessor;
import net.firefoxsalesman.dungeonsmobs.mixin.TieredItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.block.material.Material;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE;
import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED;
import static net.minecraftforge.registries.ForgeRegistries.ATTRIBUTES;

public class MeleeGear extends TieredItem implements IMeleeWeapon, IComboWeapon, Vanishable, IReloadableGear, IUniqueGear {

    private Multimap<Attribute, AttributeModifier> defaultModifiers;
    private MeleeGearConfig meleeGearConfig;
    private float attackDamage;

    public MeleeGear(Item.Properties properties) {
        super(Tiers.WOOD, properties);
        reload();
    }

    @Override
    public void reload() {
        meleeGearConfig = MeleeGearConfigRegistry.getConfig(ForgeRegistries.ITEMS.getKey(this));
        ((TieredItemAccessor) this).setTier(meleeGearConfig.getWeaponMaterial());
        ((ItemAccessor) this).setMaxDamage(this.getTier().getUses());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        meleeGearConfig.getAttributes().forEach(attributeModifier -> {
            Attribute attribute = ATTRIBUTES.getValue(attributeModifier.getAttributeResourceLocation());
            if (attribute != null) {
                UUID uuid = randomUUID();
                if (ATTACK_DAMAGE.equals(attribute)) {
                    uuid = BASE_ATTACK_DAMAGE_UUID;
                    this.attackDamage = (float) attributeModifier.getAmount() + this.getTier().getAttackDamageBonus();
                } else if (ATTACK_SPEED.equals(attribute)) {
                    uuid = BASE_ATTACK_SPEED_UUID;
                }
                builder.put(attribute, new AttributeModifier(uuid, "Weapon modifier", attributeModifier.getAmount(), attributeModifier.getOperation()));
            }
        });
        this.defaultModifiers = builder.build();
    }

    public MeleeGearConfig getGearConfig() {
        return meleeGearConfig;
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return this.getGearConfig().getComboLength();
    }

    @Override
    public boolean isUnique() {
        return meleeGearConfig.isUnique();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return this.getGearConfig().isDisablesShield();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, MojankHelper::hurtEnemyBroadcastBreakEvent);
        return true;
    }

    public float getDamage() {
        return this.attackDamage;
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (blockState.getDestroySpeed(level, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, MojankHelper::hurtEnemyBroadcastBreakEvent);
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        return p_150897_1_.is(Blocks.COBWEB) || p_150897_1_.is(BlockTags.LEAVES);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState pBlockState) {
        if (pBlockState.is(Blocks.COBWEB) || pBlockState.is(BlockTags.LEAVES)) {
            return 15.0F;
        } else {
            // Material material = pBlockState.getMaterial();
            // return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !pBlockState.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;

	    MapColor m = pBlockState.getBlock().defaultMapColor();
	    PushReaction p = pBlockState.getPistonPushReaction();
	    return !(m.equals(MapColor.PLANT) && p.equals(PushReaction.DESTROY)) ? 1.0F : 1.5F;
        }
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return getGearConfig().getRarity();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category == EnchantmentCategory.WEAPON;
    }
}
