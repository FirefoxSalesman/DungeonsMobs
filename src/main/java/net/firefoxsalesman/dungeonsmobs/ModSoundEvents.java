package net.firefoxsalesman.dungeonsmobs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			DungeonsMobs.MOD_ID);

	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_IDLE = registerSoundEvents(
			"entity.jungle_zombie.idle");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_HURT = registerSoundEvents(
			"entity.jungle_zombie.hurt");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_DEATH = registerSoundEvents(
			"entity.jungle_zombie.death");
	public static final RegistryObject<SoundEvent> JUNGLE_ZOMBIE_STEP = registerSoundEvents(
			"entity.jungle_zombie.step");

	public static final RegistryObject<SoundEvent> FROZEN_ZOMBIE_IDLE = registerSoundEvents(
			"entity.frozen_zombie.idle");
	public static final RegistryObject<SoundEvent> FROZEN_ZOMBIE_HURT = registerSoundEvents(
			"entity.frozen_zombie.hurt");
	public static final RegistryObject<SoundEvent> FROZEN_ZOMBIE_DEATH = registerSoundEvents(
			"entity.frozen_zombie.death");
	public static final RegistryObject<SoundEvent> FROZEN_ZOMBIE_SNOWBALL_LAND = registerSoundEvents(
			"entity.frozen_zombie.snowball_land");

	public static final RegistryObject<SoundEvent> ICY_CREEPER_EXPLODE = registerSoundEvents(
			"entity.icy_creeper.explode");

	public static final RegistryObject<SoundEvent> MOSSY_SKELETON_IDLE = registerSoundEvents(
			"entity.mossy_skeleton.idle");
	public static final RegistryObject<SoundEvent> MOSSY_SKELETON_HURT = registerSoundEvents(
			"entity.mossy_skeleton.hurt");
	public static final RegistryObject<SoundEvent> MOSSY_SKELETON_DEATH = registerSoundEvents(
			"entity.mossy_skeleton.death");
	public static final RegistryObject<SoundEvent> MOSSY_SKELETON_SHOOT = registerSoundEvents(
			"entity.mossy_skeleton.shoot");
	public static final RegistryObject<SoundEvent> MOSSY_SKELETON_STEP = registerSoundEvents(
			"entity.mossy_skeleton.step");
	public static final RegistryObject<SoundEvent> SUNKEN_SKELETON_IDLE = registerSoundEvents(
			"entity.sunken_skeleton.idle");
	public static final RegistryObject<SoundEvent> SUNKEN_SKELETON_HURT = registerSoundEvents(
			"entity.sunken_skeleton.hurt");
	public static final RegistryObject<SoundEvent> SUNKEN_SKELETON_DEATH = registerSoundEvents(
			"entity.sunken_skeleton.death");
	public static final RegistryObject<SoundEvent> SUNKEN_SKELETON_STEP = registerSoundEvents(
			"entity.sunken_skeleton.step");
	public static final RegistryObject<SoundEvent> SUNKEN_SKELETON_SHOOT = registerSoundEvents(
			"entity.sunken_skeleton.shoot");

	public static void register(IEventBus eventBus) {
		SOUNDS.register(eventBus);
	}

	public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUNDS.register(name, () -> SoundEvent
				.createVariableRangeEvent(new ResourceLocation(DungeonsMobs.MOD_ID, name)));
	}
}
