package com.girafi.waddles.utils;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> biomeCategoryBiomes;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> additionalBiomes;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("spawn chances");
            builder.comment("Configure penguins spawn weight & min/max group size. Set weight to 0 to disable.");
            min = builder.defineInRange("min", 1, 0, 64);
            max = builder.defineInRange("max", 4, 0, 64);
            weight = builder.defineInRange("weight", 6, 0, 100);
            builder.pop();
            builder.push("spawnable biomes");
            biomeCategoryBiomes = builder.defineList("biome categories", Collections.singletonList(Biome.Category.ICY.getName()), o -> Lists.newArrayList(Biome.Category.values()).contains(Biome.Category.func_235103_a_(String.valueOf(o).toLowerCase(Locale.ROOT))));
            additionalBiomes = builder.defineList("additional biomes (Syntax: minecraft:plains)", Collections.emptyList(), o -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(String.valueOf(o))));
            builder.pop();
        }
    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}