package net.firefoxsalesman.dungeonsmobs.mobenchants;

import java.util.function.Consumer;
import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import baguchan.enchantwithmob.utils.MobEnchantUtils;
import net.minecraft.world.entity.LivingEntity;

public class NewMobEnchantUtils {
	public static void executeIfPresentWithLevel(LivingEntity entity, MobEnchant mobEnchantment,
			Consumer<Integer> consumer) {
		if (entity != null && entity instanceof IEnchantCap cap) {
			int level = MobEnchantUtils.getMobEnchantLevelFromHandler(cap.getEnchantCap().getMobEnchants(),
					mobEnchantment);
			if (level > 0)
				consumer.accept(level);
		}
	}
}
