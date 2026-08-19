package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;

public class RegenerationMobEnchant extends MobEnchant {

	public RegenerationMobEnchant(Properties properties) {
		super(properties);
	}

	public static int getTickCountForLevel(Integer level) {
		return 62 - level * 12;
	}

	public static void doEffect(LivingEntity entity) {
		NewMobEnchantUtils.executeIfPresentWithLevel(entity, ModMobEnchants.REGENERATION.get(), (level) -> {
			if (entity.getHealth() < entity.getMaxHealth()
					&& entity.tickCount % getTickCountForLevel(level) == 0) {
				entity.heal(1.0F);
				DungeonsMobs.PROXY.spawnParticles(entity, ParticleTypes.HEART);
			}
		});
	}
}
