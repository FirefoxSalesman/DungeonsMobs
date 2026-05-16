package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.utils.RangedAttackHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class CooldownShotEnchantment extends DungeonsEnchantment {

	public CooldownShotEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onCooldownBowFired(ArrowLooseEvent event) {
		LivingEntity livingEntity = event.getEntity();
		int charge = event.getCharge();
		ItemStack stack = event.getBow();
		if (livingEntity instanceof Player && !event.getLevel().isClientSide) {
			Player player = (Player) livingEntity;
			float arrowVelocity = RangedAttackHelper.getBowArrowVelocity(livingEntity, stack, charge);
			if (arrowVelocity >= 1.0F) {
				int cooldownShotLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.COOLDOWN_SHOT.get(), stack);
				if (cooldownShotLevel > 0) {
					double cooldownReduction = 0.5 * cooldownShotLevel;
					ArtifactItem.reduceArtifactCooldowns(player, cooldownReduction);
				}
			}
		}
	}
}
