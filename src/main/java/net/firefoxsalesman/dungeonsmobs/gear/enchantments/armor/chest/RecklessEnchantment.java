package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import java.util.UUID;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class RecklessEnchantment extends DungeonsEnchantment {
	private static final UUID RECKLESS = UUID.fromString("c131aecf-3b88-43c9-9df3-16f791077d41");

	public RecklessEnchantment() {
		super(Rarity.COMMON, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEquipmentChangeEvent event) {
		// no handsies!
		if (event.getSlot().getType() == EquipmentSlot.Type.HAND)
			return;
		LivingEntity livingEntity = event.getEntity();
		int levelFrom = event.getFrom().getEnchantmentLevel(EnchantmentInit.RECKLESS.get());
		int levelTo = event.getTo().getEnchantmentLevel(EnchantmentInit.RECKLESS.get());
		if (levelFrom == levelTo)
			return;
		int highestEquippedLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RECKLESS.get(),
				livingEntity);
		// equipping armor with reckless
		if (levelTo > 0) {
			// remove any existing modifiers
			livingEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(RECKLESS);
			livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RECKLESS);

			// add the modifiers and update health
			livingEntity.getAttribute(Attributes.MAX_HEALTH)
					.addTransientModifier(new AttributeModifier(RECKLESS, "reckless multiplier",
							DungeonsGearConfig.RECKLESS_MAX_HEALTH_MULTIPLIER.get(),
							AttributeModifier.Operation.MULTIPLY_BASE));
			if (livingEntity.getHealth() > livingEntity.getMaxHealth()) {
				livingEntity.setHealth(livingEntity.getMaxHealth());
			}
			livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(
					RECKLESS, "reckless multiplier",
					DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER.get()
							+ (DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL
									.get() * highestEquippedLevel),
					AttributeModifier.Operation.MULTIPLY_BASE));
		}
		// removing reckless armor
		else {
			// remove the modifiers and regain health lost from reckless
			AttributeModifier existingRecklessHealthMod = livingEntity.getAttribute(Attributes.MAX_HEALTH)
					.getModifier(RECKLESS);
			if (existingRecklessHealthMod != null) {
				livingEntity.getAttribute(Attributes.MAX_HEALTH)
						.removeModifier(existingRecklessHealthMod);
				if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
					float regainedHealth = Math.abs(livingEntity.getMaxHealth()
							* (float) existingRecklessHealthMod.getAmount());
					livingEntity.setHealth(livingEntity.getHealth() + regainedHealth);
				}
			}
			livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RECKLESS);

		}
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
