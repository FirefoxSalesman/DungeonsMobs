package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.feet;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.firefoxsalesman.dungeonslibs.event.ArtifactEvent;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class SpeedSynergyEnchantment extends ArtifactEnchantment {

	public SpeedSynergyEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
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
		if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.SPEED_SYNERGY.get())) {
			int speedSynergyLevel = EnchantmentHelper
					.getEnchantmentLevel(EnchantmentInit.SPEED_SYNERGY.get(), livingEntity);
			MobEffectInstance speedBoost = new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
					20 * speedSynergyLevel);
			livingEntity.addEffect(speedBoost);
		}
	}
}
