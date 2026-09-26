package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonslibs.init.ModDamageSources;
import net.firefoxsalesman.dungeonslibs.utils.DamageSourceHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ThunderingMobEnchant extends MobEnchant {
	private static final float THUNDER_CHANCE = 0.3f;

	public ThunderingMobEnchant(Properties properties) {
		super(properties);
	}

	// TODO Pull this out into a helper class
	private static boolean isMelee(DamageSource source, DamageSources sources) {
		return !source.is(DamageTypeTags.IS_EXPLOSION)
				&& !source.is(DamageTypeTags.IS_FIRE)
				&& !DamageSourceHelper.isSource(source, sources.magic());
	}

	// TODO Pull this out into the library
	private static void createVisualLightningBoltOnEntity(Entity target) {
		Level world = target.getCommandSenderWorld();
		LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
		if (lightningboltentity != null) {
			lightningboltentity.moveTo(target.getX(), target.getY(), target.getZ());
			lightningboltentity.setVisualOnly(true);
			world.addFreshEntity(lightningboltentity);
		}
	}

	private static void electrify(LivingEntity attacker, LivingEntity victim, float damageAmount) {
		createVisualLightningBoltOnEntity(victim);
		DungeonsMobs.PROXY.spawnParticles(victim, ModParticleTypes.ELECTRIC_SHOCK.get());
		victim.hurt(ModDamageSources.source(attacker.level(),
				net.firefoxsalesman.dungeonslibs.init.ModDamageSources.ELECTRIC_SHOCK, attacker,
				null),
				damageAmount);
	}

	public static void doEffect(LivingEntity defender, Entity entity, DamageSource source, float amount) {
		if (entity instanceof LivingEntity attacker
				&& ThunderingMobEnchant.isMelee(source, entity.damageSources())
				&& !(source.is(ModDamageSources.ELECTRIC_SHOCK)))
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.THUNDERING.get(),
					(level) -> {
						if (attacker.getRandom().nextFloat() <= THUNDER_CHANCE) {
							electrify(attacker, defender, amount * level);
							defender.invulnerableTime = 0;
						}
					});
	}
}
