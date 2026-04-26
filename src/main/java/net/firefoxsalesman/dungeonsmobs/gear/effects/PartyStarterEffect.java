package net.firefoxsalesman.dungeonsmobs.gear.effects;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.registry.MobEffectInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AOECloudHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.PlayerAttackHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.mixin.MobEffectInstanceAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class PartyStarterEffect extends MobEffect {
	public PartyStarterEffect(MobEffectCategory effectType, int liquidColorIn) {
		super(effectType, liquidColorIn);
	}

	@SubscribeEvent
	public static void onPartyStarterAttack(LivingAttackEvent event) {
		if (PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource()))
			return;

		LivingEntity victim = event.getEntity();
		Entity trueSource = event.getSource().getEntity();
		if (trueSource instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) trueSource;
			MobEffectInstance partyStarter = attacker.getEffect(MobEffectInit.PARTY_STARTER.get());
			if (partyStarter != null) {
				int partyStarterLevel = partyStarter.getAmplifier();

				if (!attacker.level().isClientSide) {
					AOECloudHelper.spawnExplosionCloud(attacker, victim, 3.0F);
					AreaOfEffectHelper.causeExplosionAttack(attacker, victim,
							DungeonsGearConfig.PARTY_STARTER_DAMAGE.get(), 3.0F);
					SoundHelper.playGenericExplodeSound(victim);
				}
				partyStarterLevel--;
				if (partyStarterLevel <= 0) {
					attacker.removeEffect(MobEffectInit.PARTY_STARTER.get());
				} else {
					((MobEffectInstanceAccessor) partyStarter).setAmplifier(partyStarterLevel);
				}
			}
		}
	}
}
