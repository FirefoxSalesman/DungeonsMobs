package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.Combo;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.ComboHelper;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.PlayerAttackHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PainCycleEnchantment extends DungeonsEnchantment {

	public PainCycleEnchantment() {
		super(Rarity.COMMON, ModEnchantmentTypes.MELEE, ModEnchantmentTypes.WEAPON_SLOT);
	}

	@SubscribeEvent
	public static void onPainfulAttack(LivingDamageEvent event) {
		if (PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource()))
			return;
		if (event.getEntity().level().isClientSide)
			return;

		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			ItemStack mainhand = attacker.getMainHandItem();
			if (attacker.getLastHurtMobTimestamp() == attacker.tickCount)
				return;
			Combo comboCap = ComboHelper.getComboCapability(attacker);
			int painCycleLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.PAIN_CYCLE.get(),
					mainhand);
			int painDamage = 2;
			if (painCycleLevel > 0 && attacker.getHealth() > painDamage) {
				attacker.hurt(attacker.damageSources().magic(), painDamage); // 1 heart of damage
				comboCap.setPainCycleStacks(comboCap.getPainCycleStacks() + 1);
				if (comboCap.getPainCycleStacks() >= 5) {
					int painCycleMultiplier = 2 + painCycleLevel;
					comboCap.setPainCycleStacks(0);
					float currentDamage = event.getAmount();
					event.setAmount(currentDamage * painCycleMultiplier);
				}
			} else {
				comboCap.setPainCycleStacks(0);
			}
		}
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
