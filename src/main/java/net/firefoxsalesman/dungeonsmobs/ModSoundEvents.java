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
	public static final RegistryObject<SoundEvent> WRAITH_IDLE = registerSoundEvents("entity.wraith.idle");
	public static final RegistryObject<SoundEvent> WRAITH_HURT = registerSoundEvents("entity.wraith.hurt");
	public static final RegistryObject<SoundEvent> WRAITH_DEATH = registerSoundEvents("entity.wraith.death");
	public static final RegistryObject<SoundEvent> WRAITH_ATTACK = registerSoundEvents("entity.wraith.attack");
	public static final RegistryObject<SoundEvent> WRAITH_FIRE = registerSoundEvents("entity.wraith.fire");
	public static final RegistryObject<SoundEvent> WRAITH_FLY = registerSoundEvents("entity.wraith.fly");
	public static final RegistryObject<SoundEvent> WRAITH_TELEPORT = registerSoundEvents("entity.wraith.teleport");

	public static final RegistryObject<SoundEvent> ENCHANTER_IDLE = registerSoundEvents("entity.enchanter.idle");
	public static final RegistryObject<SoundEvent> ENCHANTER_HURT = registerSoundEvents("entity.enchanter.hurt");
	public static final RegistryObject<SoundEvent> ENCHANTER_DEATH = registerSoundEvents("entity.enchanter.death");
	public static final RegistryObject<SoundEvent> ENCHANTER_PRE_ATTACK = registerSoundEvents(
			"entity.enchanter.pre_attack");
	public static final RegistryObject<SoundEvent> ENCHANTER_ATTACK = registerSoundEvents(
			"entity.enchanter.attack");
	public static final RegistryObject<SoundEvent> ENCHANTER_SPELL = registerSoundEvents("entity.enchanter.spell");
	public static final RegistryObject<SoundEvent> ENCHANTER_BEAM = registerSoundEvents("entity.enchanter.beam");
	public static final RegistryObject<SoundEvent> ENCHANTER_BEAM_LOOP = registerSoundEvents(
			"entity.enchanter.beam_loop");

	public static final RegistryObject<SoundEvent> GEOMANCER_IDLE = registerSoundEvents("entity.geomancer.idle");
	public static final RegistryObject<SoundEvent> GEOMANCER_HURT = registerSoundEvents("entity.geomancer.hurt");
	public static final RegistryObject<SoundEvent> GEOMANCER_DEATH = registerSoundEvents("entity.geomancer.death");
	public static final RegistryObject<SoundEvent> GEOMANCER_PRE_ATTACK = registerSoundEvents(
			"entity.geomancer.pre_attack");
	public static final RegistryObject<SoundEvent> GEOMANCER_ATTACK = registerSoundEvents(
			"entity.geomancer.attack");
	public static final RegistryObject<SoundEvent> GEOMANCER_WALL_SPAWN = registerSoundEvents(
			"entity.geomancer.wall_spawn");
	public static final RegistryObject<SoundEvent> GEOMANCER_WALL_DESPAWN = registerSoundEvents(
			"entity.geomancer.wall_despawn");
	public static final RegistryObject<SoundEvent> GEOMANCER_BOMB_SPAWN = registerSoundEvents(
			"entity.geomancer.bomb_spawn");

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

	public static final RegistryObject<SoundEvent> ROYAL_GUARD_ATTACK = registerSoundEvents(
			"entity.royal_guard.attack");
	public static final RegistryObject<SoundEvent> ROYAL_GUARD_STEP = registerSoundEvents(
			"entity.royal_guard.step");

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

	public static final RegistryObject<SoundEvent> ENDERSENT_STEP = registerSoundEvents("entity.endersent.step");
	public static final RegistryObject<SoundEvent> ENDERSENT_IDLE = registerSoundEvents("entity.endersent.idle");
	public static final RegistryObject<SoundEvent> ENDERSENT_HURT = registerSoundEvents("entity.endersent.hurt");
	public static final RegistryObject<SoundEvent> ENDERSENT_DEATH = registerSoundEvents("entity.endersent.death");
	public static final RegistryObject<SoundEvent> ENDERSENT_ATTACK = registerSoundEvents(
			"entity.endersent.attack");
	public static final RegistryObject<SoundEvent> ENDERSENT_IDLE_SMASH = registerSoundEvents(
			"entity.endersent.idle_smash");
	public static final RegistryObject<SoundEvent> ENDERSENT_TELEPORT = registerSoundEvents(
			"entity.endersent.teleport");

	public static final RegistryObject<SoundEvent> ICE_CHUNK_IDLE_LOOP = registerSoundEvents(
			"entity.ice_chunk.idle_loop");
	public static final RegistryObject<SoundEvent> ICE_CHUNK_SUMMONED = registerSoundEvents(
			"entity.ice_chunk.summoned");
	public static final RegistryObject<SoundEvent> ICE_CHUNK_FALL = registerSoundEvents("entity.ice_chunk.fall");
	public static final RegistryObject<SoundEvent> ICE_CHUNK_LAND = registerSoundEvents("entity.ice_chunk.land");

	public static final RegistryObject<SoundEvent> SKELETON_VANGUARD_ATTACK = registerSoundEvents(
			"entity.skeleton_vanguard.attack");
	public static final RegistryObject<SoundEvent> SKELETON_VANGUARD_IDLE = registerSoundEvents(
			"entity.skeleton_vanguard.idle");
	public static final RegistryObject<SoundEvent> SKELETON_VANGUARD_HURT = registerSoundEvents(
			"entity.skeleton_vanguard.hurt");
	public static final RegistryObject<SoundEvent> SKELETON_VANGUARD_DEATH = registerSoundEvents(
			"entity.skeleton_vanguard.death");
	public static final RegistryObject<SoundEvent> SKELETON_VANGUARD_STEP = registerSoundEvents(
			"entity.skeleton_vanguard.step");

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

	public static final RegistryObject<SoundEvent> ILLUSIONER_DEATH = registerSoundEvents(
			"entity.illusioner.death");

	public static final RegistryObject<SoundEvent> MOUNTAINEER_IDLE = registerSoundEvents(
			"entity.mountaineer.idle");
	public static final RegistryObject<SoundEvent> MOUNTAINEER_HURT = registerSoundEvents(
			"entity.mountaineer.hurt");
	public static final RegistryObject<SoundEvent> MOUNTAINEER_DEATH = registerSoundEvents(
			"entity.mountaineer.death");

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

	public static final RegistryObject<SoundEvent> WHISPERER_IDLE_VOCAL = registerSoundEvents(
			"entity.whisperer.idle_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_HURT_VOCAL = registerSoundEvents(
			"entity.whisperer.hurt_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_STEP_VOCAL = registerSoundEvents(
			"entity.whisperer.step_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_ATTACK_VOCAL = registerSoundEvents(
			"entity.whisperer.attack_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_SUMMON_PQV_VOCAL = registerSoundEvents(
			"entity.whisperer.summon_pqv_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_SUMMON_QGV_VOCAL = registerSoundEvents(
			"entity.whisperer.summon_qgv_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_GRAPPLE_VOCAL = registerSoundEvents(
			"entity.whisperer.grapple_vocal");
	public static final RegistryObject<SoundEvent> WHISPERER_IDLE_FOLEY = registerSoundEvents(
			"entity.whisperer.idle_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_HURT_FOLEY = registerSoundEvents(
			"entity.whisperer.hurt_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_STEP_FOLEY = registerSoundEvents(
			"entity.whisperer.step_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_ATTACK_FOLEY = registerSoundEvents(
			"entity.whisperer.attack_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_SUMMON_PQV_FOLEY = registerSoundEvents(
			"entity.whisperer.summon_pqv_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_SUMMON_QGV_FOLEY = registerSoundEvents(
			"entity.whisperer.summon_qgv_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_GRAPPLE_FOLEY = registerSoundEvents(
			"entity.whisperer.grapple_foley");
	public static final RegistryObject<SoundEvent> WHISPERER_DEATH = registerSoundEvents("entity.whisperer.death");

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

	public static final RegistryObject<SoundEvent> WAVEWHISPERER_IDLE = registerSoundEvents(
			"entity.wavewhisperer.idle");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_HURT = registerSoundEvents(
			"entity.wavewhisperer.hurt");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_DEATH = registerSoundEvents(
			"entity.wavewhisperer.death");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_STEP = registerSoundEvents(
			"entity.wavewhisperer.step");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_ATTACK = registerSoundEvents(
			"entity.wavewhisperer.attack");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_SUMMON_PA_FOLEY = registerSoundEvents(
			"entity.wavewhisperer.summon_pa_foley");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_SUMMON_PA_VOCAL = registerSoundEvents(
			"entity.wavewhisperer.summon_pa_vocal");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_SUMMON_QGK = registerSoundEvents(
			"entity.wavewhisperer.summon_qgk");
	public static final RegistryObject<SoundEvent> WAVEWHISPERER_GRAPPLE = registerSoundEvents(
			"entity.wavewhisperer.grapple");

	public static final RegistryObject<SoundEvent> QUICK_GROWING_KELP_BURST = registerSoundEvents(
			"entity.quick_growing_kelp.burst");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_KELP_BURST_DOWN = registerSoundEvents(
			"entity.quick_growing_kelp.burst_down");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_KELP_HURT = registerSoundEvents(
			"entity.quick_growing_kelp.hurt");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_KELP_DEATH = registerSoundEvents(
			"entity.quick_growing_kelp.death");

	public static final RegistryObject<SoundEvent> POISON_ANEMONE_BURST = registerSoundEvents(
			"entity.poison_anemone.burst");
	public static final RegistryObject<SoundEvent> POISON_ANEMONE_IDLE = registerSoundEvents(
			"entity.poison_anemone.idle");
	public static final RegistryObject<SoundEvent> POISON_ANEMONE_HURT = registerSoundEvents(
			"entity.poison_anemone.hurt_vocal");
	public static final RegistryObject<SoundEvent> POISON_ANEMONE_DEATH = registerSoundEvents(
			"entity.poison_anemone.death");
	public static final RegistryObject<SoundEvent> POISON_ANEMONE_CLOSE = registerSoundEvents(
			"entity.poison_anemone.close");
	public static final RegistryObject<SoundEvent> POISON_ANEMONE_SHOOT = registerSoundEvents(
			"entity.poison_anemone.shoot");

	public static final RegistryObject<SoundEvent> QUICK_GROWING_VINE_BURST = registerSoundEvents(
			"entity.quick_growing_vine.burst");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_VINE_BURST_DOWN = registerSoundEvents(
			"entity.quick_growing_vine.burst_down");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_VINE_HURT = registerSoundEvents(
			"entity.quick_growing_vine.hurt");
	public static final RegistryObject<SoundEvent> QUICK_GROWING_VINE_DEATH = registerSoundEvents(
			"entity.quick_growing_vine.death");

	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_BURST = registerSoundEvents(
			"entity.poison_quill_vine.burst");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_IDLE = registerSoundEvents(
			"entity.poison_quill_vine.idle");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_HURT_VOCAL = registerSoundEvents(
			"entity.poison_quill_vine.hurt_vocal");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_HURT_FOLEY = registerSoundEvents(
			"entity.poison_quill_vine.hurt_foley");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_DEATH = registerSoundEvents(
			"entity.poison_quill_vine.death");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_OPEN = registerSoundEvents(
			"entity.poison_quill_vine.open");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_CLOSE = registerSoundEvents(
			"entity.poison_quill_vine.close");
	public static final RegistryObject<SoundEvent> POISON_QUILL_VINE_SHOOT = registerSoundEvents(
			"entity.poison_quill_vine.shoot");

	public static final RegistryObject<SoundEvent> FUNGUS_THROWER_THROW = registerSoundEvents(
			"entity.fungus_thrower.throw");
	public static final RegistryObject<SoundEvent> FUNGUS_THROWER_FUNGUS_LAND = registerSoundEvents(
			"entity.fungus_thrower.fungus_land");

	public static final RegistryObject<SoundEvent> NECROMANCER_SHOOT = registerSoundEvents(
			"entity.necromancer.shoot");
	public static final RegistryObject<SoundEvent> NECROMANCER_ORB_IMPACT = registerSoundEvents(
			"entity.necromancer.orb_impact");
	public static final RegistryObject<SoundEvent> NECROMANCER_PREPARE_SUMMON = registerSoundEvents(
			"entity.necromancer.prepare_summon");

	public static final RegistryObject<SoundEvent> WINDCALLER_IDLE = registerSoundEvents("entity.windcaller.idle");
	public static final RegistryObject<SoundEvent> WINDCALLER_HURT = registerSoundEvents("entity.windcaller.hurt");
	public static final RegistryObject<SoundEvent> WINDCALLER_DEATH = registerSoundEvents(
			"entity.windcaller.death");
	public static final RegistryObject<SoundEvent> WINDCALLER_LIFT_WIND = registerSoundEvents(
			"entity.windcaller.lift_wind");
	public static final RegistryObject<SoundEvent> WINDCALLER_BLAST_WIND = registerSoundEvents(
			"entity.windcaller.blast_wind");
	public static final RegistryObject<SoundEvent> WINDCALLER_LIFT_VOCAL = registerSoundEvents(
			"entity.windcaller.lift_vocal");
	public static final RegistryObject<SoundEvent> WINDCALLER_BLAST_VOCAL = registerSoundEvents(
			"entity.windcaller.blast_vocal");
	public static final RegistryObject<SoundEvent> WINDCALLER_FLY_LOOP = registerSoundEvents(
			"entity.windcaller.fly_loop");

	public static final RegistryObject<SoundEvent> SPIDER_PREPARE_SHOOT = registerSoundEvents(
			"entity.spider.prepare_shoot");
	public static final RegistryObject<SoundEvent> SPIDER_SHOOT = registerSoundEvents("entity.spider.shoot");
	public static final RegistryObject<SoundEvent> SPIDER_WEB_IMPACT = registerSoundEvents(
			"entity.spider.web_impact");

	public static void register(IEventBus eventBus) {
		SOUNDS.register(eventBus);
	}

	public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUNDS.register(name, () -> SoundEvent
				.createVariableRangeEvent(new ResourceLocation(DungeonsMobs.MOD_ID, name)));
	}
}
