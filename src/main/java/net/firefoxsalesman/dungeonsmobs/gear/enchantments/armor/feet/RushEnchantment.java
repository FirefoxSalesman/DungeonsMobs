package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.feet;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class RushEnchantment extends DungeonsEnchantment {

	public RushEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onDamage(LivingDamageEvent event) {
		LivingEntity livingEntity = event.getEntity();
		int rushLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RUSH.get(), livingEntity);
		if (rushLevel > 0 && !livingEntity.level().isClientSide) {
			MobEffectInstance speedBoost = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20,
					rushLevel - 1);
			livingEntity.addEffect(speedBoost);
		}
	}
}
