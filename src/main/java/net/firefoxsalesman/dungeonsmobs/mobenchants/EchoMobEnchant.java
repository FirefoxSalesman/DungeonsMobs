package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.firefoxsalesman.dungeonsmobs.mod.ModDamageSources;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;

public class EchoMobEnchant extends MobEnchant {
	public static final float ECHO_CHANCE = 0.25f;

	public EchoMobEnchant(Properties properties) {
		super(properties);
	}

	private static boolean isMelee(DamageSource source, DamageSources sources) {
		return !source.is(DamageTypeTags.IS_EXPLOSION)
				&& !source.is(DamageTypeTags.IS_FIRE)
				&& !DamageSourceHelper.isSource(source, sources.magic());
	}

	public static void doEffect(LivingEntity defender, Entity entity, DamageSource source, float amount) {
		if (entity instanceof LivingEntity
				&& EchoMobEnchant.isMelee(source, entity.damageSources())
				&& !(source.is(ModDamageSources.ECHO))) {
			LivingEntity attacker = (LivingEntity) entity;
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.ECHO.get(), (level) -> {
				if (attacker.getRandom().nextFloat() <= EchoMobEnchant.ECHO_CHANCE * level) {
					defender.hurt(ModDamageSources.source(entity.level(),
							ModDamageSources.ECHO, attacker, null),
							amount);
					defender.invulnerableTime = 0;
				}
			});
		}
	}
}
