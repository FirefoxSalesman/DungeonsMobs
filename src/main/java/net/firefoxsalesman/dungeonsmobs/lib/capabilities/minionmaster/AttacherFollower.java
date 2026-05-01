package net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster;

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

public class AttacherFollower {

	private static class FollowerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = ResourceLocationHelper.modLoc("minion");
		private final Follower backend = new Follower();
		private final LazyOptional<Follower> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return LibCapabilities.FOLLOWER_CAPABILITY.orEmpty(cap, this.optionalData);
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
			final FollowerProvider provider = new FollowerProvider();
			event.addCapability(FollowerProvider.IDENTIFIER, provider);
		}
	}
}
