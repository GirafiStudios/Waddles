package com.girafi.waddles.utils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler {
    public static Configuration config;
    public static boolean dropFish;
    public static boolean dropExp;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        dropFish = config.get(Configuration.CATEGORY_GENERAL, "Enable that penguins drop fish (0 - 2 Raw fish)", false).getBoolean(false);
        dropExp = config.get(Configuration.CATEGORY_GENERAL, "Penguins should drop experience?", true).getBoolean(true);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}