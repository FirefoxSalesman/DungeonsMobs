package net.firefoxsalesman.dungeonsmobs.gear.enchantments.armor.head;

import static net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

import java.util.List;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.BeastEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.FollowerLeaderHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Leader;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class BeastSurgeEnchantment extends BeastEnchantment {
	public BeastSurgeEnchantment() {
		super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onPlayerUsedHealthPotion(LivingEntityUseItemEvent.Finish event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (player.isAlive()) {
			List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
			if (potionEffects.isEmpty())
				return;
			if (potionEffects.get(0).getEffect() == MobEffects.HEAL) {
				int beastSurgeLevel = EnchantmentHelper
						.getEnchantmentLevel(EnchantmentInit.BEAST_SURGE.get(), player);
				if (beastSurgeLevel > 0) {
					Leader summonerCap = FollowerLeaderHelper.getLeaderCapability(player);

					for (Entity summonedMob : summonerCap.getSummonedMobs()) {
						if (summonedMob instanceof LivingEntity) {
							LivingEntity summonedMobAsLiving = (LivingEntity) summonedMob;
							MobEffectInstance surgeSpeed = new MobEffectInstance(
									MobEffects.MOVEMENT_SPEED,
									DungeonsGearConfig.BEAST_SURGE_DURATION.get(),
									beastSurgeLevel - 1);
							summonedMobAsLiving.addEffect(surgeSpeed);
						}
					}
				}
			}
		}
	}

}
