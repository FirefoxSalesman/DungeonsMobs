package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.feet;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.JumpingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class DodgeEnchantment extends JumpingEnchantment {

	public DodgeEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		LivingEntity victim = event.getEntity();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DODGE.get(), victim);
		float negateHitChance = (float) (DungeonsGearConfig.DODGE_CHANCE_PER_LEVEL.get() * enchantmentLevel);

		float negateHitRand = victim.getRandom().nextFloat();
		if (negateHitRand <= negateHitChance) {
			event.setCanceled(true);
		}
	}

}
