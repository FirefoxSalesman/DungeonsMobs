package net.firefoxsalesman.dungeonsmobs.lib.capabilities.enchantedprojectile;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities;
import net.minecraft.world.entity.Entity;

public class EnchantedProjectileHelper {

	public static EnchantedProjectile getEnchantedProjectileCapability(Entity entity) {
		return entity.getCapability(LibCapabilities.ENCHANTED_PROJECTILE_CAPABILITY)
				.orElse(new EnchantedProjectile());
	}
}
