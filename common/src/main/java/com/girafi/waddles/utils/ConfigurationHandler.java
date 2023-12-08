package com.girafi.waddles.utils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigurationHandler {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public final ModConfigSpec.BooleanValue dropFish;
        public final ModConfigSpec.BooleanValue dropExp;
        public final ModConfigSpec.BooleanValue darkostoDefault;

        General(ModConfigSpec.Builder builder) {
            builder.push("general");
            dropFish = builder
                    .comment("Enable that penguins drop fish (0 - 2 Raw Cod)")
                    .define("dropFish", false);
            dropExp = builder
                    .comment("Penguins should drop experience?")
                    .define("dropExp", true);
            darkostoDefault = builder
                    .comment("Use the Darkosto skin variant by default?")
                    .define("darkostoDefault", false);
            builder.pop();
        }
    }

    public static final ModConfigSpec spec = BUILDER.build();
}