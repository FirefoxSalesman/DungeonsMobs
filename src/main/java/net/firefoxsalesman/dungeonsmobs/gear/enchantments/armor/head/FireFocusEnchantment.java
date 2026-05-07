package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.head;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.FocusEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class FireFocusEnchantment extends FocusEnchantment {

	public FireFocusEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onFireAttack(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		if (!source.is(DamageTypeTags.IS_FIRE))
			return;
		if (source == event.getEntity().damageSources().onFire())
			return; // ON_FIRE is applied when you set something on fire
		if (event.getEntity().level().isClientSide)
			return;

		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			int fireFocusLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIRE_FOCUS.get(),
					attacker);
			if (fireFocusLevel > 0) {
				float multiplier = 1 + (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get()
						* fireFocusLevel);
				float currentDamage = event.getAmount();
				event.setAmount(currentDamage * multiplier);
			}
		}
	}
}
