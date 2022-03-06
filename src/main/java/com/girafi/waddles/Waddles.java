package com.girafi.waddles;

import com.girafi.waddles.client.ClientHandler;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Waddles.MOD_ID)
public class Waddles {
    public static final String MOD_ID = "waddles";

    public Waddles() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientHandler.init();
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PenguinRegistry.ENTITY_DEFERRED.register(modBus);
        PenguinRegistry.ITEM_DEFERRED.register(modBus);
        WaddlesSounds.SOUND_EVENT_DEFERRED.register(modBus);
    }
}