package net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster;

import org.jetbrains.annotations.NotNull;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonsmobs.lib.utils.ResourceLocationHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttacherSoulCaster {

	private static class SoulCasterProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = ResourceLocationHelper.modLoc("soul_caster");
		private final SoulCaster backend = new SoulCaster();
		private final LazyOptional<SoulCaster> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return LibCapabilities.SOUL_CASTER_CAPABILITY.orEmpty(cap, this.optionalData);
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

	// attach only to living entities
	public static void attach(final AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof LivingEntity) {
			final AttacherSoulCaster.SoulCasterProvider provider = new AttacherSoulCaster.SoulCasterProvider();
			event.addCapability(AttacherSoulCaster.SoulCasterProvider.IDENTIFIER, provider);
		}
	}
}
