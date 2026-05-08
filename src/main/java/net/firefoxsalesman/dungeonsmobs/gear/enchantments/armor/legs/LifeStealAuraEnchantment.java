package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit.LIFE_STEAL_AURA;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.applyToNearbyEntities;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.PulseEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.MobEffectInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.AbilityHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class LifeStealAuraEnchantment extends PulseEnchantment {

	public LifeStealAuraEnchantment() {
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

		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(LIFE_STEAL_AURA.get(), entity);
		if (enchantmentLevel > 0) {
			applyToNearbyEntities(entity, 5,
					(nearbyEntity) -> {
						return AbilityHelper.isAlly(entity, nearbyEntity);
					}, (LivingEntity nearbyEntity) -> {
						MobEffectInstance speedBoost = new MobEffectInstance(
								MobEffectInit.LIFE_STEAL.get(), 20,
								enchantmentLevel - 1);
						nearbyEntity.addEffect(speedBoost);
					});
		}
	}

}
