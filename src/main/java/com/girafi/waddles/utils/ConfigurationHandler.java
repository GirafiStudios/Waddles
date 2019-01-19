package com.girafi.waddles.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.nio.file.Path;

@EventBusSubscriber
public class ConfigurationHandler {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final String CATEGORY_PENGUIN_SPAWNS = "spawn chances";

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

    //        config.addCustomCategoryComment(CATEGORY_PENGUIN_SPAWNS, "Configure penguins spawn weight & min/max group size. Set weight to 0 to disable.");

    private static final ForgeConfigSpec spec = BUILDER.build();

    public static void loadFrom(final Path configRoot) {
        Path configFile = configRoot.resolve(Reference.MOD_ID + ".toml");
        spec.setConfigFile(configFile);
    }
}