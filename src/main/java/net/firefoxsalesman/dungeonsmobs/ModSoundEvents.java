package net.firefoxsalesman.dungeonsmobs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			Dungeonsmobs.MOD_ID);

	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_IDLE = registerSoundEvents(
			"entity.jungle_zombie.idle");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_HURT = registerSoundEvents(
			"entity.jungle_zombie.hurt");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_DEATH = registerSoundEvents(
			"entity.jungle_zombie.death");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_STEP = registerSoundEvents(
			"entity.jungle_zombie.step");

	public static void register(IEventBus eventBus) {
		SOUNDS.register(eventBus);
	}

	public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUNDS.register(name, () -> SoundEvent
				.createVariableRangeEvent(new ResourceLocation(Dungeonsmobs.MOD_ID, name)));
	}
}
