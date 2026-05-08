package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class AltruisticEnchantment extends DungeonsEnchantment {

	public AltruisticEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onDamage(LivingDamageEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.ALTRUISTIC.get())) {
			int altruisticLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ALTRUISTIC.get(),
					livingEntity);
			float damage = event.getAmount();
			float damageToHealingMultiplier = DungeonsGearConfig.ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL
					.get().floatValue() * altruisticLevel;
			AreaOfEffectHelper.healNearbyAllies(livingEntity, damage * damageToHealingMultiplier, 12);
		}
	}
}
