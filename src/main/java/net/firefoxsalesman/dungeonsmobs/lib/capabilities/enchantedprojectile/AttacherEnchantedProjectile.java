package net.firefoxsalesman.dungeonsmobs.lib.capabilities.enchantedprojectile;

import org.jetbrains.annotations.NotNull;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ResourceLocationHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttacherEnchantedProjectile {

	private static class EnchantedProjectileProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = ResourceLocationHelper.modLoc("enchanted_projectile");
		private final EnchantedProjectile backend = new EnchantedProjectile();
		private final LazyOptional<EnchantedProjectile> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return LibCapabilities.ENCHANTED_PROJECTILE_CAPABILITY.orEmpty(cap, this.optionalData);
		}

		@Override
		public CompoundTag serializeNBT() {
			return this.backend.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.backend.deserializeNBT(nbt);
		}
	}

	public static void attach(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Projectile) {
			final AttacherEnchantedProjectile.EnchantedProjectileProvider provider = new AttacherEnchantedProjectile.EnchantedProjectileProvider();
			event.addCapability(AttacherEnchantedProjectile.EnchantedProjectileProvider.IDENTIFIER,
					provider);
		}
	}
}
