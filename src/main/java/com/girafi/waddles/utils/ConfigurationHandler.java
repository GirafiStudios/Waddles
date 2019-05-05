package com.girafi.waddles.utils;

import com.google.common.collect.Lists;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

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
        public final ForgeConfigSpec.IntValue min;
        public final ForgeConfigSpec.IntValue max;
        public final ForgeConfigSpec.IntValue weight;
        public final ForgeConfigSpec.ConfigValue<String[]> include;
        public final ForgeConfigSpec.ConfigValue<String[]> exclude;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn Chances");
            builder.comment("Configure penguins spawn weight & min/max group size. Set weight to 0 to disable.");
            min = builder.defineInRange("min", 1, 0, 64);
            max = builder.defineInRange("max", 4, 0, 64);
            weight = builder.defineInRange("weight", 2, 0, 100);
            include = builder.defineList("include", Lists.newArrayList(new BiomeDictionary.Type[]{SNOWY}), o -> o instanceof String);
            exclude = builder.defineList("exclude", Arrays.asList(new BiomeDictionary.Type[]{FOREST, MOUNTAIN, NETHER}), o -> o instanceof String);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}