package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.RADIANCE_CHANCE;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.HealingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class RadianceEnchantment extends DungeonsEnchantment {

	public RadianceEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof HealingEnchantment);
	}

	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level) {
		if (!(target instanceof LivingEntity))
			return;
		float chance = user.getRandom().nextFloat();
		if (chance <= RADIANCE_CHANCE.get()) {
			AOECloudHelper.spawnRegenCloud(user, level);
		}
	}
}
