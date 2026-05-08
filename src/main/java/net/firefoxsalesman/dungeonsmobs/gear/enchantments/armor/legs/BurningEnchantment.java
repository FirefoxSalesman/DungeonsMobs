package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit.BURNING;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.PulseEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.Timers;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.TimersHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class BurningEnchantment extends PulseEnchantment {

	public BurningEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
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

	public static void triggerEffect(LivingEntity livingEntity) {
		if (ModEnchantmentHelper.hasEnchantment(livingEntity, BURNING.get())) {
			Timers timers = TimersHelper.getTimersCapability(livingEntity);
			if (timers == null)
				return;
			int currentTimer = timers.getEnchantmentTimer(BURNING.get());
			if (currentTimer < 0) {
				timers.setEnchantmentTimer(BURNING.get(), 10);
			} else if (currentTimer == 0) {
				int enchantmentLevel = EnchantmentHelper
						.getEnchantmentLevel(BURNING.get(), livingEntity);
				AreaOfEffectHelper.burnNearbyEnemies(livingEntity, 1.0F * enchantmentLevel, 1.5F);
				timers.setEnchantmentTimer(BURNING.get(), 10);
			}
		}
	}

}
