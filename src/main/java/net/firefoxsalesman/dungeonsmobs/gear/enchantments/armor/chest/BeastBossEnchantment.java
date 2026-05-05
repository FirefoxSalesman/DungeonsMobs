package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.BeastEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Minion;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.MinionMasterHelper;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class BeastBossEnchantment extends BeastEnchantment {
	public BeastBossEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onBeastDamage(LivingDamageEvent event) {
		LivingEntity target = event.getEntity();
		DamageSource source = event.getSource();
		Entity trueSource = source.getEntity();
		if (trueSource == null)
			return;

		if (trueSource.level() instanceof ServerLevel
				&& MinionMasterHelper.isMinionEntity(trueSource)) {
			Minion attackerSummonableCap = MinionMasterHelper.getMinionCapability(trueSource);

			LivingEntity beastOwner = attackerSummonableCap.getMaster();
			if (beastOwner != null) {
				if (!MinionMasterHelper.isMinionOf(target, beastOwner)) {
					int beastBossLevel = EnchantmentHelper.getEnchantmentLevel(
							EnchantmentInit.BEAST_BOSS.get(), beastOwner);
					if (beastBossLevel > 0) {
						float beastBossFactor = (float) (DungeonsGearConfig.BEAST_BOSS_BASE_MULTIPLIER
								.get()
								+ (DungeonsGearConfig.BEAST_BOSS_MULTIPLIER_PER_LEVEL
										.get() * beastBossLevel));
						float currentDamage = event.getAmount();
						float newDamage = currentDamage * beastBossFactor;
						event.setAmount(newDamage);
					}
				}
			}
		}
	}

}
