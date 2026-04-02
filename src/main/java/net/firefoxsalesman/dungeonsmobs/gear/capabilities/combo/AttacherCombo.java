package net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.GearCapabilities;
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
import org.jetbrains.annotations.NotNull;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class AttacherCombo {

	private static class ComboProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = new ResourceLocation(MOD_ID, "combo");
		private final Combo backend = new Combo();
		private final LazyOptional<Combo> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return GearCapabilities.COMBO_CAPABILITY.orEmpty(cap, this.optionalData);
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
			final ComboProvider provider = new ComboProvider();
			event.addCapability(ComboProvider.IDENTIFIER, provider);
		}
	}
}
