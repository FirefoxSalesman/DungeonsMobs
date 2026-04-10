package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.bow.RangedAbilities;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.bow.RangedAbilitiesHelper;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.event.BowEvent;
import net.firefoxsalesman.dungeonsmobs.lib.event.CrossbowEvent;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.BowGear;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.CrossbowGear;
import net.firefoxsalesman.dungeonsmobs.lib.utils.RangedAttackHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class AccelerateEnchantment extends DungeonsEnchantment {

	public AccelerateEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return enchantment != Enchantments.QUICK_CHARGE;
	}

	@SubscribeEvent
	public static void onAccelerateBowFired(ArrowLooseEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Level world = livingEntity.getCommandSenderWorld();
		long worldTime = world.getGameTime();
		int charge = event.getCharge();
		ItemStack stack = event.getBow();
		if (stack.getItem() instanceof BowItem) {
			RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(stack);
			long lastFiredTime = weaponCapability.getLastFiredTime();
			float dynamicChargeTime = weaponCapability.getDynamicChargeTime();

			int accelerateLevel = EnchantmentHelper
					.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), stack);

			if (accelerateLevel > 0) {
				float defaultChargeTime = 20.0F;
				float arrowVelocity = RangedAttackHelper.getBowArrowVelocity(livingEntity, stack,
						charge);
				if (stack.getItem() instanceof BowGear) {
					defaultChargeTime = ((BowGear) stack.getItem()).getDefaultChargeTime();
				}

				if ((lastFiredTime < worldTime - (Math.max(dynamicChargeTime, 0) + defaultChargeTime)
						&& dynamicChargeTime < defaultChargeTime)
						|| arrowVelocity < 1.0F) {
					weaponCapability.setDynamicChargeTime(defaultChargeTime);
				} else if (arrowVelocity >= 1.0F) {
					float fireRateReduction = (int) (defaultChargeTime
							* (0.04 + 0.04 * accelerateLevel));

					weaponCapability.setDynamicChargeTime(dynamicChargeTime - fireRateReduction);
				}
			}
			weaponCapability.setLastFiredTime(worldTime);
		}
	}

	@SubscribeEvent
	public static void onAccelerateCrossbowFired(PlayerInteractEvent.RightClickItem event) {
		Player playerEntity = event.getEntity();
		Level world = playerEntity.getCommandSenderWorld();
		long worldTime = world.getGameTime();
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof CrossbowItem & CrossbowItem.isCharged(stack)) {
			RangedAbilities weaponCap = RangedAbilitiesHelper.getRangedAbilitiesCapability(stack);
			long lastFiredTime = weaponCap.getLastFiredTime();
			int dynamicChargeTime = (int) weaponCap.getDynamicChargeTime();

			int accelerateLevel = EnchantmentHelper
					.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), stack);

			float defaultChargeTime = 25;
			if (stack.getItem() instanceof CrossbowGear) {
				defaultChargeTime = ((CrossbowGear) stack.getItem()).getDefaultChargeTime();
			}

			if (accelerateLevel > 0) {
				if (lastFiredTime < worldTime
						- (Math.max(dynamicChargeTime, 0) + (defaultChargeTime + 3))
						&& dynamicChargeTime < defaultChargeTime) {
					weaponCap.setDynamicChargeTime((int) defaultChargeTime);
				} else {
					int fireRateReduction = (int) (defaultChargeTime
							* (0.04 + 0.04 * accelerateLevel));

					weaponCap.setDynamicChargeTime(dynamicChargeTime - fireRateReduction);
				}
			}
			weaponCap.setLastFiredTime(worldTime);
		}
	}

	@SubscribeEvent
	public static void onBowChargeTime(BowEvent.ChargeTime event) {
		ItemStack itemStack = event.getItemStack();
		int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(),
				itemStack);
		RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(itemStack);
		float dynamicChargeTime = weaponCapability.getDynamicChargeTime();
		if (accelerateLevel > 0 && weaponCapability.getLastFiredTime() > 0) {
			event.setChargeTime(dynamicChargeTime);
		}
	}

	@SubscribeEvent
	public static void onCrossbowChargeTime(CrossbowEvent.ChargeTime event) {
		ItemStack itemStack = event.getItemStack();
		int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(),
				itemStack);
		RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(itemStack);
		float dynamicChargeTime = weaponCapability.getDynamicChargeTime();
		if (accelerateLevel > 0 && weaponCapability.getLastFiredTime() > 0) {
			event.setChargeTime(dynamicChargeTime);
		}
	}

}
