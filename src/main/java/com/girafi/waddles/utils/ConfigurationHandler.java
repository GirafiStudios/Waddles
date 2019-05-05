package com.girafi.waddles.utils;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> include;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> exclude;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("spawn chances");
            builder.comment("Configure penguins spawn weight & min/max group size. Set weight to 0 to disable.");
            min = builder.defineInRange("min", 1, 0, 64);
            max = builder.defineInRange("max", 4, 0, 64);
            weight = builder.defineInRange("weight", 2, 0, 100);
            builder.pop();
            builder.push("spawnable biomes");
            builder.comment("BiomeDictionary types to include & exclude.");
            include = builder.defineList("include", Collections.singletonList(SNOWY.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            exclude = builder.defineList("exclude", Arrays.asList(FOREST.toString(), MOUNTAIN.toString(), NETHER.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            builder.pop();
        }
    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}