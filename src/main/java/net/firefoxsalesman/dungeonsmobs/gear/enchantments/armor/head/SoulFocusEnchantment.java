package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.head;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.FocusEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.utils.DamageSourceHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class SoulFocusEnchantment extends FocusEnchantment {

	public SoulFocusEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onMagicAttack(LivingDamageEvent event) {
		if (!(event.getSource().isIndirect() && DamageSourceHelper.isSource(event.getSource(),
				event.getEntity().damageSources().magic())))
			return; // we don't want this to trigger via generic magic damage
		if (event.getEntity().level().isClientSide)
			return;

		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			int soulFocusLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOUL_FOCUS.get(),
					attacker);
			if (soulFocusLevel > 0) {
				float multiplier = 1 + (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get()
						* soulFocusLevel);
				float currentDamage = event.getAmount();
				event.setAmount(currentDamage * multiplier);
			}
		}
	}
}
