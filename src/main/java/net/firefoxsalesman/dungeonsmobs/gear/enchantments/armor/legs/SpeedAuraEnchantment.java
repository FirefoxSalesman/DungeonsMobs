package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.PulseEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonslibs.utils.AbilityHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class SpeedAuraEnchantment extends PulseEnchantment {

	public SpeedAuraEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof PulseEnchantment);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		if (ModEnchantmentHelper.canEnchantmentTrigger(event.getEntity())) {
			triggerEffect(event.getEntity());
		}
	}

	private static void triggerEffect(LivingEntity entity) {
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPEED_AURA.get(), entity);
		if (enchantmentLevel > 0) {
			applyToNearbyEntities(entity, 5,
					(nearbyEntity) -> {
						return AbilityHelper.isAlly(entity, nearbyEntity);
					}, (LivingEntity nearbyEntity) -> {
						MobEffectInstance speedBoost = new MobEffectInstance(
								MobEffects.MOVEMENT_SPEED, 20, enchantmentLevel - 1);
						nearbyEntity.addEffect(speedBoost);
					});
		}
	}

}
