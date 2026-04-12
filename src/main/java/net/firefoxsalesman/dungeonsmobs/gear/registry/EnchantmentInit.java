package net.firefoxsalesman.dungeonsmobs.gear.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ArtifactSynergyEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.BusyBeeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ChainsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.CommittedEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.CriticalHitEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.EchoEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ExplodingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.FreezingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.GuardingStrikeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.IllagersBaneEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.LeechingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.PainCycleEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ProspectorEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RadianceEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RampagingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.RushdownEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ShockwaveEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.SoulSiphonEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.StunningEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.SwirlingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.ThunderingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee.WeakeningEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.AnimaConduitEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.EnigmaResonatorEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.GravityEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.MastersCallEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.PoisonCloudEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee_ranged.RefreshmentEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.AccelerateEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.BonusShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.ChainReactionEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.ExplodingShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.FreezingShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.GaleShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.GravityShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.GrowingEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.OverchargeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.RadianceShotEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.ReplenishEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.RicochetEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.SuperchargeEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.TempoTheftEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ranged.VelocityEnchantment;

public class EnchantmentInit {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
	public static RegistryObject<AnimaConduitEnchantment> ANIMA_CONDUIT = ENCHANTMENTS.register("anima_conduit",
			() -> new AnimaConduitEnchantment());
	public static RegistryObject<EnigmaResonatorEnchantment> ENIGMA_RESONATOR = ENCHANTMENTS
			.register("enigma_resonator", () -> new EnigmaResonatorEnchantment());
	public static RegistryObject<GravityEnchantment> GRAVITY = ENCHANTMENTS.register("gravity",
			() -> new GravityEnchantment());
	public static RegistryObject<MastersCallEnchantment> MASTERS_CALL = ENCHANTMENTS.register("masters_call",
			() -> new MastersCallEnchantment());
	public static RegistryObject<PoisonCloudEnchantment> POISON_CLOUD = ENCHANTMENTS.register("poison_cloud",
			() -> new PoisonCloudEnchantment());
	public static RegistryObject<RefreshmentEnchantment> REFRESHMENT = ENCHANTMENTS.register("refreshment",
			() -> new RefreshmentEnchantment());
	public static RegistryObject<ArtifactSynergyEnchantment> ARTIFACT_SYNERGY = ENCHANTMENTS
			.register("artifact_synergy", () -> new ArtifactSynergyEnchantment());
	public static RegistryObject<ExplodingShotEnchantment> EXPLODING_SHOT = ENCHANTMENTS.register("exploding_shot",
			() -> new ExplodingShotEnchantment());
	public static RegistryObject<FreezingEnchantment> FREEZING = ENCHANTMENTS.register("freezing",
			() -> new FreezingEnchantment());
	public static RegistryObject<BusyBeeEnchantment> BUSY_BEE = ENCHANTMENTS.register("busy_bee",
			() -> new BusyBeeEnchantment());
	public static RegistryObject<ChainsEnchantment> CHAINS = ENCHANTMENTS.register("chains",
			() -> new ChainsEnchantment());
	public static RegistryObject<CommittedEnchantment> COMMITTED = ENCHANTMENTS.register("committed",
			() -> new CommittedEnchantment());
	public static RegistryObject<CriticalHitEnchantment> CRITICAL_HIT = ENCHANTMENTS.register("critical_hit",
			() -> new CriticalHitEnchantment());
	public static RegistryObject<EchoEnchantment> ECHO = ENCHANTMENTS.register("echo", () -> new EchoEnchantment());
	public static RegistryObject<ExplodingEnchantment> EXPLODING = ENCHANTMENTS.register("exploding",
			() -> new ExplodingEnchantment());
	public static RegistryObject<GuardingStrikeEnchantment> GUARDING_STRIKE = ENCHANTMENTS
			.register("guarding_strike", () -> new GuardingStrikeEnchantment());
	public static RegistryObject<IllagersBaneEnchantment> ILLAGERS_BANE = ENCHANTMENTS.register("illagers_bane",
			() -> new IllagersBaneEnchantment());
	public static RegistryObject<LeechingEnchantment> LEECHING = ENCHANTMENTS.register("leeching",
			() -> new LeechingEnchantment());
	public static RegistryObject<PainCycleEnchantment> PAIN_CYCLE = ENCHANTMENTS.register("pain_cycle",
			() -> new PainCycleEnchantment());
	public static RegistryObject<ProspectorEnchantment> PROSPECTOR = ENCHANTMENTS.register("prospector",
			() -> new ProspectorEnchantment());
	public static RegistryObject<RadianceEnchantment> RADIANCE = ENCHANTMENTS.register("radiance",
			() -> new RadianceEnchantment());
	public static RegistryObject<RampagingEnchantment> RAMPAGING = ENCHANTMENTS.register("rampaging",
			() -> new RampagingEnchantment());
	public static RegistryObject<RushdownEnchantment> RUSHDOWN = ENCHANTMENTS.register("rushdown",
			() -> new RushdownEnchantment());
	public static RegistryObject<ShockwaveEnchantment> SHOCKWAVE = ENCHANTMENTS.register("shockwave",
			() -> new ShockwaveEnchantment());
	public static RegistryObject<SoulSiphonEnchantment> SOUL_SIPHON = ENCHANTMENTS.register("soul_siphon",
			() -> new SoulSiphonEnchantment());
	public static RegistryObject<StunningEnchantment> STUNNING = ENCHANTMENTS.register("stunning",
			() -> new StunningEnchantment());
	public static RegistryObject<SwirlingEnchantment> SWIRLING = ENCHANTMENTS.register("swirling",
			() -> new SwirlingEnchantment());
	public static RegistryObject<ThunderingEnchantment> THUNDERING = ENCHANTMENTS.register("thundering",
			() -> new ThunderingEnchantment());
	public static RegistryObject<WeakeningEnchantment> WEAKENING = ENCHANTMENTS.register("weakening",
			() -> new WeakeningEnchantment());
	public static RegistryObject<AccelerateEnchantment> ACCELERATE = ENCHANTMENTS.register("accelerate",
			() -> new AccelerateEnchantment());
	public static RegistryObject<BonusShotEnchantment> BONUS_SHOT = ENCHANTMENTS.register("bonus_shot",
			() -> new BonusShotEnchantment());
	public static RegistryObject<ChainReactionEnchantment> CHAIN_REACTION = ENCHANTMENTS.register("chain_reaction",
			() -> new ChainReactionEnchantment());
	public static RegistryObject<FreezingShotEnchantment> FREEZING_SHOT = ENCHANTMENTS.register("freezing_shot",
			() -> new FreezingShotEnchantment());
	public static RegistryObject<GaleShotEnchantment> GALE_SHOT = ENCHANTMENTS.register("gale_shot",
			() -> new GaleShotEnchantment());
	public static RegistryObject<GravityShotEnchantment> GRAVITY_SHOT = ENCHANTMENTS.register("gravity_shot",
			() -> new GravityShotEnchantment());
	public static RegistryObject<GrowingEnchantment> GROWING = ENCHANTMENTS.register("growing",
			() -> new GrowingEnchantment());
	public static RegistryObject<OverchargeEnchantment> OVERCHARGE = ENCHANTMENTS.register("overcharge",
			() -> new OverchargeEnchantment());
	public static RegistryObject<RadianceShotEnchantment> RADIANCE_SHOT = ENCHANTMENTS.register("radiance_shot",
			() -> new RadianceShotEnchantment());
	public static RegistryObject<ReplenishEnchantment> REPLENISH = ENCHANTMENTS.register("replenish",
			() -> new ReplenishEnchantment());
	public static RegistryObject<RicochetEnchantment> RICOCHET = ENCHANTMENTS.register("ricochet",
			() -> new RicochetEnchantment());
	public static RegistryObject<SuperchargeEnchantment> SUPERCHARGE = ENCHANTMENTS.register("supercharge",
			() -> new SuperchargeEnchantment());
	public static RegistryObject<TempoTheftEnchantment> TEMPO_THEFT = ENCHANTMENTS.register("tempo_theft",
			() -> new TempoTheftEnchantment());
	public static RegistryObject<VelocityEnchantment> VELOCITY = ENCHANTMENTS.register("velocity",
			() -> new VelocityEnchantment());

	public static void register(IEventBus event) {
		ENCHANTMENTS.register(event);
	}
}
