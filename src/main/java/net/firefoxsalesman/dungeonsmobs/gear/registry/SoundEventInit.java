package net.firefoxsalesman.dungeonsmobs.gear.registry;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventInit {

	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			MOD_ID);

	public static final RegistryObject<SoundEvent> SOUL_WIZARD_APPEAR = registerSoundEvents(
			"entity.soul_wizard.appear");
	public static final RegistryObject<SoundEvent> SOUL_WIZARD_HURT = registerSoundEvents(
			"entity.soul_wizard.hurt");
	public static final RegistryObject<SoundEvent> SOUL_WIZARD_SHOOT = registerSoundEvents(
			"entity.soul_wizard.shoot");
	public static final RegistryObject<SoundEvent> SOUL_WIZARD_PROJECTILE_IMPACT = registerSoundEvents(
			"entity.soul_wizard.projectile_impact");
	public static final RegistryObject<SoundEvent> SOUL_WIZARD_FLY_LOOP = registerSoundEvents(
			"entity.soul_wizard.fly_loop");

	private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUNDS.register(name, () -> SoundEvent
				.createVariableRangeEvent(new ResourceLocation(MOD_ID, name)));
	}

	public static void register(IEventBus event) {
		SOUNDS.register(event);
	}
}
