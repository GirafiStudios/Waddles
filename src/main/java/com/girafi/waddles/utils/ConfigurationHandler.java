package com.girafi.waddles.utils;

import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue dropFish;
        public final ForgeConfigSpec.BooleanValue dropExp;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            dropFish = builder
                    .comment("Enable that penguins drop fish (0 - 2 Raw Cod)")
                    .translation("waddles.configgui.dropFish")
                    .define("dropFish", false);
            dropExp = builder
                    .comment("Penguins should drop experience?")
                    .translation("waddles.configgui.dropExp")
                    .define("dropExp", true);
            builder.pop();
        }
    }

    public static class Spawn {
        public ForgeConfigSpec.ConfigValue<String[]> include;
        public ForgeConfigSpec.ConfigValue<String[]> exclude;

        Spawn(ForgeConfigSpec.Builder builder) {

        }
    }

    //config.addCustomCategoryComment(CATEGORY_PENGUIN_SPAWNS, "Configure penguins spawn weight & min/max group size. Set weight to 0 to disable.");

    public static final ForgeConfigSpec spec = BUILDER.build();
}