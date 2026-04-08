package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.AOEDamageEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DamageBoostEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ShockwaveEnchantment extends AOEDamageEnchantment {

	public static final float SHOCKWAVE_DAMAGE_MULTIPLIER = 0.25F;

	public ShockwaveEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
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

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onVanillaCriticalHit(CriticalHitEvent event) {
		if (event.getEntity() != null && !event.getEntity().level().isClientSide()
				&& event.getTarget() instanceof LivingEntity
				&& (event.getResult() == Event.Result.ALLOW
						|| (event.getResult() == Event.Result.DEFAULT
								&& event.isVanillaCritical()))) {
			Player attacker = event.getEntity();
			LivingEntity victim = (LivingEntity) event.getTarget();
			ItemStack mainhand = attacker.getMainHandItem();
			if (attacker.getLastHurtMobTimestamp() == attacker.tickCount)
				return;
			if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.SHOCKWAVE.get())) {
				int shockwaveLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.SHOCKWAVE.get(), mainhand);
				float shockwaveDamage = 4;
				shockwaveDamage *= (shockwaveLevel + 1) / 2.0F;
				SoundHelper.playBoltImpactSound(attacker);
				AOECloudHelper.spawnCritCloud(attacker, victim, 3.0f);
				AreaOfEffectHelper.causeShockwave(attacker, victim, shockwaveDamage, 6.0f);
			}
		}
	}

}
