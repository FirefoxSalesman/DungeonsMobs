package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;
import static net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants.ECHO;

public class EchoMobEnchant extends MobEnchant {

	private static final float ECHO_CHANCE = 0.25f;

	public EchoMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		LivingEntity defender = event.getEntity();
		Entity entity = event.getSource().getEntity();
		if (entity instanceof LivingEntity && isMelee(event.getSource(), entity.damageSources())
				&& !(event.getSource().is(ModDamageSources.ECHO))) {
			LivingEntity attacker = (LivingEntity) entity;
			executeIfPresentWithLevel(attacker, ECHO.get(), (level) -> {
				if (attacker.getRandom().nextFloat() <= ECHO_CHANCE * level) {
					defender.hurt(ModDamageSources.source(entity.level(),
							ModDamageSources.ECHO, attacker, null), event.getAmount());
					defender.invulnerableTime = 0;
				}
			});
		}
	}

	private static boolean isMelee(DamageSource source, DamageSources sources) {
		return !DamageSourceHelper.isSource(source, sources.onFire())
				&& !DamageSourceHelper.isSource(source, sources.inFire())
				&& !DamageSourceHelper.isSource(source, sources.lava())
				&& !DamageSourceHelper.isSource(source, sources.magic());
	}
}
