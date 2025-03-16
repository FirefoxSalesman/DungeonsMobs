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

	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_IDLE = registerSoundEvents(
			"entity.squall_golem.idle");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_HURT = registerSoundEvents(
			"entity.squall_golem.hurt");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_DEATH = registerSoundEvents(
			"entity.squall_golem.death");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_ATTACK = registerSoundEvents(
			"entity.squall_golem.attack");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_OPEN = registerSoundEvents(
			"entity.squall_golem.on");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_OFF = registerSoundEvents(
			"entity.squall_golem.off");
	public static final RegistryObject<SoundEvent> SQUALL_GOLEM_WALK = registerSoundEvents(
			"entity.squall_golem.walk");

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

	public static final RegistryObject<SoundEvent> SNARELING_STEP = registerSoundEvents("entity.snareling.step");
	public static final RegistryObject<SoundEvent> SNARELING_IDLE = registerSoundEvents("entity.snareling.idle");
	public static final RegistryObject<SoundEvent> SNARELING_HURT = registerSoundEvents("entity.snareling.hurt");
	public static final RegistryObject<SoundEvent> SNARELING_DEATH = registerSoundEvents("entity.snareling.death");
	public static final RegistryObject<SoundEvent> SNARELING_ATTACK = registerSoundEvents(
			"entity.snareling.attack");
	public static final RegistryObject<SoundEvent> SNARELING_PREPARE_SHOOT = registerSoundEvents(
			"entity.snareling.prepare_shoot");
	public static final RegistryObject<SoundEvent> SNARELING_SHOOT = registerSoundEvents("entity.snareling.shoot");
	public static final RegistryObject<SoundEvent> SNARELING_GLOB_LAND = registerSoundEvents(
			"entity.snareling.glob_land");

	public static final RegistryObject<SoundEvent> BLASTLING_IDLE = registerSoundEvents("entity.blastling.idle");
	public static final RegistryObject<SoundEvent> BLASTLING_HURT = registerSoundEvents("entity.blastling.hurt");
	public static final RegistryObject<SoundEvent> BLASTLING_DEATH = registerSoundEvents("entity.blastling.death");
	public static final RegistryObject<SoundEvent> BLASTLING_STEP = registerSoundEvents("entity.blastling.step");
	public static final RegistryObject<SoundEvent> BLASTLING_SHOOT = registerSoundEvents("entity.blastling.shoot");
	public static final RegistryObject<SoundEvent> BLASTLING_BULLET_LAND = registerSoundEvents(
			"entity.blastling.bullet_land");

	public static final RegistryObject<SoundEvent> WATCHLING_IDLE = registerSoundEvents("entity.watchling.idle");
	public static final RegistryObject<SoundEvent> WATCHLING_HURT = registerSoundEvents("entity.watchling.hurt");
	public static final RegistryObject<SoundEvent> WATCHLING_DEATH = registerSoundEvents("entity.watchling.death");
	public static final RegistryObject<SoundEvent> WATCHLING_STEP = registerSoundEvents("entity.watchling.step");
	public static final RegistryObject<SoundEvent> WATCHLING_ATTACK = registerSoundEvents(
			"entity.watchling.attack");

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

	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_IDLE = registerSoundEvents(
			"entity.redstone_golem.idle");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_HURT = registerSoundEvents(
			"entity.redstone_golem.hurt");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_DEATH = registerSoundEvents(
			"entity.redstone_golem.death");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_STEP = registerSoundEvents(
			"entity.redstone_golem.step");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_ATTACK = registerSoundEvents(
			"entity.redstone_golem.attack");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_SUMMON_MINES = registerSoundEvents(
			"entity.redstone_golem.summon_mines");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_IDLE_PULSE_LOOP = registerSoundEvents(
			"entity.redstone_golem.idle_pulse_loop");
	public static final RegistryObject<SoundEvent> REDSTONE_GOLEM_SPARK = registerSoundEvents(
			"entity.redstone_golem.spark");

	public static final RegistryObject<SoundEvent> LEAPLEAF_IDLE_VOCAL = registerSoundEvents(
			"entity.leapleaf.idle_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_HURT_VOCAL = registerSoundEvents(
			"entity.leapleaf.hurt_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_STEP_VOCAL = registerSoundEvents(
			"entity.leapleaf.step_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_ATTACK_VOCAL = registerSoundEvents(
			"entity.leapleaf.attack_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_PREPARE_LEAP_VOCAL = registerSoundEvents(
			"entity.leapleaf.prepare_leap_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_LEAP_VOCAL = registerSoundEvents(
			"entity.leapleaf.leap_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_REST_VOCAL = registerSoundEvents(
			"entity.leapleaf.rest_vocal");
	public static final RegistryObject<SoundEvent> LEAPLEAF_IDLE_FOLEY = registerSoundEvents(
			"entity.leapleaf.idle_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_HURT_FOLEY = registerSoundEvents(
			"entity.leapleaf.hurt_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_STEP_FOLEY = registerSoundEvents(
			"entity.leapleaf.step_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_ATTACK_FOLEY = registerSoundEvents(
			"entity.leapleaf.attack_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_PREPARE_LEAP_FOLEY = registerSoundEvents(
			"entity.leapleaf.prepare_leap_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_LEAP_FOLEY = registerSoundEvents(
			"entity.leapleaf.leap_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_REST_FOLEY = registerSoundEvents(
			"entity.leapleaf.rest_foley");
	public static final RegistryObject<SoundEvent> LEAPLEAF_DEATH = registerSoundEvents("entity.leapleaf.death");
	public static final RegistryObject<SoundEvent> LEAPLEAF_LAND = registerSoundEvents("entity.leapleaf.land");

	public static final RegistryObject<SoundEvent> NECROMANCER_SHOOT = registerSoundEvents(
			"entity.necromancer.shoot");
	public static final RegistryObject<SoundEvent> NECROMANCER_ORB_IMPACT = registerSoundEvents(
			"entity.necromancer.orb_impact");

	public static void register(IEventBus eventBus) {
		SOUNDS.register(eventBus);
	}

	public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUNDS.register(name, () -> SoundEvent
				.createVariableRangeEvent(new ResourceLocation(DungeonsMobs.MOD_ID, name)));
	}
}
