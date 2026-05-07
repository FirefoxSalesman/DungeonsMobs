package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.head;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.FocusEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.utils.DamageSourceHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class PoisonFocusEnchantment extends FocusEnchantment {

	public PoisonFocusEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onPoisonAttack(LivingDamageEvent event) {
		if (DamageSourceHelper.isSource(event.getSource(), event.getEntity().damageSources().magic()))
			return; // Poison effect applies this specific damage source
		if (event.getEntity().level().isClientSide)
			return;

		LivingEntity victim = event.getEntity();
		if (victim.getEffect(MobEffects.POISON) != null) {
			LivingEntity attacker = victim.getKillCredit();
			if (attacker != null) {
				int poisonFocusLevel = EnchantmentHelper
						.getEnchantmentLevel(EnchantmentInit.POISON_FOCUS.get(), attacker);
				if (poisonFocusLevel > 0) {
					float multiplier = 1
							+ (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get()
									* poisonFocusLevel);
					float currentDamage = event.getAmount();
					event.setAmount(currentDamage * multiplier);
				}
			}
		}
	}
}
