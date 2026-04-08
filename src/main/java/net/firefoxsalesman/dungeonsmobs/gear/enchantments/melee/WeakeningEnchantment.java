package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.WEAKENING_DISTANCE;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.WEAKENING_DURATION;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.getCanApplyToSecondEnemyPredicate;
import static net.firefoxsalesman.dungeonsmobs.lib.utils.AreaOfEffectHelper.applyToNearbyEntities;;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class WeakeningEnchantment extends DungeonsEnchantment {

	public WeakeningEnchantment() {
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
		LivingEntity livingTarget = (LivingEntity) target;
		livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKENING_DURATION.get(), level - 1));
		applyToNearbyEntities(target, target.level(), WEAKENING_DISTANCE.get(),
				getCanApplyToSecondEnemyPredicate(user, livingTarget), (LivingEntity nearbyEntity) -> {
					nearbyEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
							WEAKENING_DURATION.get(), level - 1));
				});
	}
}
