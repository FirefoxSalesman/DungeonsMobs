package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.PulseEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.Timers;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.timers.TimersHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class GravityPulseEnchantment extends PulseEnchantment {

	public GravityPulseEnchantment() {
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
		if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.GRAVITY_PULSE.get())) {
			Timers timers = TimersHelper.getTimersCapability(livingEntity);
			if (timers == null)
				return;
			int currentTimer = timers.getEnchantmentTimer(EnchantmentInit.GRAVITY_PULSE.get());
			if (currentTimer < 0) {
				timers.setEnchantmentTimer(EnchantmentInit.GRAVITY_PULSE.get(), 100);
			} else if (currentTimer == 0) {
				int enchantmentLevel = EnchantmentHelper
						.getEnchantmentLevel(EnchantmentInit.GRAVITY_PULSE.get(), livingEntity);
				AreaOfEffectHelper.pullInNearbyEntities(livingEntity, livingEntity,
						(float) (DungeonsGearConfig.GRAVITY_PULSE_BASE_STRENGTH.get()
								+ DungeonsGearConfig.GRAVITY_PULSE_STRENGTH_PER_LEVEL
										.get() * enchantmentLevel),
						ParticleTypes.PORTAL);
				timers.setEnchantmentTimer(EnchantmentInit.GRAVITY_PULSE.get(), 100);
			}
		}
	}
}
