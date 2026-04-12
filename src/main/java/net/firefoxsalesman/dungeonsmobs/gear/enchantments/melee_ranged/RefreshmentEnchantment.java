package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.Combo;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.ComboHelper;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DropsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.PlayerAttackHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class RefreshmentEnchantment extends DropsEnchantment {

	public static final int REFRESHMENT_GOAL = 45;

	public RefreshmentEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	@SubscribeEvent
	public static void onRefreshmentKill(LivingDeathEvent event) {
		DamageSource damageSource = event.getSource();
		if (damageSource.getEntity() instanceof Player) {
			Player killerPlayer = (Player) damageSource.getEntity();
			if (!PlayerAttackHelper.isProbablyNotMeleeDamage(damageSource)) {
				ItemStack mainhand = killerPlayer.getMainHandItem();
				int refreshmentLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.REFRESHMENT.get(), mainhand);
				if (refreshmentLevel > 0) {
					updateRefreshment(killerPlayer, refreshmentLevel);
				}
			} else if (ArrowHelper.wasHitByArrow(damageSource)) {
				AbstractArrow arrowEntity = (AbstractArrow) damageSource.getDirectEntity();
				int refreshmentLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity,
						EnchantmentInit.REFRESHMENT.get());
				if (refreshmentLevel > 0) {
					updateRefreshment(killerPlayer, refreshmentLevel);
				}
			}
		}
	}

	private static void updateRefreshment(Player player, int refreshmentLevel) {
		Combo comboCap = ComboHelper.getComboCapability(player);
		if (refreshmentLevel <= 0)
			return;
		comboCap.setRefreshmentCounter(comboCap.getRefreshmentCounter() + refreshmentLevel);

		if (comboCap.getRefreshmentCounter() >= REFRESHMENT_GOAL) {
			Inventory playerInventory = player.getInventory();
			for (int slotId = 0; slotId < playerInventory.getContainerSize(); slotId++) {
				ItemStack currentStack = playerInventory.getItem(slotId);
				if (currentStack.getItem() instanceof BottleItem) {
					ItemStack healthPotion = PotionUtils.setPotion(new ItemStack(Items.POTION),
							Potions.HEALING);
					if (!player.getAbilities().instabuild)
						currentStack.shrink(1);
					if (!playerInventory.add(healthPotion))
						player.drop(healthPotion, false);
					comboCap.setRefreshmentCounter(
							comboCap.getRefreshmentCounter() - REFRESHMENT_GOAL);
					return;
				}
			}
		}
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof DropsEnchantment);
	}

}
