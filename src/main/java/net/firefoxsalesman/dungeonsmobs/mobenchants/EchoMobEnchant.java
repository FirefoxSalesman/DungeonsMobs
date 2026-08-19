package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;

public class EchoMobEnchant extends MobEnchant {
	public static final float ECHO_CHANCE = 0.25f;

	public EchoMobEnchant(Properties properties) {
		super(properties);
	}

	public static boolean isMelee(DamageSource source, DamageSources sources) {
		return !source.is(DamageTypeTags.IS_EXPLOSION)
				&& !source.is(DamageTypeTags.IS_FIRE)
				&& !DamageSourceHelper.isSource(source, sources.magic());
	}
}
