package net.firefoxsalesman.dungeonsmobs.lib.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DungeonsLibrariesConfig {
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_AREA_OF_EFFECT_ON_OTHER_PLAYERS;
	public static ForgeConfigSpec.ConfigValue<Integer> SOUL_BAR_VERTICAL_OFFSET;
	public static ForgeConfigSpec.ConfigValue<Integer> SOUL_BAR_HORIZONTAL_OFFSET;
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_KEEP_SOULS_ON_DEATH;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENEMY_BLACKLIST;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENEMY_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_TARGETS_BASED_ON_GOALS;

	public static class Common {

		public Common(ForgeConfigSpec.Builder builder) {

			builder.comment("Combat Configuration").push("combat_configuration");
			ENABLE_AREA_OF_EFFECT_ON_OTHER_PLAYERS = builder
					.comment("Enable area of effects also being applied to players. \n" +
							"If you do not want area of effects being applied to other players, disable this feature. [true / false]")
					.define("enableAreaOfEffectOnOtherPlayers", false);
			ENABLE_TARGETS_BASED_ON_GOALS = builder
					.comment("Enable limiting area of effects of mobs to only mobs they can normally target. \n"
							+
							"Disabling this feature will cause mobs to hit eachother with AoE effects, but can fix unintended issues. [true / false]")
					.define("enableTargetsBasedOnGoals", true);
			ENEMY_BLACKLIST = builder
					.comment("Add entities that will never be targeted by aggressive Dungeons effects. \n"
							+ "To do so, enter their registry names.")
					.defineList("effectTargetBlacklist", Lists.newArrayList(),
							(itemRaw) -> itemRaw instanceof String);
			ENEMY_WHITELIST = builder
					.comment("Add entities that should be targetted, but aren't by aggressive Dungeons effects. \n"
							+ "To do so, enter their registry names.")
					.defineList("effectTargetWhitelist", Lists.newArrayList(),
							(itemRaw) -> itemRaw instanceof String);
			builder.pop();

			builder.comment("Souls Configuration").push("souls_configuration");
			ENABLE_KEEP_SOULS_ON_DEATH = builder
					.comment("Enables keeping of souls upon death, disabled by default. [true / false]")
					.define("enableKeepSoulsOnDeath", false);
			SOUL_BAR_HORIZONTAL_OFFSET = builder
					.comment("Horizontal offset of the soul bar. Negative values move the bar to the left. Positive values move the bar to the right.")
					.define("soulBarHorizontalOffset", 0);

			SOUL_BAR_VERTICAL_OFFSET = builder
					.comment("Vertical offset of the soul bar. Negative values move the bar down. Positive values move the bar up.")
					.define("soulBarVerticalOffset", 0);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder()
				.configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}
}
