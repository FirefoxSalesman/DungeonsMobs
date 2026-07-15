package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.chest;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit.FINAL_SHOUT;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.HealthAbilityEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.items.artifacts.beacon.AbstractBeaconItem;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonslibs.capabilities.timers.Timers;
import net.firefoxsalesman.dungeonslibs.capabilities.timers.TimersHelper;
import net.firefoxsalesman.dungeonslibs.integration.curios.CuriosIntegration;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactUseContext;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class FinalShoutEnchantment extends HealthAbilityEnchantment {

	public FinalShoutEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
	}

	@SubscribeEvent
	public static void onPlayerHurt(LivingDamageEvent event) {
		LivingEntity victim = event.getEntity();
		if (victim != null && victim.isAlive() && victim instanceof Player) {
			Player player = (Player) victim;
			float currentHealth = player.getHealth();
			float maxHealth = player.getMaxHealth();
			float damageDealt = event.getAmount();
			Timers timers = TimersHelper.getTimersCapability(player);
			if (currentHealth - damageDealt <= (0.25F * maxHealth)) {
				if (!ModEnchantmentHelper.hasEnchantment(player, FINAL_SHOUT.get()))
					return;
				if (timers != null && timers.getEnchantmentTimer(FINAL_SHOUT.get()) == 0) {
					int proc = 0;
					for (ItemStack is : CuriosIntegration.getArtifacts(player))
						if (is.getItem() instanceof ArtifactItem
								&& !(is.getItem() instanceof AbstractBeaconItem)) {
							InteractionResultHolder<ItemStack> procResult = ((ArtifactItem) is
									.getItem())
									.procArtifact(new ArtifactUseContext(player,
											InteractionHand.MAIN_HAND,
											new BlockHitResult(player
													.position(),
													Direction.UP,
													player.blockPosition(),
													false)));
							if (procResult.getResult().consumesAction()
									&& !player.level().isClientSide)
								ArtifactItem.triggerSynergy(player, is);
							proc++;
						}
					if (proc > 0) {
						timers.setEnchantmentTimer(FINAL_SHOUT.get(),
								240 - 40 * Math.min(EnchantmentHelper
										.getEnchantmentLevel(FINAL_SHOUT.get(),
												player),
										6));
					}
				} else if (timers != null && timers.getEnchantmentTimer(FINAL_SHOUT.get()) < 0) {
					timers.setEnchantmentTimer(FINAL_SHOUT.get(), 0);
				}
			}
		}
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof HealthAbilityEnchantment);
	}
}
