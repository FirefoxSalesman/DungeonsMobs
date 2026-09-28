package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModBlocks;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;

public class FireTrailMobEnchant extends MobEnchant {
	public FireTrailMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity entity) {
		NewMobEnchantUtils.executeIfPresentWithLevel(entity, ModMobEnchants.FIRE_TRAIL.get(),
				level -> entity.level().setBlock(entity.blockPosition(),
						ModBlocks.MAGIC_FIRE.get().defaultBlockState(),
						1));
	}
}
