package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.init.ModDamageSources;
import net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ThunderingMobEnchant extends MobEnchant {
	private static final float THUNDER_CHANCE = 0.3f;

	public ThunderingMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, Entity entity, DamageSource source, float amount) {
		if (entity instanceof LivingEntity attacker
				&& DamageSourceHelper.isMelee(source, entity.damageSources())
				&& !(source.is(ModDamageSources.ELECTRIC_SHOCK)))
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.THUNDERING.get(),
					(level) -> {
						if (attacker.getRandom().nextFloat() <= THUNDER_CHANCE) {
							AreaOfEffectHelper.electrify(attacker, defender,
									amount * level);
							defender.invulnerableTime = 0;
						}
					});
	}
}
