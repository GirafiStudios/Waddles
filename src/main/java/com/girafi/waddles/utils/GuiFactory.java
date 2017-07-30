package com.girafi.waddles.utils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiFactory extends DefaultGuiFactory {

    public GuiFactory() {
        super(Reference.MOD_ID, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiConfig(parentScreen, getConfigElements(), Reference.MOD_ID, true, true, title);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        List<IConfigElement> general = new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements();
        List<IConfigElement> spawnChances = new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS)).getChildElements();

        list.add(new DummyConfigElement.DummyCategoryElement("General", new ResourceLocation(Reference.MOD_ID, "config.category.general").toString(), general));
        list.add(new DummyConfigElement.DummyCategoryElement("Spawn Chances", new ResourceLocation(Reference.MOD_ID, "config.category.spawnchances").toString(), spawnChances));

        return list;
    }
}