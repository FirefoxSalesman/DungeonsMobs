package net.firefoxsalesman.dungeonsmobs.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DungeonsMobsConfig {

	public static class Common {
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ICY_CREEPER_GRIEFING;

		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MOUNTAINEERS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ROYAL_GUARDS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_GEOMANCERS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MAGES_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ICEOLOGERS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_WINDCALLERS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SQUALL_GOLEMS_IN_RAIDS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_REDSTONE_GOLEMS_IN_RAIDS;

		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_STRONGER_HUSKS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_RANGED_SPIDERS;
		public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_HOSTILE_MOOSHROOMS;
		public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ITEM_TAB;

		public Common(ForgeConfigSpec.Builder builder) {
			// RAID CONFIGURATION
			builder.comment("Raid Configuration").push("raid_configuration");
			ENABLE_MOUNTAINEERS_IN_RAIDS = builder
					.comment("Enable the addition of Mountaineers to raids. [true / false]")
					.define("enableMountaineersInRaids", false);
			ENABLE_ROYAL_GUARDS_IN_RAIDS = builder
					.comment("Enable the addition of Royal Guards to raids. [true / false]")
					.define("enableRoyalGuardsInRaids", false);
			ENABLE_GEOMANCERS_IN_RAIDS = builder
					.comment("Enable the addition of Geomancers to raids. [true / false]")
					.define("enableGeomancersInRaids", true);
			ENABLE_MAGES_IN_RAIDS = builder
					.comment("Enable the addition of Mages to raids. [true / false]")
					.define("enableMagesInRaids", false);
			ENABLE_ICEOLOGERS_IN_RAIDS = builder
					.comment("Enable the addition of Iceologers to raids. [true / false]")
					.define("enableIceologersInRaids", false);
			ENABLE_WINDCALLERS_IN_RAIDS = builder
					.comment("Enable the addition of Windcallers to raids. [true / false]")
					.define("enableWindcallersInRaids", false);
			ENABLE_SQUALL_GOLEMS_IN_RAIDS = builder
					.comment("Enable the addition of Squall Golems to raids. [true / false]")
					.define("enableSquallGolemsInRaids", true);
			ENABLE_REDSTONE_GOLEMS_IN_RAIDS = builder
					.comment("Enable the addition of Redstone Golems to raids. [true / false]")
					.define("enableRedstoneGolemsInRaids", true);
			builder.pop();

			// MOB CONFIGURATION
			builder.comment("Mob Configuration").push("mob_configuration");
			ENABLE_ICY_CREEPER_GRIEFING = builder
					.comment("Enable the default ability of Icy Creeper Explosions to grief the environment. \n"
							+
							"If you prefer their explosions to not damage the environment, disable this feature. [true / false]")
					.define("enablyIcyCreeperGriefing", true);

			// VANILLA MOB CONFIGURATION
			builder.comment("Vanilla Mob Configuration").push("vanilla_mob_configuration");
			ENABLE_STRONGER_HUSKS = builder
					.comment("Enable the addition of additional attributes to Husks to make them as powerful as they are in Minecraft Dungeons. [true / false]")
					.define("enableStrongerHusks", true);
			ENABLE_RANGED_SPIDERS = builder
					.comment("Enables Spiders and Cave Spiders shooting webs as a ranged attack like they do in Minecraft Dungeons. [true / false]")
					.define("enableRangedSpiders", true);
			ENABLE_HOSTILE_MOOSHROOMS = builder
					.comment("Makes Mooshrooms hostile, like in Minecraft Dungeons. [true / false]")
					.define("enableHostileMooshrooms", true);
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
