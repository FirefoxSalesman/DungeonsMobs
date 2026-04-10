package net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DungeonsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ProjectileEffectHelper;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ArrowHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ChainReactionEnchantment extends DungeonsEnchantment {

	public static final String INTRINSIC_CHAIN_REACTION_TAG = "IntrinsicChainReaction";

	public ChainReactionEnchantment() {
		super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	@SubscribeEvent
	public static void onChainReactionDamage(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		if (ArrowHelper.wasHitByArrow(source)) {
			AbstractArrow arrowEntity = (AbstractArrow) source.getDirectEntity();

			LivingEntity victim = event.getEntity();
			if (source.getEntity() instanceof LivingEntity) {
				if (!(source.getEntity() instanceof LivingEntity))
					return;
				LivingEntity attacker = (LivingEntity) source.getEntity();
				int chainReactionLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity,
						EnchantmentInit.CHAIN_REACTION.get());
				if (chainReactionLevel > 0) {
					float chainReactionChance = chainReactionLevel * 0.1f;
					float chainReactionRand = attacker.getRandom().nextFloat();
					if (chainReactionRand <= chainReactionChance) {
						ProjectileEffectHelper.fireChainReactionProjectiles(
								victim.getCommandSenderWorld(), attacker,
								victim, 3.15F, 1.0F, arrowEntity);
					}
				}
				if (arrowEntity.getTags().contains(INTRINSIC_CHAIN_REACTION_TAG)) {
					float chainReactionRand = attacker.getRandom().nextFloat();
					if (chainReactionRand <= 0.1F) {
						ProjectileEffectHelper.fireChainReactionProjectiles(
								victim.getCommandSenderWorld(), attacker,
								victim, 3.15F, 1.0F, arrowEntity);
					}
				}
			}
		}

	}

	public int getMaxLevel() {
		return 3;
	}
}
