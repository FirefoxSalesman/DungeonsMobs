package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.ArtifactEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.event.ArtifactEvent;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class HealthSynergyEnchantment extends ArtifactEnchantment {

	public HealthSynergyEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof ArtifactEnchantment);
	}

	@SubscribeEvent
	public static void onArtifactTriggered(ArtifactEvent.Activated event) {
		LivingEntity livingEntity = event.getEntity();
		if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.HEALTH_SYNERGY.get())) {
			int healthSynergyLevel = EnchantmentHelper
					.getEnchantmentLevel(EnchantmentInit.HEALTH_SYNERGY.get(), livingEntity);
			livingEntity.heal(0.2F + (0.1F * healthSynergyLevel));
		}
	}
}
