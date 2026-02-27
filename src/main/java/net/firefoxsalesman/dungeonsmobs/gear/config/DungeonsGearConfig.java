package net.firefoxsalesman.dungeonsmobs.gear.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.util.List;

public class DungeonsGearConfig {
	private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR;

	public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENCHANTMENT_BLACKLIST;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> TREASURE_ONLY_ENCHANTMENTS;
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTMENT_TRADES;
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTMENT_LOOT;

	// Enchanting specific values
	public static ForgeConfigSpec.ConfigValue<Double> BUSY_BEE_BASE_CHANCE;
	public static ForgeConfigSpec.ConfigValue<Double> BUSY_BEE_CHANCE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// TUMBLE_BEE_CHANCE_PER_LEVEL;
	public static ForgeConfigSpec.ConfigValue<Double> RAMPAGING_CHANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> RAMPAGING_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Integer> RUSHDOWN_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Integer> WEAKENING_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Integer> WEAKENING_DISTANCE;
	// public static ForgeConfigSpec.ConfigValue<Double> LEECHING_BASE_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// LEECHING_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> COMMITTED_BASE_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// COMMITTED_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Integer> DYNAMO_MAX_STACKS;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// DYNAMO_DAMAGE_MULTIPLIER_PER_STACK;
	public static ForgeConfigSpec.ConfigValue<Integer> FREEZING_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Double> SOUL_SIPHON_CHANCE;
	// public static ForgeConfigSpec.ConfigValue<Integer>
	// SOUL_SIPHON_SOULS_PER_LEVEL;
	public static ForgeConfigSpec.ConfigValue<Double> CHAINS_CHANCE;
	public static ForgeConfigSpec.ConfigValue<Double> RADIANCE_CHANCE;
	// public static ForgeConfigSpec.ConfigValue<Double> THUNDERING_CHANCE;
	// public static ForgeConfigSpec.ConfigValue<Integer> THUNDERING_BASE_DAMAGE;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> BEAST_BOSS_BASE_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// BEAST_BOSS_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Integer>
	// BEAST_BURST_DAMAGE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Integer> BEAST_SURGE_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Double> COWARDICE_BASE_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// COWARDICE_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> DEFLECT_CHANCE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> FOCUS_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// FRENZIED_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// GRAVITY_PULSE_BASE_STRENGTH;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// GRAVITY_PULSE_STRENGTH_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Integer>
	// POTION_BARRIER_BASE_DURATION;
	// public static ForgeConfigSpec.ConfigValue<Integer>
	// POTION_BARRIER_DURATION_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// RECKLESS_MAX_HEALTH_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// EXPLODING_MULTIPLIER_PER_LEVEL;
	public static ForgeConfigSpec.ConfigValue<Double> PROSPECTOR_CHANCE_PER_LEVEL;
	public static ForgeConfigSpec.ConfigValue<Double> POISON_CLOUD_CHANCE;
	// public static ForgeConfigSpec.ConfigValue<Double> DODGE_CHANCE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double>
	// VOID_DODGE_CHANCE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> BEEHIVE_CHANCE_PER_LEVEL;
	// public static ForgeConfigSpec.ConfigValue<Double> STUNNING_CHANCE_PER_LEVEL;

	private static CommentedFileConfig cfg;

	public DungeonsGearConfig() {
		cfg = CommentedFileConfig
				.builder(new File(FMLPaths.CONFIGDIR.get().toString(),
						"dungeons_gear-common.toml"))
				.sync()
				.autosave().build();
		cfg.load();
		initConfig();
		ForgeConfigSpec spec = builder.build();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, cfg.getFile().getName());
		spec.setConfig(cfg);
	}

	private void initConfig() {
		builder.comment("General Mod Configuration").push("general_mod_configuration");
		ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR = builder
				.comment("Enable applying enchantments from this mod on non-Dungeons gear using the Enchanting Table. \n"
						+
						"If you don't want your enchantments to become too cluttered for non-Dungeons gear, or simply don't like it, disable this feature. \n"
						+
						"You can still use the anvil to put the enchantments onto them and have them work correctly. [true / false]")
				.define("enableEnchantsOnNonDungeonsGear", true);
		ENCHANTMENT_BLACKLIST = builder
				.comment("Add enchantments that should be prevented from being applied to any gear. \n"
						+ "To do so, enter their registry names.")
				.defineList("enchantmentBlacklist", Lists.newArrayList(
						"dungeonsmobs:lucky_explorer"),
						(itemRaw) -> itemRaw instanceof String);
		TREASURE_ONLY_ENCHANTMENTS = builder
				.comment("Add enchantments that should be designated as treasure-only. \n"
						+ "To do so, enter their registry names.")
				.defineList("treasureOnlyEnchantments", Lists.newArrayList(
						"dungeonsmobs:masters_call"),
						(itemRaw) -> itemRaw instanceof String);
		ENABLE_ENCHANTMENT_TRADES = builder
				.comment("Enable Librarian Villagers trading books enchanted with this mod's enchantments. \n"
						+
						"Disable this feature if you want to prevent this. [true / false]")
				.define("enableEnchantmentTrades", true);
		ENABLE_ENCHANTMENT_LOOT = builder
				.comment("Enable enchantments from this mod appearing in any type of generated loot. \n"
						+
						"Disable this feature if you want to prevent this. [true / false]")
				.define("enableEnchantmentLoot", true);
		builder.pop();

		builder.comment("Enchantment Specific Configuration").push("enchantment_specific_configuration");
		BUSY_BEE_BASE_CHANCE = builder
				.comment("The decimal base chance for a busy bee to spawn [0.0-1.0, default: 0.1]")
				.defineInRange("busyBeeBaseChance", 0.1, 0, 1.0);
		BUSY_BEE_CHANCE_PER_LEVEL = builder.comment(
				"The decimal chance per level added for a busy bee to spawn [0.0-1.0, default: 0.1]")
				.defineInRange("busyBeeChancePerLevel", 0.1, 0, 1.0);
		// TUMBLE_BEE_CHANCE_PER_LEVEL = builder
		// .comment("The decimal chance per level added for a tumble bee to spawn
		// [0.0-1.0, default: 0.1]")
		// .defineInRange("tumbleBeeChancePerLevel", 0.1, 0, 1.0);
		RAMPAGING_CHANCE = builder
				.comment("The decimal chance for rampaging to trigger [0.0-1.0, default: 0.1]")
				.defineInRange("rampagingChance", 0.2, 0, 1.0);
		RAMPAGING_DURATION = builder.comment(
				"The duration in ticks (20ticks = 1 second) per level added for rampaging. [0-10000, default: 100]")
				.defineInRange("rampagingDuration", 100, 0, 10000);
		// RUSHDOWN_DURATION = builder
		// .comment("The duration in ticks (20ticks = 1 second) per level added for
		// rushdown. [0-10000, default: 20]")
		// .defineInRange("rushdownDuration", 20, 0, 10000);
		// WEAKENING_DURATION = builder
		// .comment("The duration in ticks (20ticks = 1 second) for weakening. [0-10000,
		// default: 100]")
		// .defineInRange("weakeningDuration", 100, 0, 10000);
		// WEAKENING_DISTANCE = builder
		// .comment("The application distance in blocks for weakening. [0-10000,
		// default: 5]")
		// .defineInRange("weakeningDistance", 5, 0, 10000);
		// LEECHING_BASE_MULTIPLIER = builder
		// .comment("The decimal base multiplier on the victim's max health for leeching
		// [0.0-5.0, default: 0.2]")
		// .defineInRange("leechingBaseMultiplier", 0.2, 0, 5.0);
		// LEECHING_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for leeching [0.0-5.0, default:
		// 0.2]")
		// .defineInRange("leechingMultiplierPerLevel", 0.2, 0, 5.0);
		// DYNAMO_MAX_STACKS = builder
		// .comment("The max stacks for dynamo. [0-10000, default: 20]")
		// .defineInRange("dynamoMaxStacks", 20, 0, 10000);
		// DYNAMO_DAMAGE_MULTIPLIER_PER_STACK = builder
		// .comment("Multiplier per level applied to the damage. " +
		// "damage * (1 + (configValue*Stacks)) [0.0-5.0, default: 0.1]")
		// .defineInRange("dynamoDamageMultiplierPerStack", 0.1, 0, 5.0);
		// COMMITTED_BASE_MULTIPLIER = builder
		// .comment("The decimal base multiplier on the damage for max damage committed.
		// [0.0-5.0, default: 1.25]")
		// .defineInRange("committedBaseMultiplier", 1.25, 0, 5.0);
		// COMMITTED_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for max damage committed
		// [0.0-5.0, default: 0.25]")
		// .defineInRange("committedMultiplierPerLevel", 0.25, 0, 5.0);
		FREEZING_DURATION = builder
				.comment("The duration in ticks (20ticks = 1 second) for freezing. [0-10000, default: 60]")
				.defineInRange("freezingDuration", 60, 0, 10000);
		// SOUL_SIPHON_CHANCE = builder
		// .comment("The decimal chance for Soul Siphon to trigger [0.0-1.0, default:
		// 0.05]")
		// .defineInRange("soulSiphonChance", 0.05, 0, 1.0);
		// SOUL_SIPHON_SOULS_PER_LEVEL = builder
		// .comment("The amount of souls per trigger of Soul Siphon. " +
		// "Each souls will give a value based on Soul Gathering. [0-100, default: 2]")
		// .defineInRange("soulSiphonSoulsPerLevel", 2, 0, 100);
		CHAINS_CHANCE = builder.comment("The decimal chance for Chains to trigger [0.0-1.0, default: 0.3]")
				.defineInRange("chainsChance", 0.3, 0, 1.0);
		RADIANCE_CHANCE = builder.comment("The decimal chance for Radiance to trigger [0.0-1.0, default: 0.2]")
				.defineInRange("radianceChance", 0.2, 0, 1.0);
		// THUNDERING_CHANCE = builder
		// .comment("The decimal chance for Thundering to trigger [0.0-1.0, default:
		// 0.3]")
		// .defineInRange("thunderingChance", 0.3, 0, 1.0);
		// THUNDERING_BASE_DAMAGE = builder
		// .comment("The base damage for Thundering [0-10000, default: 5]")
		// .defineInRange("thunderingBaseDamage", 5, 0, 10000);
		// ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL = builder
		// .comment("Multiplier per level damage to healing conversion. [0.0-5.0,
		// default: 0.25]")
		// .defineInRange("altruisticDamageToHealingPerLevel", 0.25, 0, 5.0);
		// BEAST_BOSS_BASE_MULTIPLIER = builder
		// .comment("The decimal base multiplier on the minions's damage for beast boss
		// [0.0-5.0, default: 0.1]")
		// .defineInRange("beastBossBaseMultiplier", 0.1, 0, 5.0);
		// BEAST_BOSS_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for beast boss [0.0-5.0, default:
		// 0.1]")
		// .defineInRange("beastBossMultiplierPerLevel", 0.1, 0, 5.0);
		// BEAST_BURST_DAMAGE_PER_LEVEL = builder
		// .comment("The amount of damage per trigger of Beast burst. " +
		// "Each trigger causes an explosion around each minion [0-100, default: 5]")
		// .defineInRange("beastBurstDamagePerLevel", 5, 0, 100);
		// BEAST_SURGE_DURATION = builder
		// .comment("The duration in ticks of the speed boost applied by Beast Surge.
		// [0-10000, default: 200]")
		// .defineInRange("beastSurgeDuration", 200, 0, 10000);
		// COWARDICE_BASE_MULTIPLIER = builder
		// .comment("The decimal base multiplier on the damage for cowardice [0.0-5.0,
		// default: 0.1]")
		// .defineInRange("cowardiceBaseMultiplier", 0.1, 0, 5.0);
		// COWARDICE_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for cowardice [0.0-5.0, default:
		// 0.1]")
		// .defineInRange("cowardiceMultiplierPerLevel", 0.1, 0, 5.0);
		// DEFLECT_CHANCE_PER_LEVEL = builder
		// .comment("The chance per level for deflect to trigger [0.0-5.0, default:
		// 0.2]")
		// .defineInRange("deflectChancePerLevel", 0.2, 0, 5.0);
		// FOCUS_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for Focus Enchantments [0.0-5.0,
		// default: 0.25]")
		// .defineInRange("focusMultiplierPerLevel", 0.25, 0, 5.0);
		// FRENZIED_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for Frenzied [0.0-5.0, default:
		// 0.1]")
		// .defineInRange("frenziedMultiplierPerLevel", 0.1, 0, 5.0);
		// GRAVITY_PULSE_BASE_STRENGTH = builder
		// .comment("The decimal base pull strength for Gravity Pulse [0.0-5.0, default:
		// 0.1]")
		// .defineInRange("gravityPulseBaseStrength", 0.1, 0, 5.0);
		// GRAVITY_PULSE_STRENGTH_PER_LEVEL = builder
		// .comment("The strength increase per level for Gravity Pulse [0.0-5.0,
		// default: 0.1]")
		// .defineInRange("gravityPulseStrengthPerLevel", 0.1, 0, 5.0);
		// POTION_BARRIER_BASE_DURATION = builder
		// .comment("The decimal base duration for Potion Barrier [0-10000, default:
		// 60]")
		// .defineInRange("potionBarrierBaseDuration", 60, 0, 10000);
		// POTION_BARRIER_DURATION_PER_LEVEL = builder
		// .comment("The duration increase per level for Potion Barrier [0-10000,
		// default: 20]")
		// .defineInRange("potionBarrierDurationPerLevel", 20, 0, 10000);
		// RECKLESS_MAX_HEALTH_MULTIPLIER = builder
		// .comment("The multiplier to max health for reckless. Balanced as a negative
		// number. [-5.0-5.0, default: -0.6]")
		// .defineInRange("recklessMaxHealthMultiplier", -0.6, -5.0, 5.0);
		// RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER = builder
		// .comment("The decimal base multiplier on the damage for reckless [-5.0-5.0,
		// default: 0.2]")
		// .defineInRange("recklessAttackDamageBaseMultiplier", 0.2, -5.0, 5.0);
		// RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for reckless [-5.0-5.0, default:
		// 0.2]")
		// .defineInRange("recklessAttackDamageMultiplierPerLevel", 0.2, -5.0, 5.0);
		// EXPLODING_MULTIPLIER_PER_LEVEL = builder
		// .comment("The multiplier increase per level for exploding [-5.0-5.0, default:
		// 0.2]")
		// .defineInRange("explodingMultiplierPerLevel", 0.2, -5.0, 5.0);
		PROSPECTOR_CHANCE_PER_LEVEL = builder
				.comment("The chance per level for prospector to trigger [-5.0-5.0, default: 0.25]")
				.defineInRange("prospectorChancePerLevel", 0.25, -5.0, 5.0);
		POISON_CLOUD_CHANCE = builder.comment("chance The for Poison Cloud to trigger [-5.0-5.0, default: 0.3]")
				.defineInRange("prospectorChancePerLevel", 0.3, -5.0, 5.0);
		// DODGE_CHANCE_PER_LEVEL = builder
		// .comment("The chance per level for dodge to trigger [-5.0-5.0, default:
		// 0.25]")
		// .defineInRange("dodgeChancePerLevel", 0.01, -5.0, 5.0);
		// VOID_DODGE_CHANCE_PER_LEVEL = builder
		// .comment("The chance per level for void dodge to trigger [-5.0-5.0, default:
		// 0.25]")
		// .defineInRange("voidDodgeChancePerLevel", 0.05, -5.0, 5.0);
		// BEEHIVE_CHANCE_PER_LEVEL = builder
		// .comment("The chance per level for beehive to trigger [-5.0-5.0, default:
		// 0.25]")
		// .defineInRange("beehiveChancePerLevel", 0.1, -5.0, 5.0);
		// STUNNING_CHANCE_PER_LEVEL = builder
		// .comment("The chance per level for Stunning to trigger [-5.0-5.0, default:
		// 0.25]")
		// .defineInRange("stunningChancePerLevel", 0.1, -5.0, 5.0);
		builder.pop();
	}
}
