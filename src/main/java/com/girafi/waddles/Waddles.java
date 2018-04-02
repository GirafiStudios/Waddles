package com.girafi.waddles;

import com.girafi.waddles.proxy.CommonProxy;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Waddles {
    @Mod.Instance(Reference.MOD_ID)
    public static Waddles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_ClASS)
    public static CommonProxy proxy;

    public static final ResourceLocation EMPTY = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "empty"));
    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/penguin"));

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "Waddles.cfg"));
        proxy.registerRenders();
    }
}