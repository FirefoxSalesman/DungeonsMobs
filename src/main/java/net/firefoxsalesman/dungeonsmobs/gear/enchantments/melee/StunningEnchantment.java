package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig.STUNNING_CHANCE_PER_LEVEL;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.MobEffectInit;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class StunningEnchantment extends DungeonsEnchantment {

	public StunningEnchantment() {
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
		if (chance <= level * STUNNING_CHANCE_PER_LEVEL.get()) {
			MobEffectInstance stunned = new MobEffectInstance(MobEffectInit.STUNNED.get(), 60);
			MobEffectInstance nausea = new MobEffectInstance(MobEffects.CONFUSION, 60);
			MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5);
			((LivingEntity) target).addEffect(stunned);
			((LivingEntity) target).addEffect(nausea);
			((LivingEntity) target).addEffect(slowness);
		}
	}
}
