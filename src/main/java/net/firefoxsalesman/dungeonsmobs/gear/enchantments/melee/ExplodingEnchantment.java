package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.AOEDamageEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DamageBoostEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ExplodingEnchantment extends AOEDamageEnchantment {

	public ExplodingEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof DamageEnchantment)
				&& !(enchantment instanceof DamageBoostEnchantment)
				&& !(enchantment instanceof AOEDamageEnchantment);
	}

	@SubscribeEvent
	public static void onExplodingKill(LivingDeathEvent event) {
		if (event.getSource().getDirectEntity() instanceof AbstractArrow)
			return;
		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			LivingEntity victim = event.getEntity();
			ItemStack mainhand = attacker.getMainHandItem();
			if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.EXPLODING.get())) {
				int explodingLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.EXPLODING.get(), mainhand);
				float explosionDamage;
				explosionDamage = victim.getMaxHealth()
						* (float) (DungeonsGearConfig.EXPLODING_MULTIPLIER_PER_LEVEL.get()
								* explodingLevel);
				SoundHelper.playGenericExplodeSound(victim);
				AOECloudHelper.spawnExplosionCloud(attacker, victim, 3.0F);
				AreaOfEffectHelper.causeExplosionAttack(attacker, victim, explosionDamage, 3.0F);
			}
		}
	}

}
