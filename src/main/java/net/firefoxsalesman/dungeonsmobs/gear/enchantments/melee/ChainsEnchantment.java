package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.CHAINS_CHANCE;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ChainsEnchantment extends DungeonsEnchantment {

	public ChainsEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level) {
		if (!(target instanceof LivingEntity))
			return;
		float chance = user.getRandom().nextFloat();
		if (chance <= CHAINS_CHANCE.get()) {
			AreaOfEffectHelper.chainNearbyEntities(user, (LivingEntity) target, 1.5F, level);
		}
	}
}
