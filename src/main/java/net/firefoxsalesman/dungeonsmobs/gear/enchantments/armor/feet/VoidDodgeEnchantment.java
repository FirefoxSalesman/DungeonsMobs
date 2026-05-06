package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.feet;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit.VOID_DODGE;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.JumpingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ArmorEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class VoidDodgeEnchantment extends JumpingEnchantment {

	public VoidDodgeEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		LivingEntity victim = event.getEntity();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(VOID_DODGE.get(), victim);
		float totalTeleportChance = (float) (DungeonsGearConfig.VOID_DODGE_CHANCE_PER_LEVEL.get()
				* enchantmentLevel);

		float teleportRand = victim.getRandom().nextFloat();
		if (teleportRand <= totalTeleportChance) {
			ArmorEffectHelper.teleportOnHit(victim);
		}
	}

}
