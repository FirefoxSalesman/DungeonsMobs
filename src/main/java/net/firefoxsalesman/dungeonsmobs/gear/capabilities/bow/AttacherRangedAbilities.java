package net.firefoxsalesman.dungeonsmobs.gear.capabilities.bow;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.GearCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class AttacherRangedAbilities {

	private static class RangedAbilitiesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = new ResourceLocation(MOD_ID, "ranged_abilities");
		private final RangedAbilities backend = new RangedAbilities();
		private final LazyOptional<RangedAbilities> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return GearCapabilities.RANGED_ABILITIES_CAPABILITY.orEmpty(cap, this.optionalData);
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
	public static void attach(final AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack itemStack = event.getObject();
		if (itemStack.getItem() instanceof ProjectileWeaponItem) {
			final AttacherRangedAbilities.RangedAbilitiesProvider provider = new AttacherRangedAbilities.RangedAbilitiesProvider();
			event.addCapability(AttacherRangedAbilities.RangedAbilitiesProvider.IDENTIFIER, provider);
		}
	}
}
