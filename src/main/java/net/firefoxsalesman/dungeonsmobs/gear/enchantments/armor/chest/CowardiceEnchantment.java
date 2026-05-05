package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.HealthAbilityEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CowardiceEnchantment extends HealthAbilityEnchantment {
	private static final UUID COWARD = UUID.fromString("86ded262-f5b3-41f9-a1ca-b881f6abfcff");

	public CowardiceEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		if (ModEnchantmentHelper.canEnchantmentTrigger(event.getEntity())) {
			triggerEffect(event.getEntity());
		}
	}

	private static void triggerEffect(LivingEntity livingEntity) {
		float maxHealth = livingEntity.getMaxHealth();
		float currentHealth = livingEntity.getHealth();
		AttributeInstance attackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
		AttributeInstance rangedDamage = livingEntity
				.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get());
		if (attackDamage != null) {
			attackDamage.removeModifier(COWARD);
		}
		if (rangedDamage != null) {
			rangedDamage.removeModifier(COWARD);
		}
		if (currentHealth >= maxHealth) {
			if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.COWARDICE.get())) {
				int cowardiceLevel = EnchantmentHelper
						.getEnchantmentLevel(EnchantmentInit.COWARDICE.get(), livingEntity);
				if (attackDamage != null) {
					livingEntity.getAttribute(Attributes.ATTACK_DAMAGE)
							.addTransientModifier(new AttributeModifier(COWARD,
									"cowardice multiplier",
									DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER
											.get()
											+ DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL
													.get()
													* cowardiceLevel,
									AttributeModifier.Operation.MULTIPLY_TOTAL));
				}
				if (rangedDamage != null) {
					livingEntity.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get())
							.addTransientModifier(new AttributeModifier(COWARD,
									"cowardice multiplier",
									DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER
											.get()
											+ DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL
													.get()
													* cowardiceLevel,
									AttributeModifier.Operation.MULTIPLY_TOTAL));
				}
			}
		}
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof HealthAbilityEnchantment);
	}
}
