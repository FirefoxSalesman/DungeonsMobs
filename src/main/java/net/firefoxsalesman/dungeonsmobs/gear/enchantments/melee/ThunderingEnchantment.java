package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.THUNDERING_BASE_DAMAGE;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.THUNDERING_CHANCE;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.AOEDamageEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ThunderingEnchantment extends AOEDamageEnchantment {

	public ThunderingEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 1;
	}

	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level) {
		if (!(target instanceof LivingEntity))
			return;
		float chance = user.getRandom().nextFloat();
		if (chance <= THUNDERING_CHANCE.get()) {
			SoundHelper.playLightningStrikeSounds(user);
			AreaOfEffectHelper.electrifyNearbyEnemies(user, 5, THUNDERING_BASE_DAMAGE.get(),
					Integer.MAX_VALUE);
		}
	}
}
