package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static baguchan.enchantwithmob.utils.MobEnchantUtils.getMobEnchantLevelFromHandler;

import java.util.function.Consumer;

import baguchan.enchantwithmob.capability.MobEnchantCapability;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class NewMobEnchantUtils {
	private static Capability<MobEnchantCapability> MOB_ENCHANT_CAP = CapabilityManager
			.get(new CapabilityToken<>() {
			});

	public static void executeIfPresentWithLevel(LivingEntity entity, MobEnchant mobEnchantment,
			Consumer<Integer> consumer) {
		if (entity != null) {
			entity.getCapability(MOB_ENCHANT_CAP).ifPresent((cap) -> {
				int level = getMobEnchantLevelFromHandler(cap.getMobEnchants(), mobEnchantment);
				if (level > 0) {
					consumer.accept(level);
				}

			});
		}

	}
}
