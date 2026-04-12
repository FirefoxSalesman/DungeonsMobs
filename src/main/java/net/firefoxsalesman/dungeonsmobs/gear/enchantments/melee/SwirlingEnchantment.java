package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.Combo;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.ComboHelper;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.AOEDamageEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DamageBoostEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class SwirlingEnchantment extends AOEDamageEnchantment {

	public static final float SWIRLING_DAMAGE_MULTIPLIER = 0.5F;

	public SwirlingEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onVanillaCriticalHit(CriticalHitEvent event) {
		if (event.getEntity() != null && !event.getEntity().level().isClientSide()
				&& event.getTarget() instanceof LivingEntity
				&& (event.getResult() == Event.Result.ALLOW
						|| (event.getResult() == Event.Result.DEFAULT
								&& event.isVanillaCritical()))) {
			Player attacker = event.getEntity();
			LivingEntity victim = (LivingEntity) event.getTarget();
			if (attacker.getLastHurtMobTimestamp() == attacker.tickCount)
				return;
			ItemStack mainhand = attacker.getMainHandItem();
			if (event.getResult() != Event.Result.ALLOW)
				return;
			if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.SWIRLING.get())) {
				int swirlingLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.SWIRLING.get(), mainhand);
				// gets the attack damage of the original attack before any enchantment
				// modifiers are added
				float attackDamage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
				Combo ic = ComboHelper.getComboCapability(attacker);
				float cooledAttackStrength = ic == null ? 1 : ic.getCachedCooldown();
				attackDamage *= 0.2F + cooledAttackStrength * cooledAttackStrength * 0.8F;

				float swirlingDamage = attackDamage * SWIRLING_DAMAGE_MULTIPLIER;
				swirlingDamage *= (swirlingLevel + 1) / 2.0F;
				SoundHelper.playAttackSweepSound(attacker);
				AOECloudHelper.spawnCritCloud(attacker, victim, 1.5f);
				AreaOfEffectHelper.causeSwirlingAttack(attacker, victim, swirlingDamage, 3f);
			}
		}
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof DamageEnchantment)
				&& !(enchantment instanceof DamageBoostEnchantment)
				&& !(enchantment instanceof AOEDamageEnchantment);
	}
}
