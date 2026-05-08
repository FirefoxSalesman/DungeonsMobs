package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.legs;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class PotionAuraEnchantment extends DungeonsEnchantment {

	public PotionAuraEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onPlayerDamaged(LivingEntityUseItemEvent.Finish event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (player.isAlive()) {
			List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
			if (potionEffects.isEmpty())
				return;
			MobEffectInstance effectInstance = potionEffects.get(0);
			if (effectInstance.getEffect() == MobEffects.HEAL) {
				if (ModEnchantmentHelper.hasEnchantment(player, EnchantmentInit.POTION_AURA.get())) {
					int potionBarrierLevel = EnchantmentHelper
							.getEnchantmentLevel(EnchantmentInit.POTION_AURA.get(), player);
					AreaOfEffectHelper.healNearbyAllies(player, effectInstance,
							6.0F * potionBarrierLevel);
				}
			}
		}
	}
}
