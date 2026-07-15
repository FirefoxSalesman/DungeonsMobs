package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.head;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.FocusEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class LightningFocusEnchantment extends FocusEnchantment {

	public LightningFocusEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onLightningAttack(LivingDamageEvent event) {
		if (!DamageSourceHelper.isSource(event.getSource(), event.getEntity().damageSources().lightningBolt()))
			return;
		if (event.getEntity().level().isClientSide)
			return;

		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			int lightningFocusLevel = EnchantmentHelper
					.getEnchantmentLevel(EnchantmentInit.LIGHTNING_FOCUS.get(), attacker);
			if (lightningFocusLevel > 0) {
				float multiplier = 1 + (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get()
						* lightningFocusLevel);
				float currentDamage = event.getAmount();
				event.setAmount(currentDamage * multiplier);
			}
		}
	}
}
