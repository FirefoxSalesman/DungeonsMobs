package net.firefoxsalesman.dungeonsmobs.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class DungeonsMobsConfig {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_STRONGER_HUSKS;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_RANGED_SPIDERS;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_HOSTILE_MOOSHROOMS;
        public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ITEM_TAB;

        public Common(ForgeConfigSpec.Builder builder) {
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
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}
