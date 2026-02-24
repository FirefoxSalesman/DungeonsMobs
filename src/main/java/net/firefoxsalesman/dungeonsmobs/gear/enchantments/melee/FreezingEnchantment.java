package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;

import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.FREEZING_DURATION;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class FreezingEnchantment extends DungeonsEnchantment {

	public FreezingEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return enchantment != Enchantments.FIRE_ASPECT;
	}

	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level) {
		if (!(target instanceof LivingEntity))
			return;
		applyFreezing((LivingEntity) target, level);
	}

	private static void applyFreezing(LivingEntity target, int level) {
		MobEffectInstance freezing = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
				FREEZING_DURATION.get(), level - 1);
		MobEffectInstance miningFatigue = new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
				FREEZING_DURATION.get(), level - 1);
		target.addEffect(freezing);
		target.addEffect(miningFatigue);
		PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
	}
}
