package com.girafi.waddles.utils;

import net.minecraftforge.fml.common.Loader;

public class Reference {
    public static final String MOD_ID = "waddles";
    public static final String MOD_NAME = "Waddles";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String CLIENT_PROXY_CLASS = "com.girafi.waddles.proxy.ClientProxy";
    public static final String COMMON_PROXY_ClASS = "com.girafi.waddles.proxy.CommonProxy";
    public static final String GUI_FACTORY_CLASS = "com.girafi.waddles.client.gui.GuiFactory";
    public static final String DEPENDENCIES = "required-after:forge@[13.19.0.2152,);after:biomesoplenty@[6.0.0.2101,)";

    public static final boolean IS_BOP_LOADED = Loader.isModLoaded("biomesoplenty");
}